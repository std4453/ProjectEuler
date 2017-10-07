package io.github.std4453.projecteuler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 *
 */
public class Problem067 {
	private static final int ROWS = 100;
	private static final int[][] NUMBERS = new int[100][];

	static {
		try {
			List<String> lines = Files.readAllLines(
					Paths.get("assets/p067_triangle.txt"));
			for (int i = 0; i < ROWS; ++i) {
				List<Integer> numbers = Stream.of(lines.get(i).split(" "))
						.map(Integer::parseInt)
						.collect(Collectors.toList());
				NUMBERS[i] = new int[i + 1];
				for (int j = 0; j <= i; ++j)
					NUMBERS[i][j] = numbers.get(j);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) {
		int[] maximums = new int[ROWS];
		for (int i = 0; i < ROWS; ++i)
			for (int j = i; j >= 0; --j)
				if (i == 0) maximums[j] = NUMBERS[i][j];
				else if (j == 0) maximums[j] = maximums[j] + NUMBERS[i][j];
				else maximums[j] = Math.max(maximums[j], maximums[j - 1]) + NUMBERS[i][j];
		System.out.println(IntStream.of(maximums).max().orElse(0));

		// Answer: 7273
		// TODO: add explanations
	}
}
