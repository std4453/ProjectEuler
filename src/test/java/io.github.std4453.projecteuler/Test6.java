package io.github.std4453.projecteuler;

import java.util.stream.LongStream;

import static io.github.std4453.projecteuler.utils.MathsHelper.pow;

/**
 *
 */
public class Test6 {
	private static final long[] MASKS = {
			0x5555555555555555L,
			0x3333333333333333L,
			0x0F0F0F0F0F0F0F0FL,
			0x00FF00FF00FF00FFL,
			0x0000FFFF0000FFFFL,
			0x00000000FFFFFFFFL
	};

	public static void main(String[] args) {
		LongStream.rangeClosed(1, 18)
				.map(n -> pow(10, n))
				.map(n -> {
					for (int i = 0; i < MASKS.length; ++i)
						n = (n & MASKS[i]) + ((n >> (1 << i)) & MASKS[i]);
					return n;
				}).forEach(System.out::println);
	}
}
