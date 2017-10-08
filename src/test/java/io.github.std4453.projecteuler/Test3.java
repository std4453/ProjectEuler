package io.github.std4453.projecteuler;

/**
 *
 */
public class Test3 {
	// number of terms in continued fraction.
	// 15 is the max without precision errors for M_PI
	private static final int MAX = 15;
	private static final double eps = 1e-9;

	private static long[] p = new long[MAX],
			q = new long[MAX],
			a = new long[MAX];
	private static long len;

	private static void findContinuedFraction(double x) {
		// The first two convergents are 0/1 and 1/0
		p[0] = 0;
		q[0] = 1;
		p[1] = 1;
		q[1] = 0;
		// The rest of the convergents (and continued fraction)
		for (int i = 2; i < MAX; ++i) {
			a[i] = Math.round(Math.floor(x));
			p[i] = a[i] * p[i - 1] + p[i - 2];
			q[i] = a[i] * q[i - 1] + q[i - 2];
			System.out.printf("%d:  %d/%d\n", a[i], p[i], q[i]);
			len = i;

			if (Math.abs(x - a[i]) < eps) return;
			x = 1.0 / (x - a[i]);
		}
	}

	private static void findAllConvergents(double x) {
		for (int i = 2; i < len; ++i) {
			// Test n = a[i+1]/2 separately. Enough to test when a[i+1] is even, actually.
			int n = (int) (a[i + 1] / 2);
			long cp = n * p[i] + p[i - 1];
			long cq = n * q[i] + q[i - 1];
			if (Math.abs(x - (double) cp / cq) < Math.abs(x - (double) p[i] / q[i]))
				System.out.printf("%d/%d,", cp, cq);

			// And print all the rest, no need to test
			for (n = (int) ((a[i + 1] + 2) / 2); n <= a[i + 1]; ++n) {
				System.out.printf("%d/%d,", n * p[i] + p[i - 1], n * q[i] + q[i - 1]);
			}
		}
	}

	public static void main(String[] args) {
		double x;
		if (args.length == 0) {
			x = Math.PI;
		} else {
			x = Double.parseDouble(args[0]);
		}

		System.out.println(x);

		findContinuedFraction(x);
		findAllConvergents(x);
	}
}
