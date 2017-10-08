package io.github.std4453.projecteuler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 *
 */
public class Problem022 {
	private static final Path NAMES_FILE = Paths.get("assets/p022_names.txt");

	public static void main(String[] args) throws IOException {
		int[] values = Stream.of(Files.readAllLines(NAMES_FILE)
				.get(0)
				.replace("\"", "")
				.split(","))
				.sorted()
				.mapToInt(str -> str.chars()
						.map(ch -> ch - 'A' + 1)
						.reduce(0, (a, b) -> a + b))
				.toArray();
		int sum = 0;
		for (int i = 0; i < values.length; ++i)
			sum += (i + 1) * values[i];
		System.out.println(sum);

		// Answer: 871198282
		// TODO: add explanations
	}
}
