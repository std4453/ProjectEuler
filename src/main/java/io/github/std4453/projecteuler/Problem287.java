package io.github.std4453.projecteuler;

/**
 *
 */
public class Problem287 {
	private static final int N = 24;
	private static final int MAX = 1 << N;
	private static final int MID = 1 << (N - 1);
	private static final long DIST_SQUARE = 1L << ((N - 1) << 1);

	private static int calc(int x1, int y1, int x2, int y2) {
		if (x2 - x1 == 1) return 2;
		long dx1 = x1 - MID, dx2 = x2 - 1 - MID;
		long dy1 = y1 - MID, dy2 = y2 - 1 - MID;
		long px1 = dx1 * dx1, px2 = dx2 * dx2, py1 = dy1 * dy1, py2 = dy2 * dy2;
		boolean bL = px1 + py1 <= DIST_SQUARE;
		boolean bR = px2 + py1 <= DIST_SQUARE;
		boolean tR = px2 + py2 <= DIST_SQUARE;
		boolean tL = px1 + py2 <= DIST_SQUARE;
		if (bL == bR && bR == tR && tR == tL) return 2;

		int midX = (x1 + x2) >> 1;
		int midY = (y1 + y2) >> 1;
		return 1 +
				calc(x1, y1, midX, midY) +
				calc(midX, y1, x2, midY) +
				calc(midX, midY, x2, y2) +
				calc(x1, midY, midX, y2);
	}

	public static void main(String[] args) {
		System.out.println(1 +
				calc(0, 0, MID, MID) +
				calc(MID, 0, MAX, MID) +
				calc(MID, MID, MAX, MAX) +
				calc(0, MID, MID, MAX));

		// Answer: 313135496
		// TODO: add explanations
	}
}
