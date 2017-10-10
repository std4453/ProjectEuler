package io.github.std4453.projecteuler;

import io.github.std4453.projecteuler.utils.LongFactorized;

import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.LongStream;

/**
 * Problem #15: Lattice paths<br />
 * Starting in the top left corner of a 2×2 grid, and only being able to move to the right
 * and down, there are exactly 6 routes to the bottom right corner.<br />
 * <img src="https://projecteuler.net/project/images/p015.gif" /><br />
 * How many such routes are there through a 20×20 grid?
 */
public class Problem015 {
	private static final int X = 20;
	private static final int Y = 20;

	// In fact this is a very classic problem.
	// Just count by hand how many routes are there to reach point (x, y):
	// 1  1  1  1  1  ...
	// 1  2  3  4  5  ...
	// 1  3  6  10 15 ...
	// 1  4  10 20 30 ...
	// 1  5  20 40 70 ...
	// ...

	// You might recognize the pattern that:
	// f(x, y) = f(x - 1, y) + f(x, y - 1) for all x, y >= 1
	// You're right, and it is also easily proved: To reach point (x, y) only moving
	// right and down, the last point on your route must be (x - 1, y) or (x, y - 1).
	// Obviously f(0, y) and f(x, 0) are 1, so we can construct the previous matrix.

	// But what more? How can we translate the recursive formula into closed-form
	// formula?
	// To get point (x, y), you have to go x steps right and y steps down, altogether
	// (x + y) steps, but their order is arbitrary: To get (2, 2), route
	// right-down-down-right and down-right-down-right are both valid.
	// Imagine (x + y) squares painted white placed in a row, how many ways are there
	// to paint exactly x squares of them black? Hence we define function C(x + y, x) to
	// be the answer.

	// To step deeper into this problem, please go to
	// https://en.wikipedia.org/wiki/Combination.
	// Here we just need the closed-form formula of C(n, k):
	// C(n, k) = n! / (k! * (n - k)!) = n * (n - 1) * ... * (n - k + 1) / k!
	// which evaluation is implemented below.

	private static long c(int n, int k) {
		// Here we first factorize the numbers before multiplying them together because
		// a) 20! exceeds max value of long b) factorized multiplication & division is
		// faster than that of BigInteger

		Supplier<RuntimeException> orElse = RuntimeException::new;  // unexpected
		return Objects.requireNonNull(LongFactorized.divide(  // divide
				LongStream.rangeClosed(n - k + 1, n)  // from n - k + 1 to n
						.mapToObj(LongFactorized::of)  // factorize
						.reduce(LongFactorized::newMult)  // multiply
						.orElseThrow(orElse),  // unexpected
				LongStream.rangeClosed(1, k)  // k!
						.mapToObj(LongFactorized::of)  // factorize
						.reduce(LongFactorized::newMult)  // multiply
						.orElseThrow(orElse)))  // unexpected
				.getNumber();  // LongFactorized -> long
	}

	public static void main(String[] args) {
		System.out.println(c(X + Y, X));

		// Answer: 137846528820
		// Complexity: O(X + Y)

		// Homework:
		// 1. Write a version that constructs the matrix of route count using the
		// recursive formula f(x, y) = f(x - 1, y) + f(x, y - 1).

		// 2. How many routes are there to start from (0, 0, 0) to (x, y, z) in a
		// 3-dimensional Cartesian space going only right, down and forward
		// (i.e. from (x, y, z) to (x, y, z + 1))? What about in an n-dimensional space?
	}
}
