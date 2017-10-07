package io.github.std4453.projecteuler;

import io.github.std4453.projecteuler.utils.Primes;

import static io.github.std4453.projecteuler.utils.StreamUtils.limitUntil;

/**
 * Problem #3: Largest prime factor<br />
 * The prime factors of 13195 are 5, 7, 13 and 29.<br />
 * What is the largest prime factor of the number 600851475143 ?
 */
public class Problem003 {
	private static final long N = 600851475143L; // 600851475143 > Integer.MAX_VALUE

	// because we want to change value of n inside lambdas, it has to be a field
	private static long n = N;

	// If you haven't solved Homework 2 yet, I recommend you to take a look at it
	// before reading the answer.

	// The problem sounds easy enough: Fetch all primes below N that can divide N and
	// get the maximum, however it gets tricky if the maximum prime is a very very big
	// one, like 100000073. Even if you are clever enough to write a program to
	// generate that big primes, it's not very clever to actually do so.
	// Because every number has maximum 1 prime factor that is greater than its square
	// root. (no need to explain, is there?) So if we divide all the prime factors
	// under the square root of n, the remaining number must either be 1 or that only
	// big prime factor, and that's exactly our final answer does.

	public static void main(String[] args) {
		long sqrt = Math.round(Math.sqrt(N));  // square root
		System.out.println(limitUntil(Primes.longPrimesStream(),  // all primes
				n -> n > sqrt)  // less than square root
				.filter(n -> N % n == 0)  // that can divide N
				.peek(p -> {
					while (n % p == 0) n /= p;  // remove prime factor p from n
				})
				.filter(p -> n == 1)  // if remaining 1
				.findFirst()  // then the last prime p is the answer
				.orElseGet(() -> n));  // otherwise the remaining number is the answer

		// Answer: 6857
		// Complexity: O(sqrt(n) / log(n))
		//     (we traverse through all the primes below sqrt(n), which makes it
		//     sqrt(n)/log(sqrt(n)) = 2 * sqrt(n) / log(n))

		// Homework:
		// 1. As a test, I first wrote the following code:
		// limitUntil(NumberTheory.longPrimesStream()  // all primes
		//			.filter(n -> 63 % n == 0),  // that can divide N
		// 		n -> n > 63)  // of course it must be smaller than or equal to N
		//		.max()  // and get the maximum
		//		.ifPresent(System.out::print);  // print the result
		// Obviously the answer should be 7, right?
		// But wait, why doesn't the program stop?

		// 2. I then wrote the following code:
		// NumberTheory.longPrimesStream()  // all primes
		//  		.filter(n -> N % n == 0)  // that can divide N
		//	    	.peek(p -> {  // remove prime factor p from n
		//				while (n % p == 0) n /= p;
		//			})
		//			.filter(p -> n == 1)  // until n gets 1
		//			.findFirst()  // that's the maximum prime factor
		//			.ifPresent(System.out::println);  // print the result
		// It works for N, but for some Ns (like 200000146) it will run very very slowly.
		// What's its problem? Try to answer without actually running the program.
		// (I recommend looking into NumberTheory.longPrimesStream())
	}
}
