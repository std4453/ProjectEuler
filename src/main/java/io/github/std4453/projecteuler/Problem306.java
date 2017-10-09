package io.github.std4453.projecteuler;

import java.util.BitSet;

/**
 * Problem #306: Paper-strip Game<br />
 * The following game is a classic example of Combinatorial Game Theory:<br />
 * Two players start with a strip of n white squares and they take alternate turns.<br />
 * On each turn, a player picks two contiguous white squares and paints them black.<br />
 * The first player who cannot make a move loses.<br />
 * If {@code n = 1}, there are no valid moves, so the first player loses automatically.
 * <br />
 * If {@code n = 2}, there is only one valid move, after which the second player
 * loses.<br />
 * If {@code n = 3}, there are two valid moves, but both leave a situation where the
 * second player loses.<br />
 * If {@code n = 4}, there are three valid moves for the first player; she can win the
 * game by painting the two middle squares.<br />
 * If {@code n = 5}, there are four valid moves for the first player (shown below in
 * red); but no matter what she does, the second player (blue) wins.<br />
 * <img src="https://projecteuler.net/project/images/p306_pstrip.gif" /><br />
 * So, for {@code 1 <= n <= 5}, there are 3 values of n for which the first player can
 * force a win.<br />
 * Similarly, for {@code 1 <= n <= 50}, there are 40 values of n for which the first
 * player can force a win.<br />
 * For {@code 1 <= n <= 1 000 000}, how many values of n are there for which the first
 * player can force a win?
 */
public class Problem306 {
	private static final int MAX = 1000000;

	public static void main(String[] args) {
		int count = 0;
		int[] table = new int[MAX + 1];

		for (int i = 2; i <= MAX; ++i) {
			BitSet set = new BitSet();
			for (int j = 0, l = i - 2; j < i / 2; ++j, --l)
				set.set(table[j] ^ table[l]);

			if (set.get(0)) ++count;
			table[i] = set.nextClearBit(0);

			if (i % 10000 == 0) System.out.printf("%d%% completed\n", i / 10000);
		}

		System.out.printf("The final answer is %d\n", count);

		// Runs for about 22 minutes on my computer.
		// The program outputs the current program every 10000 iterations, therefore
		// please be patient and wait until it terminates.

		// Answer: 852938
		// TODO: add explanations
	}
}
