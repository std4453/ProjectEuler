package io.github.std4453.projecteuler;

import java.util.Stack;

/**
 *
 */
public class Test1 {
	static long count = 0;

	public static void main(String[] args) {
		int sum = 200;
		enumerate(1, sum, new Stack<>());
		System.out.println(count);
	}

	private static void enumerate(int min, int sum, Stack<Integer> stack) {
		stack.push(sum);
		action(stack);
		stack.pop();
		++count;

		for (int i = min, max = sum / 2; i <= max; ++i) {
			stack.push(i);
			enumerate(i, sum - i, stack);
			stack.pop();
		}
	}

	private static void action(Stack<Integer> partition) {
		++count;
	}
}
