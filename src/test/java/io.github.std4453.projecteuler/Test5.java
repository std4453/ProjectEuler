package io.github.std4453.projecteuler;

import java.util.stream.IntStream;

/**
 *
 */
public class Test5 {
	public static void main(String[] args) {
		IntStream.iterate(1, n -> n + 1)
				.limit(10000)
				.filter(n -> (n ^ (n << 1) ^ (3 * n)) == 0)
				.forEach(n -> System.out.printf("%d = %s\n",
						n, Integer.toBinaryString(n)));
	}
}
