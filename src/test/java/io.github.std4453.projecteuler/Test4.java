package io.github.std4453.projecteuler;

import io.github.std4453.projecteuler.utils.BigDecimalSqrt;
import io.github.std4453.projecteuler.utils.LongArrayList;

import java.math.BigDecimal;

/**
 *
 */
public class Test4 {
	private static final int SCALE = 1000;

	private static class ContinuedFraction {
		LongArrayList conv;
		LongArrayList deno;
		LongArrayList frac;

		ContinuedFraction() {
			this.conv = new LongArrayList();
			this.deno = new LongArrayList();
			this.frac = new LongArrayList();
		}

		int size() {
			return this.conv.size();
		}
	}

	private static ContinuedFraction findContinuedFraction(int maxCF, BigDecimal x) {
		ContinuedFraction fraction = new ContinuedFraction();
		LongArrayList conv = fraction.conv;
		LongArrayList deno = fraction.deno;
		LongArrayList frac = fraction.frac;

		// The first two convergents are 0/1 and 1/0
		deno.add(0);
		frac.add(1);
		deno.add(1);
		frac.add(0);

		conv.add(0);
		conv.add(0);

		// The rest of the convergents (and continued fraction)
		for (int i = 2; i < maxCF; ++i) {
			conv.add(x.longValue());
			deno.add(conv.getLong(i) * deno.getLong(i - 1) + deno.getLong(i - 2));
			frac.add(conv.getLong(i) * frac.getLong(i - 1) + frac.getLong(i - 2));

			x = BigDecimal.ONE.divide(x.subtract(BigDecimal.valueOf(conv.getLong(i))),
					SCALE, BigDecimal.ROUND_HALF_EVEN);
		}

		return fraction;
	}

	private static LongArrayList findAllConvergents(
			ContinuedFraction fraction, BigDecimal x) {
		LongArrayList conv = fraction.conv;
		LongArrayList deno = fraction.deno;
		LongArrayList frac = fraction.frac;
		int len = fraction.size() - 1;

		LongArrayList convergents = new LongArrayList();

		for (int i = 2; i < len; ++i) {
			long n = conv.getLong(i + 1);
			long cp = (n >> 1) * deno.getLong(i) + deno.getLong(i - 1);
			long cq = (n >> 1) * frac.getLong(i) + frac.getLong(i - 1);
			if ((n & 1) == 0) {
				convergents.add(cp);
				convergents.add(cq);
			}

			// And print all the rest, no need to test
			for (n = (int) ((conv.getLong(i + 1) >> 1) + 1);
				 n <= conv.getLong(i + 1); ++n) {
				convergents.add(n * deno.getLong(i) + deno.getLong(i - 1));
				convergents.add(n * frac.getLong(i) + frac.getLong(i - 1));
			}
		}

		return convergents;
	}

	private static LongArrayList findConvergents(int maxCF, BigDecimal x) {
		ContinuedFraction fraction = findContinuedFraction(maxCF, x);
		return findAllConvergents(fraction, x);
	}

	public static void main(String[] args) {
		BigDecimal pi = new BigDecimal(
				"3.14159265358979323846264338327950288419716939937510582097494459230781" +
						"6406286208998628034825342117067982148086513282306647093844609550582231" +
						"7253594081284811174502841027019385211055596446229489549303819644288109" +
						"7566593344612847564823378678316527120190914564856692346034861045432664" +
						"8213393607260249141273724587006606315588174881520920962829254091715364" +
						"3678925903600113305305488204665213841469519415116094330572703657595919" +
						"5309218611738193261179310511854807446237996274956735188575272489122793" +
						"8183011949129833673362440656643086021394946395224737190702179860943702" +
						"7705392171762931767523846748184676694051320005681271452635608277857713" +
						"4275778960917363717872146844090122495343014654958537105079227968925892" +
						"3542019956112129021960864034418159813629774771309960518707211349999998" +
						"3729780499510597317328160963185950244594553469083026425223082533446850" +
						"3526193118817101000313783875288658753320838142061717766914730359825349" +
						"0428755468731159562863882353787593751957781857780532171226806613001927" +
						"876611195909216420199");
		BigDecimal sqrt2 = BigDecimalSqrt.bigSqrt(BigDecimal.valueOf(2));
		LongArrayList convergents = findConvergents(40, sqrt2);
		for (int i = 0, size = convergents.size(); i < size; i += 2)
			System.out.printf("%d/%d\n",
					convergents.getLong(i), convergents.getLong(i + 1));

		//		LongStream.rangeClosed(2, 99)
		//				.filter(n -> pow(Math.round(Math.sqrt(n)), 2) != n)
		//				.mapToObj(BigDecimal::valueOf)
		//				.map(BigDecimalSqrt::bigSqrt)
		//				.forEach(n -> findConvergents(40, n));
	}
}

