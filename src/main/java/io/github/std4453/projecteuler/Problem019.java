package io.github.std4453.projecteuler;

import java.util.Calendar;

/**
 *
 */
public class Problem019 {
	public static void main(String[] args) throws Exception {
		Calendar calendar = Calendar.getInstance();
		calendar.set(1900, 0, 7);
		int sundays = 0;
		while (calendar.get(Calendar.YEAR) <= 2000) {
			if (calendar.get(Calendar.YEAR) >= 1901 &&
					calendar.get(Calendar.DAY_OF_MONTH) == 1) ++sundays;
			calendar.add(Calendar.DAY_OF_MONTH, 7);
		}
		System.out.println(sundays);

		// Answer: 171
		// TODO: add explanations
	}
}
