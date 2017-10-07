package io.github.std4453.projecteuler;

import io.github.std4453.projecteuler.utils.IntFactorized;
import io.github.std4453.projecteuler.utils.NumberTheory;

import java.util.stream.IntStream;

/**
 * Problem #5: Smallest multiple<br />
 * 2520 is the smallest number that can be divided by each of the numbers from 1 to 10
 * without any remainder.<br />
 * What is the smallest positive number that is evenly divisible by all of the numbers
 * from 1 to 20?
 */
public class Problem005 {
	private static final int START_INCLUSIVE = 1;
	private static final int END_INCLUSIVE = 20;

	public static void main(String[] args) {
		// TODO: use a better algorithm

		IntStream.rangeClosed(START_INCLUSIVE, END_INCLUSIVE)  // numbers
				.mapToObj(NumberTheory::factorize)  // factorize them
				.reduce(IntFactorized::newLCM)  // calculate LCM
				.map(IntFactorized::getNumber)  // calculate final result
				.ifPresent(System.out::println);  // print the result

		// Answer: 232792560
		// Complexity: about O(n^2)
		// Complexity of factorisation algorithm is difficult to determine. See:
		// https://en.wikipedia.org/wiki/Integer_factorization#Difficulty_and_complexity

		// Homework:
		// For the following code:
		// IntPredicate isDividable =
		// 		IntStream.rangeClosed(START_INCLUSIVE, END_INCLUSIVE)  // numbers
		// 				.mapToObj(i -> (IntPredicate) (n -> n % i == 0))  // dividable
		// 				.reduce(IntPredicate::and)  // when all satisfied
		// 				.orElseThrow(() -> new RuntimeException("WTF"));  // must exist
		// IntStream.iterate(1, n -> n + 1)
		// 		.filter(isDividable)
		// 		.findFirst()
		// 		.ifPresent(System.out::println);
		// Calculate its complexity.
	}
}
