package io.github.std4453.projecteuler.utils;

import java.util.*;
import java.util.function.*;

/**
 * An {@code int} version of {@link ArrayList}, storing values as {@code ints} instead
 * of {@link Integer Integers} to improve performance.<br />
 * Most documentations are deleted from the code, please see the corresponding method
 * in {@link ArrayList} for details.
 */
@SuppressWarnings({"unused", "WeakerAccess", "deprecation", "Duplicates"})
public class IntArrayList extends AbstractList<Integer>
		implements List<Integer>, RandomAccess, Cloneable, java.io.Serializable {
	private static final long serialVersionUID = 8683452581122892189L;

	private static final int DEFAULT_CAPACITY = 10;
	private static final int[] EMPTY_ELEMENTDATA = {};

	transient int[] elementData; // non-private to simplify nested class access

	private int size;

	public IntArrayList(int initialCapacity) {
		if (initialCapacity < 0)
			throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
		this.elementData = new int[initialCapacity];
	}

	public IntArrayList() {
		this.elementData = EMPTY_ELEMENTDATA;
	}

	@Deprecated
	public IntArrayList(Collection<? extends Integer> c) {
		this();
		this.addAll(c);
	}

	public IntArrayList(IntArrayList c) {
		this();
		this.addAll(c);
	}

	public void trimToSize() {
		modCount++;
		if (size < elementData.length) elementData = Arrays.copyOf(elementData, size);
	}

	public void ensureCapacity(int minCapacity) {
		int minExpand = elementData != EMPTY_ELEMENTDATA ? 0 : DEFAULT_CAPACITY;
		if (minCapacity > minExpand) ensureExplicitCapacity(minCapacity);
	}

	private void ensureCapacityInternal(int minCapacity) {
		if (elementData == EMPTY_ELEMENTDATA)
			minCapacity = Math.max(DEFAULT_CAPACITY, minCapacity);
		ensureExplicitCapacity(minCapacity);
	}

	private void ensureExplicitCapacity(int minCapacity) {
		modCount++;

		// overflow-conscious code
		if (minCapacity - elementData.length > 0)
			grow(minCapacity);
	}

	private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

	private void grow(int minCapacity) {
		// overflow-conscious code
		int oldCapacity = elementData.length;
		int newCapacity = oldCapacity + (oldCapacity >> 1);
		if (newCapacity - minCapacity < 0)
			newCapacity = minCapacity;
		if (newCapacity - MAX_ARRAY_SIZE > 0)
			newCapacity = hugeCapacity(minCapacity);
		// minCapacity is usually close to size, so this is a win:
		elementData = Arrays.copyOf(elementData, newCapacity);
	}

	private static int hugeCapacity(int minCapacity) {
		if (minCapacity < 0) // overflow
			throw new OutOfMemoryError();
		return (minCapacity > MAX_ARRAY_SIZE) ?
				Integer.MAX_VALUE :
				MAX_ARRAY_SIZE;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	@Deprecated
	public boolean contains(Object o) {
		return indexOf(o) >= 0;
	}

	public boolean contains(int n) {
		return indexOf(n) >= 0;
	}

	@Override
	@Deprecated
	public int indexOf(Object o) {
		if (o == null) return -1; // only ints are stored
		for (int i = 0; i < size; i++)
			if (o.equals(elementData[i])) return i;
		return -1;
	}

	public int indexOf(int n) {
		for (int i = 0; i < size; i++) if (elementData[i] == n) return i;
		return -1;
	}

	@Override
	@Deprecated
	public int lastIndexOf(Object o) {
		if (o == null) return -1;
		for (int i = size - 1; i >= 0; i--)
			if (o.equals(elementData[i])) return i;
		return -1;
	}

	public int lastIndexOf(int n) {
		for (int i = size - 1; i >= 0; i--) if (elementData[i] == n) return i;
		return -1;
	}

	@Override
	public Object clone() {
		try {
			IntArrayList v = (IntArrayList) super.clone();
			v.elementData = Arrays.copyOf(elementData, size);
			v.modCount = 0;
			return v;
		} catch (CloneNotSupportedException e) {
			// this shouldn't happen, since we are Cloneable
			throw new InternalError(e);
		}
	}

	@Override
	@Deprecated
	public Object[] toArray() {
		Object[] objects = new Object[size];
		for (int i = 0; i < size; i++) objects[i] = elementData[i];
		return objects;
	}

	public int[] toIntArray() {
		return Arrays.copyOf(elementData, size);
	}

	@Override
	@Deprecated
	@SuppressWarnings({"unchecked", "SuspiciousSystemArraycopy"})
	public <T> T[] toArray(T[] a) {
		Object[] content = new Integer[this.size];
		for (int i = 0; i < size; i++) content[i] = elementData[i];

		if (a.length < size)
			// Make a new array of a's runtime type, but my contents:
			return (T[]) Arrays.copyOf(content, size, a.getClass());
		System.arraycopy(content, 0, a, 0, size);
		if (a.length > size)
			a[size] = null;
		return a;
	}

	// Positional Access Operations

	@Deprecated
	Integer elementData(int index) {
		return elementData[index];
	}

	int intElementData(int index) {
		return elementData[index];
	}

	@Override
	@Deprecated
	public Integer get(int index) {
		rangeCheck(index);
		return elementData(index);
	}

	public int getInt(int index) {
		rangeCheck(index);
		return intElementData(index);
	}

	@Override
	@Deprecated
	public Integer set(int index, Integer element) {
		rangeCheck(index);
		Integer oldValue = elementData(index);
		elementData[index] = element;
		return oldValue;
	}

	public int set(int index, int element) {
		rangeCheck(index);
		int oldValue = intElementData(index);
		elementData[index] = element;
		return oldValue;
	}

	@Override
	@Deprecated
	public boolean add(Integer e) {
		ensureCapacityInternal(size + 1);  // Increments modCount!!
		elementData[size++] = e;
		return true;
	}

	public boolean add(int n) {
		ensureCapacityInternal(size + 1);  // Increments modCount!!
		elementData[size++] = n;
		return true;
	}

	@Override
	@Deprecated
	public void add(int index, Integer element) {
		rangeCheckForAdd(index);
		ensureCapacityInternal(size + 1);  // Increments modCount!!
		System.arraycopy(elementData, index, elementData, index + 1,
				size - index);
		elementData[index] = element;
		size++;
	}

	public void add(int index, int element) {
		rangeCheckForAdd(index);
		ensureCapacityInternal(size + 1);  // Increments modCount!!
		System.arraycopy(elementData, index, elementData, index + 1,
				size - index);
		elementData[index] = element;
		size++;
	}

	@Override
	@Deprecated
	public Integer remove(int index) {
		rangeCheck(index);
		modCount++;
		Integer oldValue = elementData(index);
		int numMoved = size - index - 1;
		if (numMoved > 0)
			System.arraycopy(elementData, index + 1, elementData, index,
					numMoved);
		//		elementData[--size] = null; // clear to let GC do its work
		--size;  // primitive value doesn't need to be cleared
		return oldValue;
	}

	public int removeAt(int index) {  // to distinguish with remove(int)
		rangeCheck(index);
		modCount++;
		int oldValue = intElementData(index);
		int numMoved = size - index - 1;
		if (numMoved > 0)
			System.arraycopy(elementData, index + 1, elementData, index, numMoved);
		--size;
		return oldValue;
	}

	@Override
	@Deprecated
	public boolean remove(Object o) {
		if (o == null) return false;
		for (int index = 0; index < size; index++)
			if (o.equals(elementData[index])) {
				fastRemove(index);
				return true;
			}
		return false;
	}

	public boolean removeElement(int n) {  // to distinguish with remove(int)
		for (int index = 0; index < size; index++)
			if (elementData[index] == n) {
				fastRemove(index);
				return true;
			}
		return false;
	}

	private void fastRemove(int index) {
		modCount++;
		int numMoved = size - index - 1;
		if (numMoved > 0)
			System.arraycopy(elementData, index + 1, elementData, index,
					numMoved);
		//		elementData[--size] = null; // clear to let GC do its work
		--size;  // primitive value doesn't need to be cleared
	}

	@Override
	public void clear() {
		modCount++;
		//		// clear to let GC do its work
		//		for (int i = 0; i < size; i++)
		//			elementData[i] = null;
		size = 0;
	}

	@Override
	@Deprecated
	public boolean addAll(Collection<? extends Integer> c) {
		Object[] a = c.toArray();
		int numNew = a.length;
		ensureCapacityInternal(size + numNew);  // Increments modCount
		//		System.arraycopy(a, 0, elementData, size, numNew);
		for (int i = 0; i < numNew; ++i) elementData[size + i] = (Integer) a[i];
		size += numNew;
		return numNew != 0;
	}

	public boolean addAll(IntArrayList c) {
		int[] a = c.toIntArray();
		int numNew = a.length;
		ensureCapacityInternal(size + numNew); // Increments modCount
		System.arraycopy(a, 0, elementData, size, numNew);
		size += numNew;
		return numNew != 0;
	}

	@Override
	@Deprecated
	public boolean addAll(int index, Collection<? extends Integer> c) {
		rangeCheckForAdd(index);

		Object[] a = c.toArray();
		int numNew = a.length;
		ensureCapacityInternal(size + numNew);  // Increments modCount

		int numMoved = size - index;
		if (numMoved > 0)
			System.arraycopy(elementData, index, elementData, index + numNew,
					numMoved);

		//		System.arraycopy(a, 0, elementData, index, numNew);
		for (int i = 0; i < numNew; ++i) elementData[index + i] = (Integer) a[i];
		size += numNew;
		return numNew != 0;
	}

	public boolean addAll(int index, IntArrayList c) {
		rangeCheckForAdd(index);

		int[] a = c.toIntArray();
		int numNew = a.length;
		ensureCapacityInternal(size + numNew);  // Increments modCount

		int numMoved = size - index;
		if (numMoved > 0)
			System.arraycopy(elementData, index, elementData, index + numNew,
					numMoved);

		System.arraycopy(a, 0, elementData, index, numNew);
		size += numNew;
		return numNew != 0;
	}

	@Override
	protected void removeRange(int fromIndex, int toIndex) {
		modCount++;
		int numMoved = size - toIndex;
		System.arraycopy(elementData, toIndex, elementData, fromIndex,
				numMoved);
		//		// clear to let GC do its work
		//		int newSize = size - (toIndex - fromIndex);
		//		for (int i = newSize; i < size; i++) {
		//			elementData[i] = null;
		//		}
		//		size = newSize;
		size = size - (toIndex - fromIndex);
	}

	private void rangeCheck(int index) {
		if (index >= size) throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
	}

	private void rangeCheckForAdd(int index) {
		if (index > size || index < 0)
			throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
	}

	private String outOfBoundsMsg(int index) {
		return "Index: " + index + ", Size: " + size;
	}

	@Override
	@Deprecated
	public boolean removeAll(Collection<?> c) {
		Objects.requireNonNull(c);
		return batchRemove(c, false);
	}

	public boolean removeAll(IntArrayList c) {
		Objects.requireNonNull(c);
		return batchRemove(c, false);
	}

	@Override
	@Deprecated
	public boolean retainAll(Collection<?> c) {
		Objects.requireNonNull(c);
		return batchRemove(c, true);
	}

	public boolean retainAll(IntArrayList c) {
		Objects.requireNonNull(c);
		return batchRemove(c, true);
	}


	@Deprecated
	private boolean batchRemove(Collection<?> c, boolean complement) {
		final int[] elementData = this.elementData;
		int r = 0, w = 0;
		boolean modified = false;
		try {
			for (; r < size; r++)
				if (c.contains(elementData[r]) == complement)
					elementData[w++] = elementData[r];
		} finally {
			// Preserve behavioral compatibility with AbstractCollection,
			// even if c.contains() throws.
			if (r != size) {
				System.arraycopy(elementData, r, elementData, w, size - r);
				w += size - r;
			}
			if (w != size) {
				//				// clear to let GC do its work
				//				for (int i = w; i < size; i++)
				//					elementData[i] = null;
				modCount += size - w;
				size = w;
				modified = true;
			}
		}
		return modified;
	}


	private boolean batchRemove(IntArrayList c, boolean complement) {
		final int[] elementData = this.elementData;
		int r = 0, w = 0;
		boolean modified = false;
		try {
			for (; r < size; r++)
				if (c.contains(elementData[r]) == complement)
					elementData[w++] = elementData[r];
		} finally {
			// Preserve behavioral compatibility with AbstractCollection,
			// even if c.contains() throws.
			if (r != size) {
				System.arraycopy(elementData, r, elementData, w, size - r);
				w += size - r;
			}
			if (w != size) {
				//				// clear to let GC do its work
				//				for (int i = w; i < size; i++)
				//					elementData[i] = null;
				modCount += size - w;
				size = w;
				modified = true;
			}
		}
		return modified;
	}

	private void writeObject(java.io.ObjectOutputStream s)
			throws java.io.IOException {
		// Write out element count, and any hidden stuff
		int expectedModCount = modCount;
		s.defaultWriteObject();

		// Write out size as capacity for behavioural compatibility with clone()
		s.writeInt(size);

		// Write out all elements in the proper order.
		for (int i = 0; i < size; i++) s.writeInt(elementData[i]);

		if (modCount != expectedModCount) {
			throw new ConcurrentModificationException();
		}
	}

	private void readObject(java.io.ObjectInputStream s)
			throws java.io.IOException, ClassNotFoundException {
		elementData = EMPTY_ELEMENTDATA;

		// Read in size, and any hidden stuff
		s.defaultReadObject();

		// Read in capacity
		s.readInt(); // ignored

		if (size > 0) {
			// be like clone(), allocate array based upon size not capacity
			ensureCapacityInternal(size);

			int[] a = elementData;
			// Read in all elements in the proper order.
			for (int i = 0; i < size; i++) a[i] = s.readInt();
		}
	}

	@Override
	@Deprecated
	public ListIterator<Integer> listIterator(int index) {
		if (index < 0 || index > size)
			throw new IndexOutOfBoundsException("Index: " + index);
		return new ListItr(index);
	}

	@Override
	@Deprecated
	public ListIterator<Integer> listIterator() {
		return new ListItr(0);
	}

	@Override
	@Deprecated
	public Iterator<Integer> iterator() {
		return new Itr();
	}

	public PrimitiveIterator.OfInt intIterator() {
		return new IntItr();
	}

	@Deprecated
	private class Itr implements Iterator<Integer> {
		int cursor;       // index of next element to return
		int lastRet = -1; // index of last element returned; -1 if no such
		int expectedModCount = modCount;

		@Override
		public boolean hasNext() {
			return cursor != size;
		}


		@Override
		public Integer next() {
			checkForComodification();
			int i = cursor;
			if (i >= size)
				throw new NoSuchElementException();
			int[] elementData = IntArrayList.this.elementData;
			if (i >= elementData.length)
				throw new ConcurrentModificationException();
			cursor = i + 1;
			return elementData[lastRet = i];
		}

		@Override
		public void remove() {
			if (lastRet < 0)
				throw new IllegalStateException();
			checkForComodification();

			try {
				IntArrayList.this.remove(lastRet);
				cursor = lastRet;
				lastRet = -1;
				expectedModCount = modCount;
			} catch (IndexOutOfBoundsException ex) {
				throw new ConcurrentModificationException();
			}
		}


		@Override
		public void forEachRemaining(Consumer<? super Integer> consumer) {
			Objects.requireNonNull(consumer);
			final int size = IntArrayList.this.size;
			int i = cursor;
			if (i >= size) {
				return;
			}
			final int[] elementData = IntArrayList.this.elementData;
			if (i >= elementData.length) {
				throw new ConcurrentModificationException();
			}
			while (i != size && modCount == expectedModCount) {
				consumer.accept(elementData[i++]);
			}
			// update once at end of iteration to reduce heap write traffic
			cursor = i;
			lastRet = i - 1;
			checkForComodification();
		}

		final void checkForComodification() {
			if (modCount != expectedModCount)
				throw new ConcurrentModificationException();
		}
	}

	@Deprecated
	private class ListItr extends Itr implements ListIterator<Integer> {
		ListItr(int index) {
			super();
			cursor = index;
		}

		@Override
		public boolean hasPrevious() {
			return cursor != 0;
		}

		@Override
		public int nextIndex() {
			return cursor;
		}

		@Override
		public int previousIndex() {
			return cursor - 1;
		}

		@Override
		public Integer previous() {
			checkForComodification();
			int i = cursor - 1;
			if (i < 0)
				throw new NoSuchElementException();
			int[] elementData = IntArrayList.this.elementData;
			if (i >= elementData.length)
				throw new ConcurrentModificationException();
			cursor = i;
			return elementData[lastRet = i];
		}

		@Override
		public void set(Integer e) {
			if (lastRet < 0)
				throw new IllegalStateException();
			checkForComodification();

			try {
				IntArrayList.this.set(lastRet, e);
			} catch (IndexOutOfBoundsException ex) {
				throw new ConcurrentModificationException();
			}
		}

		@Override
		public void add(Integer e) {
			checkForComodification();

			try {
				int i = cursor;
				IntArrayList.this.add(i, e);
				cursor = i + 1;
				lastRet = -1;
				expectedModCount = modCount;
			} catch (IndexOutOfBoundsException ex) {
				throw new ConcurrentModificationException();
			}
		}
	}

	private class IntItr implements PrimitiveIterator.OfInt {
		int cursor;       // index of next element to return
		int lastRet = -1; // index of last element returned; -1 if no such
		int expectedModCount = modCount;

		@Override
		public boolean hasNext() {
			return cursor != size;
		}


		@Override
		public int nextInt() {
			checkForComodification();
			int i = cursor;
			if (i >= size)
				throw new NoSuchElementException();
			int[] elementData = IntArrayList.this.elementData;
			if (i >= elementData.length)
				throw new ConcurrentModificationException();
			cursor = i + 1;
			return elementData[lastRet = i];
		}


		@Override
		public void forEachRemaining(IntConsumer action) {
			Objects.requireNonNull(action);
			final int size = IntArrayList.this.size;
			int i = cursor;
			if (i >= size) {
				return;
			}
			final int[] elementData = IntArrayList.this.elementData;
			if (i >= elementData.length) {
				throw new ConcurrentModificationException();
			}
			while (i != size && modCount == expectedModCount) {
				action.accept(elementData[i++]);
			}
			// update once at end of iteration to reduce heap write traffic
			cursor = i;
			lastRet = i - 1;
			checkForComodification();
		}

		@Override
		public void remove() {
			if (lastRet < 0)
				throw new IllegalStateException();
			checkForComodification();

			try {
				IntArrayList.this.removeAt(lastRet);
				cursor = lastRet;
				lastRet = -1;
				expectedModCount = modCount;
			} catch (IndexOutOfBoundsException ex) {
				throw new ConcurrentModificationException();
			}
		}

		final void checkForComodification() {
			if (modCount != expectedModCount)
				throw new ConcurrentModificationException();
		}
	}

	@Override
	@Deprecated
	public List<Integer> subList(int fromIndex, int toIndex) {
		subListRangeCheck(fromIndex, toIndex, size);
		return new SubList(this, 0, fromIndex, toIndex);
	}

	static void subListRangeCheck(int fromIndex, int toIndex, int size) {
		if (fromIndex < 0)
			throw new IndexOutOfBoundsException("fromIndex = " + fromIndex);
		if (toIndex > size)
			throw new IndexOutOfBoundsException("toIndex = " + toIndex);
		if (fromIndex > toIndex)
			throw new IllegalArgumentException("fromIndex(" + fromIndex +
					") > toIndex(" + toIndex + ")");
	}

	@Deprecated
	private class SubList extends AbstractList<Integer> implements RandomAccess {
		private final AbstractList<Integer> parent;
		private final int parentOffset;
		private final int offset;
		int size;

		SubList(AbstractList<Integer> parent,
				int offset, int fromIndex, int toIndex) {
			this.parent = parent;
			this.parentOffset = fromIndex;
			this.offset = offset + fromIndex;
			this.size = toIndex - fromIndex;
			this.modCount = IntArrayList.this.modCount;
		}

		protected void setModCount() {
			if (parent instanceof IntArrayList)
				this.modCount = ((IntArrayList) parent).modCount;
			else if (parent instanceof SubList)
				this.modCount = ((SubList) parent).modCount;
			else throw new UnsupportedOperationException("set modCount");
		}

		@Override
		public Integer set(int index, Integer e) {
			rangeCheck(index);
			checkForComodification();
			Integer oldValue = IntArrayList.this.elementData(offset + index);
			IntArrayList.this.elementData[offset + index] = e;
			return oldValue;
		}

		@Override
		public Integer get(int index) {
			rangeCheck(index);
			checkForComodification();
			return IntArrayList.this.elementData(offset + index);
		}

		@Override
		public int size() {
			checkForComodification();
			return this.size;
		}

		@Override
		public void add(int index, Integer e) {
			rangeCheckForAdd(index);
			checkForComodification();
			parent.add(parentOffset + index, e);
			this.setModCount();
			this.size++;
		}

		@Override
		public Integer remove(int index) {
			rangeCheck(index);
			checkForComodification();
			Integer result = parent.remove(parentOffset + index);
			this.setModCount();
			this.size--;
			return result;
		}

		@Override
		protected void removeRange(int fromIndex, int toIndex) {
			checkForComodification();
			if (this.parent instanceof IntArrayList)
				((IntArrayList) parent).removeRange(parentOffset + fromIndex,
						parentOffset + toIndex);
			else if (this.parent instanceof SubList)
				((SubList) parent).removeRange(parentOffset + fromIndex,
						parentOffset + toIndex);
			else throw new UnsupportedOperationException("remove range");
			this.setModCount();
			this.size -= toIndex - fromIndex;
		}

		@Override
		public boolean addAll(Collection<? extends Integer> c) {
			return addAll(this.size, c);
		}

		@Override
		public boolean addAll(int index, Collection<? extends Integer> c) {
			rangeCheckForAdd(index);
			int cSize = c.size();
			if (cSize == 0)
				return false;

			checkForComodification();
			parent.addAll(parentOffset + index, c);
			this.setModCount();
			this.size += cSize;
			return true;
		}

		@Override
		public Iterator<Integer> iterator() {
			return listIterator();
		}

		@Override
		public ListIterator<Integer> listIterator(final int index) {
			checkForComodification();
			rangeCheckForAdd(index);
			final int offset = this.offset;

			return new ListIterator<Integer>() {
				int cursor = index;
				int lastRet = -1;
				int expectedModCount = IntArrayList.this.modCount;

				@Override
				public boolean hasNext() {
					return cursor != IntArrayList.SubList.this.size;
				}

				@Override
				public Integer next() {
					checkForComodification();
					int i = cursor;
					if (i >= IntArrayList.SubList.this.size)
						throw new NoSuchElementException();
					int[] elementData = IntArrayList.this.elementData;
					if (offset + i >= elementData.length)
						throw new ConcurrentModificationException();
					cursor = i + 1;
					return elementData[offset + (lastRet = i)];
				}

				@Override
				public boolean hasPrevious() {
					return cursor != 0;
				}

				@Override
				public Integer previous() {
					checkForComodification();
					int i = cursor - 1;
					if (i < 0)
						throw new NoSuchElementException();
					int[] elementData = IntArrayList.this.elementData;
					if (offset + i >= elementData.length)
						throw new ConcurrentModificationException();
					cursor = i;
					return elementData[offset + (lastRet = i)];
				}

				@Override
				public void forEachRemaining(Consumer<? super Integer> consumer) {
					Objects.requireNonNull(consumer);
					final int size = IntArrayList.SubList.this.size;
					int i = cursor;
					if (i >= size) {
						return;
					}
					final int[] elementData = IntArrayList.this.elementData;
					if (offset + i >= elementData.length) {
						throw new ConcurrentModificationException();
					}
					while (i != size && modCount == expectedModCount) {
						consumer.accept(elementData[offset + (i++)]);
					}
					// update once at end of iteration to reduce heap write traffic
					lastRet = cursor = i;
					checkForComodification();
				}

				@Override
				public int nextIndex() {
					return cursor;
				}

				@Override
				public int previousIndex() {
					return cursor - 1;
				}

				@Override
				public void remove() {
					if (lastRet < 0)
						throw new IllegalStateException();
					checkForComodification();

					try {
						IntArrayList.SubList.this.remove(lastRet);
						cursor = lastRet;
						lastRet = -1;
						expectedModCount = IntArrayList.this.modCount;
					} catch (IndexOutOfBoundsException ex) {
						throw new ConcurrentModificationException();
					}
				}

				@Override
				public void set(Integer e) {
					if (lastRet < 0)
						throw new IllegalStateException();
					checkForComodification();

					try {
						IntArrayList.this.set(offset + lastRet, e);
					} catch (IndexOutOfBoundsException ex) {
						throw new ConcurrentModificationException();
					}
				}

				@Override
				public void add(Integer e) {
					checkForComodification();

					try {
						int i = cursor;
						IntArrayList.SubList.this.add(i, e);
						cursor = i + 1;
						lastRet = -1;
						expectedModCount = IntArrayList.this.modCount;
					} catch (IndexOutOfBoundsException ex) {
						throw new ConcurrentModificationException();
					}
				}

				final void checkForComodification() {
					if (expectedModCount != IntArrayList.this.modCount)
						throw new ConcurrentModificationException();
				}
			};
		}

		@Override
		public List<Integer> subList(int fromIndex, int toIndex) {
			subListRangeCheck(fromIndex, toIndex, size);
			return new IntArrayList.SubList(this, offset, fromIndex, toIndex);
		}

		private void rangeCheck(int index) {
			if (index < 0 || index >= this.size)
				throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
		}

		private void rangeCheckForAdd(int index) {
			if (index < 0 || index > this.size)
				throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
		}

		private String outOfBoundsMsg(int index) {
			return "Index: " + index + ", Size: " + this.size;
		}

		private void checkForComodification() {
			if (IntArrayList.this.modCount != this.modCount)
				throw new ConcurrentModificationException();
		}

		@Override
		public Spliterator<Integer> spliterator() {
			checkForComodification();
			return new IntArrayList.ArrayListSpliterator(
					IntArrayList.this, offset,
					offset + this.size, this.modCount);
		}
	}


	@Deprecated
	@Override
	public void forEach(Consumer<? super Integer> action) {
		Objects.requireNonNull(action);
		final int expectedModCount = modCount;
		final int[] elementData = this.elementData;
		final int size = this.size;
		for (int i = 0; modCount == expectedModCount && i < size; i++) {
			action.accept(elementData[i]);
		}
		if (modCount != expectedModCount) {
			throw new ConcurrentModificationException();
		}
	}


	public void forEach(IntConsumer action) {
		Objects.requireNonNull(action);
		final int expectedModCount = modCount;
		final int[] elementData = this.elementData;
		final int size = this.size;
		for (int i = 0; modCount == expectedModCount && i < size; i++) {
			action.accept(elementData[i]);
		}
		if (modCount != expectedModCount) {
			throw new ConcurrentModificationException();
		}
	}

	@Deprecated
	@Override
	public Spliterator<Integer> spliterator() {
		return new ArrayListSpliterator(this, 0, -1, 0);
	}

	public Spliterator.OfInt spliteratorOfInt() {
		return new IntArrayListSpliterator(this, 0, -1, 0);
	}

	@Deprecated
	static final class ArrayListSpliterator implements Spliterator<Integer> {
		private final IntArrayList list;
		private int index; // current index, modified on advance/split
		private int fence; // -1 until used; then one past last index
		private int expectedModCount; // initialized when fence set

		ArrayListSpliterator(IntArrayList list, int origin, int fence,
							 int expectedModCount) {
			this.list = list; // OK if null unless traversed
			this.index = origin;
			this.fence = fence;
			this.expectedModCount = expectedModCount;
		}

		private int getFence() { // initialize fence to size on first use
			int hi; // (a specialized variant appears in method forEach)
			IntArrayList lst;
			if ((hi = fence) < 0) {
				if ((lst = list) == null)
					hi = fence = 0;
				else {
					expectedModCount = lst.modCount;
					hi = fence = lst.size;
				}
			}
			return hi;
		}

		@Override
		public IntArrayList.ArrayListSpliterator trySplit() {
			int hi = getFence(), lo = index, mid = (lo + hi) >>> 1;
			return (lo >= mid) ? null : // divide range in half unless too small
					new IntArrayList.ArrayListSpliterator(list, lo, index = mid,
							expectedModCount);
		}


		@Override
		public boolean tryAdvance(Consumer<? super Integer> action) {
			if (action == null)
				throw new NullPointerException();
			int hi = getFence(), i = index;
			if (i < hi) {
				index = i + 1;
				java.lang.Integer e = list.elementData[i];
				action.accept(e);
				if (list.modCount != expectedModCount)
					throw new ConcurrentModificationException();
				return true;
			}
			return false;
		}


		@Override
		public void forEachRemaining(Consumer<? super Integer> action) {
			int i, hi, mc; // hoist accesses and checks from loop
			IntArrayList lst;
			int[] a;
			if (action == null)
				throw new NullPointerException();
			if ((lst = list) != null && (a = lst.elementData) != null) {
				if ((hi = fence) < 0) {
					mc = lst.modCount;
					hi = lst.size;
				} else
					mc = expectedModCount;
				if ((i = index) >= 0 && (index = hi) <= a.length) {
					for (; i < hi; ++i) {
						Integer e = a[i];
						action.accept(e);
					}
					if (lst.modCount == mc)
						return;
				}
			}
			throw new ConcurrentModificationException();
		}

		@Override
		public long estimateSize() {
			return (long) (getFence() - index);
		}

		@Override
		public int characteristics() {
			return Spliterator.ORDERED | Spliterator.SIZED | Spliterator.SUBSIZED;
		}
	}

	static final class IntArrayListSpliterator implements Spliterator.OfInt {
		private final IntArrayList list;
		private int index; // current index, modified on advance/split
		private int fence; // -1 until used; then one past last index
		private int expectedModCount; // initialized when fence set

		IntArrayListSpliterator(IntArrayList list, int origin, int fence,
								int expectedModCount) {
			this.list = list; // OK if null unless traversed
			this.index = origin;
			this.fence = fence;
			this.expectedModCount = expectedModCount;
		}

		private int getFence() { // initialize fence to size on first use
			int hi; // (a specialized variant appears in method forEach)
			IntArrayList lst;
			if ((hi = fence) < 0) {
				if ((lst = list) == null)
					hi = fence = 0;
				else {
					expectedModCount = lst.modCount;
					hi = fence = lst.size;
				}
			}
			return hi;
		}

		@Override
		public IntArrayList.IntArrayListSpliterator trySplit() {
			int hi = getFence(), lo = index, mid = (lo + hi) >>> 1;
			return (lo >= mid) ? null : // divide range in half unless too small
					new IntArrayList.IntArrayListSpliterator(list, lo, index = mid,
							expectedModCount);
		}


		@Override
		public boolean tryAdvance(IntConsumer action) {
			if (action == null)
				throw new NullPointerException();
			int hi = getFence(), i = index;
			if (i < hi) {
				index = i + 1;
				java.lang.Integer e = list.elementData[i];
				action.accept(e);
				if (list.modCount != expectedModCount)
					throw new ConcurrentModificationException();
				return true;
			}
			return false;
		}


		@Override
		public void forEachRemaining(IntConsumer action) {
			int i, hi, mc; // hoist accesses and checks from loop
			IntArrayList lst;
			int[] a;
			if (action == null)
				throw new NullPointerException();
			if ((lst = list) != null && (a = lst.elementData) != null) {
				if ((hi = fence) < 0) {
					mc = lst.modCount;
					hi = lst.size;
				} else
					mc = expectedModCount;
				if ((i = index) >= 0 && (index = hi) <= a.length) {
					for (; i < hi; ++i) {
						Integer e = a[i];
						action.accept(e);
					}
					if (lst.modCount == mc)
						return;
				}
			}
			throw new ConcurrentModificationException();
		}

		@Override
		public long estimateSize() {
			return (long) (getFence() - index);
		}

		@Override
		public int characteristics() {
			return Spliterator.ORDERED | Spliterator.SIZED | Spliterator.SUBSIZED;
		}
	}


	@Deprecated
	@Override
	public boolean removeIf(Predicate<? super Integer> filter) {
		Objects.requireNonNull(filter);
		// figure out which elements are to be removed
		// any exception thrown from the filter predicate at this stage
		// will leave the collection unmodified
		int removeCount = 0;
		final BitSet removeSet = new BitSet(size);
		final int expectedModCount = modCount;
		final int size = this.size;
		for (int i = 0; modCount == expectedModCount && i < size; i++) {
			final Integer element = elementData[i];
			if (filter.test(element)) {
				removeSet.set(i);
				removeCount++;
			}
		}
		if (modCount != expectedModCount) {
			throw new ConcurrentModificationException();
		}

		// shift surviving elements left over the spaces left by removed elements
		final boolean anyToRemove = removeCount > 0;
		if (anyToRemove) {
			final int newSize = size - removeCount;
			for (int i = 0, j = 0; (i < size) && (j < newSize); i++, j++) {
				i = removeSet.nextClearBit(i);
				elementData[j] = elementData[i];
			}
			//			for (int k = newSize; k < size; k++) {
			//				elementData[k] = null;  // Let gc do its work
			//			}
			this.size = newSize;
			if (modCount != expectedModCount) {
				throw new ConcurrentModificationException();
			}
			modCount++;
		}

		return anyToRemove;
	}


	public boolean removeIf(IntPredicate filter) {
		Objects.requireNonNull(filter);
		// figure out which elements are to be removed
		// any exception thrown from the filter predicate at this stage
		// will leave the collection unmodified
		int removeCount = 0;
		final BitSet removeSet = new BitSet(size);
		final int expectedModCount = modCount;
		final int size = this.size;
		for (int i = 0; modCount == expectedModCount && i < size; i++) {
			final Integer element = elementData[i];
			if (filter.test(element)) {
				removeSet.set(i);
				removeCount++;
			}
		}
		if (modCount != expectedModCount) {
			throw new ConcurrentModificationException();
		}

		// shift surviving elements left over the spaces left by removed elements
		final boolean anyToRemove = removeCount > 0;
		if (anyToRemove) {
			final int newSize = size - removeCount;
			for (int i = 0, j = 0; (i < size) && (j < newSize); i++, j++) {
				i = removeSet.nextClearBit(i);
				elementData[j] = elementData[i];
			}
			//			for (int k = newSize; k < size; k++) {
			//				elementData[k] = null;  // Let gc do its work
			//			}
			this.size = newSize;
			if (modCount != expectedModCount) {
				throw new ConcurrentModificationException();
			}
			modCount++;
		}

		return anyToRemove;
	}

	@Deprecated
	@Override
	public void replaceAll(UnaryOperator<Integer> operator) {
		Objects.requireNonNull(operator);
		final int expectedModCount = modCount;
		final int size = this.size;
		for (int i = 0; modCount == expectedModCount && i < size; i++) {
			elementData[i] = operator.apply(elementData[i]);
		}
		if (modCount != expectedModCount) {
			throw new ConcurrentModificationException();
		}
		modCount++;
	}

	@Deprecated
	public void replaceAll(IntUnaryOperator operator) {
		Objects.requireNonNull(operator);
		final int expectedModCount = modCount;
		final int size = this.size;
		for (int i = 0; modCount == expectedModCount && i < size; i++) {
			elementData[i] = operator.applyAsInt(elementData[i]);
		}
		if (modCount != expectedModCount) {
			throw new ConcurrentModificationException();
		}
		modCount++;
	}

	// Arrays.sort does not support sorting an int[] using a Comparator, therefore we
	// use the method in AbstractList instead.
	//	@Override
	//	public void sort(Comparator<? super Integer> c) {
	//		final int expectedModCount = modCount;
	//		Arrays.sort( elementData, 0, size, c);
	//		if (modCount != expectedModCount) {
	//			throw new ConcurrentModificationException();
	//		}
	//		modCount++;
	//	}
}
