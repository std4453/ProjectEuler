package io.github.std4453.projecteuler.utils;

import java.util.*;
import java.util.function.*;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 *
 */
public class StreamUtils {
	private static class MergeStreamIterator<T> implements Iterator<T> {
		private Iterator<T> a, b;

		MergeStreamIterator(Stream<T> a, Stream<T> b) {
			Objects.requireNonNull(a);
			Objects.requireNonNull(b);
			this.a = a.iterator();
			this.b = b.iterator();
		}

		@Override
		public boolean hasNext() {
			return this.a.hasNext() || this.b.hasNext();
		}

		@Override
		public T next() {
			boolean aHasNext = this.a.hasNext();
			boolean bHasNext = this.b.hasNext();

			if (!aHasNext && !bHasNext) throw new NoSuchElementException(); // both empty
			if (!aHasNext) return this.b.next(); // a empty
			if (!bHasNext) return this.a.next(); // b empty

			// both iterators have next
			// swap the iterators to retrieve from the other iterator next time
			Iterator<T> temp = this.a;
			this.b = this.a;
			this.a = temp;
			return this.b.next(); // now this.b is former this.a
		}
	}

	private static class BinaryOperationStreamIterator<A, B, T> implements Iterator<T> {
		private BiFunction<A, B, T> op;
		private Iterator<A> a;
		private Iterator<B> b;

		BinaryOperationStreamIterator(
				BiFunction<A, B, T> op, Stream<A> a, Stream<B> b) {
			Objects.requireNonNull(op);
			Objects.requireNonNull(a);
			Objects.requireNonNull(b);
			this.op = op;
			this.a = a.iterator();
			this.b = b.iterator();
		}

		@Override
		public boolean hasNext() {
			return this.a.hasNext() && this.b.hasNext();
		}

		@Override
		public T next() {
			if (!this.a.hasNext() || this.b.hasNext()) throw new NoSuchElementException();
			return op.apply(a.next(), b.next());
		}
	}

	private static class LimitUntilIterator<T> implements Iterator<T> {
		private Iterator<T> iterator;
		private Predicate<T> predicate;

		private T cache;
		private boolean hasNext;

		LimitUntilIterator(Stream<T> stream, Predicate<T> predicate) {
			this.iterator = stream.iterator();
			this.predicate = predicate;

			this.updateCache();
		}

		@Override
		public boolean hasNext() {
			return this.hasNext;
		}

		@Override
		public T next() {
			if (!this.hasNext) throw new NoSuchElementException();
			T next = this.cache;
			this.updateCache();
			return next;
		}

		// In order to determine whether there should be a next element, we pre-fetch an
		// element from the underlying iterator and test it against the predicate.
		// If this.hasNext if false, this.hasNext() will return false even if the
		// iterator still has remaining elements.

		private void updateCache() {
			if (!this.iterator.hasNext()) {
				this.hasNext = false;
				return;
			}

			this.cache = this.iterator.next();
			this.hasNext = !predicate.test(this.cache);
		}
	}

	/**
	 * {@code int} version of {@link LimitUntilIterator}.
	 */
	private static final class IntLimitUntilIterator implements PrimitiveIterator.OfInt {
		private OfInt iterator;
		private IntPredicate predicate;

		private int cache;
		private boolean hasNext;

		IntLimitUntilIterator(IntStream stream, IntPredicate predicate) {
			this.iterator = stream.iterator();
			this.predicate = predicate;

			this.updateCache();
		}

		@Override
		public boolean hasNext() {
			return this.hasNext;
		}

		@Override
		public int nextInt() {
			if (!this.hasNext) throw new NoSuchElementException();
			int next = this.cache;
			this.updateCache();
			return next;
		}

		private void updateCache() {
			if (!this.iterator.hasNext()) {
				this.hasNext = false;
				return;
			}

			this.cache = this.iterator.next();
			this.hasNext = !predicate.test(this.cache);
		}
	}

	/**
	 * {@code long} version of {@link LimitUntilIterator}.
	 */
	private static final class LongLimitUntilIterator
			implements PrimitiveIterator.OfLong {
		private OfLong iterator;
		private LongPredicate predicate;

		private long cache;
		private boolean hasNext;

		LongLimitUntilIterator(LongStream stream, LongPredicate predicate) {
			this.iterator = stream.iterator();
			this.predicate = predicate;

			this.updateCache();
		}

		@Override
		public boolean hasNext() {
			return this.hasNext;
		}

		@Override
		public long nextLong() {
			if (!this.hasNext) throw new NoSuchElementException();
			long next = this.cache;
			this.updateCache();
			return next;
		}

		private void updateCache() {
			if (!this.iterator.hasNext()) {
				this.hasNext = false;
				return;
			}

			this.cache = this.iterator.next();
			this.hasNext = !predicate.test(this.cache);
		}
	}

	private static class AdjacentStreamIterator<T, U> implements Iterator<U> {
		private Iterator<T> iterator;
		private BiFunction<T, T, U> fn;

		private boolean hasNext;
		private T src1, src2;
		private U calculated;

		AdjacentStreamIterator(Stream<T> stream, BiFunction<T, T, U> fn) {
			this.iterator = stream.iterator();
			this.fn = fn;

			this.prefetch();
		}

		@Override
		public boolean hasNext() {
			return this.hasNext;
		}

		@Override
		public U next() {
			if (!this.hasNext) throw new NoSuchElementException();
			U value = this.calculated;
			this.update();
			return value;
		}

		private void prefetch() {
			if (!this.checkNext()) return;
			this.src1 = iterator.next();
			this.fetchSrc2AndCalculate();
		}

		private void update() {
			if (!this.checkNext()) return;
			this.src1 = this.src2;
			this.fetchSrc2AndCalculate();
		}

		private void fetchSrc2AndCalculate() {
			if (!this.checkNext()) return;
			this.src2 = iterator.next();
			this.calculated = this.fn.apply(this.src1, this.src2);
			this.hasNext = true;
		}

		private boolean checkNext() {
			return this.hasNext = this.iterator.hasNext();
		}
	}

	/**
	 * {@code int} version of {@link AdjacentStreamIterator}.
	 */
	private static class IntAdjacentIterator implements PrimitiveIterator.OfInt {
		private OfInt iterator;
		private IntBinaryOperator fn;

		private boolean hasNext;
		private int src1, src2;
		private int calculated;

		IntAdjacentIterator(IntStream stream, IntBinaryOperator fn) {
			this.iterator = stream.iterator();
			this.fn = fn;

			this.prefetch();
		}

		@Override
		public boolean hasNext() {
			return this.hasNext;
		}

		@Override
		public int nextInt() {
			if (!this.hasNext) throw new NoSuchElementException();
			int value = this.calculated;
			this.update();
			return value;
		}

		private void prefetch() {
			if (!this.checkNext()) return;
			this.src1 = iterator.next();
			this.fetchSrc2AndCalculate();
		}

		private void update() {
			if (!this.checkNext()) return;
			this.src1 = this.src2;
			this.fetchSrc2AndCalculate();
		}

		private void fetchSrc2AndCalculate() {
			if (!this.checkNext()) return;
			this.src2 = iterator.next();
			this.calculated = this.fn.applyAsInt(this.src1, this.src2);
			this.hasNext = true;
		}

		private boolean checkNext() {
			return this.hasNext = this.iterator.hasNext();
		}
	}

	/**
	 * {@code long} version of {@link AdjacentStreamIterator}.
	 */
	private static class LongAdjacentIterator implements PrimitiveIterator.OfLong {
		private OfLong iterator;
		private LongBinaryOperator fn;

		private boolean hasNext;
		private long src1, src2;
		private long calculated;

		LongAdjacentIterator(LongStream stream, LongBinaryOperator fn) {
			this.iterator = stream.iterator();
			this.fn = fn;

			this.prefetch();
		}

		@Override
		public boolean hasNext() {
			return this.hasNext;
		}

		@Override
		public long nextLong() {
			if (!this.hasNext) throw new NoSuchElementException();
			long value = this.calculated;
			this.update();
			return value;
		}

		private void prefetch() {
			if (!this.checkNext()) return;
			this.src1 = iterator.next();
			this.fetchSrc2AndCalculate();
		}

		private void update() {
			if (!this.checkNext()) return;
			this.src1 = this.src2;
			this.fetchSrc2AndCalculate();
		}

		private void fetchSrc2AndCalculate() {
			if (!this.checkNext()) return;
			this.src2 = iterator.next();
			this.calculated = this.fn.applyAsLong(this.src1, this.src2);
			this.hasNext = true;
		}

		private boolean checkNext() {
			return this.hasNext = this.iterator.hasNext();
		}
	}

	/**
	 * Return a {@link Stream} which is the merges the elements of two
	 * {@link Stream Streams}.<br />
	 * Let the <i>front-most element</i> of a {@link Stream} be the first element
	 * returned by calling the {@link Iterator#next()} method on the
	 * {@link Stream#iterator() Stream's Iterator}.<br />
	 * The merged {@link Stream} take the <i>front-most element</i> of {@code a} and
	 * {@code b} in turn. When one of the two streams runs out of elements, all
	 * following elements are provided by the other stream. When both streams run out
	 * of elements, no further elements can be retrieved.<br />
	 * As an example, if stream {@code a} contains all the odd numbers:<br />
	 * {@code 1, 3, 5, 7, 9, 11...}<br />
	 * and stream {@code b} contains all the even numbers:<br />
	 * {@code 2, 4, 6, 8, 10, 12...}<br />
	 * {@code merge(a, b)} will return:<br />
	 * {@code 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12...}<br />
	 * and {@code merge(b, a)} will return:<br />
	 * {@code 2, 1, 4, 3, 6, 5, 8, 7, 10, 9, 12, 11...}<br />
	 * Please note the difference.<br />
	 * This method is mostly used to combine two infinite streams, in supplement to
	 * {@link Stream#concat(Stream, Stream)}, which appends elements in the second
	 * {@link Stream} to all the elements in the first {@link Stream} - impossible for
	 * infinite streams.<br />
	 * This methods runs a <i>terminal operation</i> on the two given streams.<br />
	 * The returned {@link Stream} is <i>SEQUENTIAL</i>.
	 */
	public static <T> Stream<T> merge(Stream<T> a, Stream<T> b) {
		return asStream(new MergeStreamIterator<>(a, b));
	}

	/**
	 * Calculate each element of {@link Stream} {@code a} and {@link Stream} {@code b}
	 * in order to form a new {@link Stream}.<br />
	 * The size of the returned {@link Stream} will be the minimum of the sizes of
	 * {@code a} and {@code b}.<br />
	 * This methods runs a <i>terminal operation</i> on the two given streams.<br />
	 * The returned {@link Stream} is <i>SEQUENTIAL</i>.
	 */
	public static <A, B, T> Stream<T> calc(
			Stream<A> a, Stream<B> b, BiFunction<A, B, T> fn) {
		return asStream(new BinaryOperationStreamIterator<>(fn, a, b));
	}

	/**
	 * Return a part of the given {@link Stream} starting at its first element and
	 * ending at the first element in {@code s} that satisfies {@link Predicate} {@code
	 * p} (exclusive).<br />
	 * This methods runs a <i>terminal operation</i> on the given stream.<br />
	 * The returned {@link Stream} is <i>SEQUENTIAL</i>.
	 */
	public static <T> Stream<T> limitUntil(Stream<T> s, Predicate<T> p) {
		return asStream(new LimitUntilIterator<>(s, p));
	}

	/**
	 * {@code int} version of {@link #limitUntil(Stream, Predicate)}.
	 *
	 * @see #limitUntil(Stream, Predicate)
	 */
	public static IntStream limitUntil(IntStream s, IntPredicate p) {
		return asStream(new IntLimitUntilIterator(s, p));
	}

	/**
	 * {@code long} version of {@link #limitUntil(Stream, Predicate)}.
	 *
	 * @see #limitUntil(Stream, Predicate)
	 */
	public static LongStream limitUntil(LongStream s, LongPredicate p) {
		return asStream(new LongLimitUntilIterator(s, p));
	}

	/**
	 * Return a {@link Stream} representing the result of executing a function {@code
	 * fn} on every 2 adjacent elements in {@link Stream} {@code s}.<br />
	 * This methods runs a <i>terminal operation</i> on the given stream.<br />
	 * The returned {@link Stream} is <i>SEQUENTIAL</i>.
	 */
	public static <T, U> Stream<U> calcAdjacent(
			Stream<T> s, BiFunction<T, T, U> fn) {
		return asStream(new AdjacentStreamIterator<>(s, fn));
	}

	/**
	 * {@code int} version of {@link #calcAdjacent(Stream, BiFunction)}.
	 *
	 * @see #calc(Stream, Stream, BiFunction)
	 */
	public static IntStream calcAdjacent(IntStream s, IntBinaryOperator fn) {
		return asStream(new IntAdjacentIterator(s, fn));
	}

	/**
	 * {@code long} version of {@link #calcAdjacent(Stream, BiFunction)}.
	 *
	 * @see #calc(Stream, Stream, BiFunction)
	 */
	public static LongStream calcAdjacent(LongStream s, LongBinaryOperator fn) {
		return asStream(new LongAdjacentIterator(s, fn));
	}

	/**
	 * Convert {@link Iterator} to {@link Stream}.<br />
	 * The returned {@link Stream} is {@link Spliterator#IMMUTABLE IMMUTABLE} and
	 * {@link Spliterator#ORDERED ORDERED} and SEQUENTIAL.
	 */
	public static <T> Stream<T> asStream(Iterator<T> iterator) {
		Spliterator<T> spliterator = Spliterators.spliteratorUnknownSize(iterator,
				Spliterator.IMMUTABLE | Spliterator.ORDERED);
		return StreamSupport.stream(spliterator, false);
	}

	/**
	 * Convert {@link PrimitiveIterator.OfInt} to {@link IntStream}.<br />
	 * The returned {@link Stream} is {@link Spliterator#IMMUTABLE IMMUTABLE} and
	 * {@link Spliterator#ORDERED ORDERED} and SEQUENTIAL.
	 */
	public static IntStream asStream(PrimitiveIterator.OfInt iterator) {
		Spliterator.OfInt spliterator = Spliterators.spliteratorUnknownSize(iterator,
				Spliterator.IMMUTABLE | Spliterator.ORDERED);
		return StreamSupport.intStream(spliterator, false);
	}

	/**
	 * Convert {@link PrimitiveIterator.OfLong} to {@link LongStream}.<br />
	 * The returned {@link Stream} is {@link Spliterator#IMMUTABLE IMMUTABLE} and
	 * {@link Spliterator#ORDERED ORDERED} and SEQUENTIAL.
	 */
	public static LongStream asStream(PrimitiveIterator.OfLong iterator) {
		Spliterator.OfLong spliterator = Spliterators.spliteratorUnknownSize(iterator,
				Spliterator.IMMUTABLE | Spliterator.ORDERED);
		return StreamSupport.longStream(spliterator, false);
	}
}
