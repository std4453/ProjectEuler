package io.github.std4453.projecteuler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.Math.*;

/**
 *
 */
public class Problem102 {
	private static final int TRIANGLES = 1000;
	private static final Stream<List<Integer>> COORDS;

	private static final double EPS = 1e-6;

	static {
		try {
			COORDS = Files.lines(Paths.get("assets/p102_triangles.txt"))
					.map(line -> Stream.of(line.split(","))
							.map(Integer::parseInt)
							.collect(Collectors.toList()));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) {
		System.out.println(COORDS.map(Problem102::containsOrigin)
				.mapToInt(b -> b ? 1 : 0)
				.sum());

		// Answer: 228
		// TODO: add explanations
	}

	private static boolean containsOrigin(List<Integer> coords) {
		int x1 = coords.get(0);
		int y1 = coords.get(1);
		int x2 = coords.get(2);
		int y2 = coords.get(3);
		int x3 = coords.get(4);
		int y3 = coords.get(5);

		double A = sqrt(pow(x2 - x1, 2) + pow(y2 - y1, 2));
		double B = sqrt(pow(x3 - x2, 2) + pow(y3 - y2, 2));
		double C = sqrt(pow(x1 - x3, 2) + pow(y1 - y3, 2));
		double a = sqrt(pow(x3, 2) + pow(y3, 2));
		double b = sqrt(pow(x1, 2) + pow(y1, 2));
		double c = sqrt(pow(x2, 2) + pow(y2, 2));

		double a1 = (pow(a, 2) + pow(b, 2) - pow(C, 2)) / a / b / 2;
		double a2 = (pow(b, 2) + pow(c, 2) - pow(A, 2)) / b / c / 2;
		double a3 = (pow(c, 2) + pow(a, 2) - pow(B, 2)) / c / a / 2;

		return abs(acos(a1) + acos(a2) + acos(a3) - PI * 2) < EPS;
	}
}
