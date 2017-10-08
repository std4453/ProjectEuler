package io.github.std4453.projecteuler.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Compute the square root of a {@link BigDecimal}, up to precision of 1000 digits.
 * (actually about 1570)<br />
 * The code is from
 * <a href="https://stackoverflow.com/questions/13649703/square-root-of-bigdecimal-in-java">Stackoverflow</a>.
 */
public class BigDecimalSqrt {
	private static final BigDecimal SQRT_DIG = new BigDecimal(1000);
	private static final BigDecimal SQRT_PRE =
			new BigDecimal(10).pow(SQRT_DIG.intValue());

	/**
	 * Private utility method used to compute the square root of a BigDecimal.
	 *
	 * @author Luciano Culacciatti
	 * @url http://www.codeproject.com/Tips/257031/Implementing-SqrtRoot-in-BigDecimal
	 */
	private static BigDecimal sqrtNewtonRaphson(BigDecimal c, BigDecimal xn,
												BigDecimal precision) {
		BigDecimal fx = xn.pow(2).add(c.negate());
		BigDecimal fpx = xn.multiply(new BigDecimal(2));
		BigDecimal xn1 = fx.divide(fpx, 2 * SQRT_DIG.intValue(), RoundingMode.HALF_DOWN);
		xn1 = xn.add(xn1.negate());
		BigDecimal currentSquare = xn1.pow(2);
		BigDecimal currentPrecision = currentSquare.subtract(c);
		currentPrecision = currentPrecision.abs();
		if (currentPrecision.compareTo(precision) <= -1) {
			return xn1;
		}
		return sqrtNewtonRaphson(c, xn1, precision);
	}

	/**
	 * Uses Newton Raphson to compute the square root of a BigDecimal.
	 *
	 * @author Luciano Culacciatti
	 * @url http://www.codeproject.com/Tips/257031/Implementing-SqrtRoot-in-BigDecimal
	 */
	@SuppressWarnings("BigDecimalMethodWithoutRoundingCalled")
	public static BigDecimal bigSqrt(BigDecimal c) {
		return sqrtNewtonRaphson(c, new BigDecimal(1),
				new BigDecimal(1).divide(SQRT_PRE));
	}
}
