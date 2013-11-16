package com.kc.util;

import java.text.DecimalFormat;

public class QuotationUtil {

	public static String doubleToString(double value)
	{
		DecimalFormat myFormatter = new DecimalFormat("###.##");
		String output = myFormatter.format(value);
		return output;
	}
}
