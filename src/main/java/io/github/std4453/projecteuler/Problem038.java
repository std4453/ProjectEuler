package io.github.std4453.projecteuler;

import java.util.stream.IntStream;

import static io.github.std4453.projecteuler.utils.StreamUtils.limitUntil;

/**
 * Problem #38: Pandigital multiples<br />
 * Take the number 192 and multiply it by each of 1, 2, and 3:<br />
 * <pre><code>
 * 192 × 1 = 192
 * 192 × 2 = 384
 * 192 × 3 = 576
 * </code></pre>
 * By concatenating each product we get the 1 to 9 pandigital, {@code 192384576}. We will
 * call {@code 192384576} the concatenated product of {@code 192} and {@code (1,2,3)}
 * <br /> The same can be achieved by starting with 9 and multiplying by
 * {@code 1, 2, 3, 4}, and 5, giving the pandigital, {@code 918273645}, which is the
 * concatenated product of 9 and {@code (1,2,3,4,5)}.<br />
 * What is the largest 1 to 9 pandigital 9-digit number that can be formed as the
 * concatenated product of an integer with {@code (1,2, ... , n)} where {@code n > 1}?
 */
public class Problem038 {
	// Although this problem can also be solved using brute force, let's try to make it
	// easier using some mathematical analysis.

	// We already know the one of the largest possible pandigital 9-digit number is
	// 918273645, if we want to find a bigger one, it has to start with 9, which means
	// that if the bigger pandigital 9-digit number is product of a with (1, 2, ... n),
	// a must also start with 9.
	// If a is 1-digit, then a is 9, nope.
	// If a is 2-digit, then there exists no integer n so that 1 * a, 2 * a, ... n * a
	// contain altogether 9 digits.
	// For the same reason, a cannot have 3 digits.
	// And obviously a cannot have 5 or more digits, so a must have 4 digits, being
	// 9ABC, and n must be 2.
	// 2 * a starts with 18 / 19, however since 9 is already used, it must start from
	// 18 so ABC * 2 < 1000.
	// We can even have that ABC * 2 < 800.
	// So now we only have to iterate through 236 to 376 to find our desired ABC.
	// And, iterating reversely, we can output the result on the first pandigital
	// 9-digit number 9ABC18(2ABC), since it must be bigger than any other pandigital
	// number.

	// @see Problem032
	private static boolean checkDigits(int n, boolean[] used) {
		while (n != 0) {
			int digit = n % 10;
			if (used[digit]) return false;
			used[digit] = true;
			n /= 10;
		}
		return true;
	}

	// @see Problem032
	private static boolean allUsed(boolean[] used) {
		for (int i = 1; i < 10; ++i) if (!used[i]) return false;
		return true;
	}

	private static boolean isPandigital(int abc) {
		boolean[] used = new boolean[10];
		return checkDigits(9000 + abc, used) &&
				checkDigits(18000 + (abc << 1), used) &&
				allUsed(used);
	}

	public static void main(String[] args) {
		limitUntil(IntStream.iterate(376, n -> n - 1),  // iterate reversely from 376
				n -> n < 236)  // >= 236
				.filter(Problem038::isPandigital)  // that are pandigital
				.findFirst()  // the first one
				.ifPresent(n -> System.out.printf("9%d18%d\n", n, n << 1));  // print

		// Answer: 932718654
		// Complexity: Without a input there is no complexity.

		// TODO: add homework
	}
}
