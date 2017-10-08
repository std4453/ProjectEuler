package io.github.std4453.projecteuler;

import io.github.std4453.projecteuler.utils.Primes;

import static io.github.std4453.projecteuler.utils.StreamUtils.limitUntil;

/**
 *
 */
public class Test2 {
	//	static int sum = 0;

	public static void main(String[] args) {
		//		Primes.intPrimesStream()
		//				.peek(p -> sum += p)
		//				.filter(p -> sum >= 350)
		//				.findFirst()
		//				.ifPresent(System.out::println);

		limitUntil(Primes.longPrimesStream(),
				p -> p >= 53)
				.reduce((a, b) -> a * b)
				.ifPresent(System.out::println);
	}
}
