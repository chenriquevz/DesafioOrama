package com.challengeorama.orama.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class FormatHelper {

    public String FloatToReais (Float number) {

        DecimalFormat formatter = new DecimalFormat("#,###.##");

        return formatter.format(number);

    }

    public String FloatToPercent (Float number) {

        DecimalFormat formatter = new DecimalFormat("#,##0.00");

        return formatter.format(number);

    }

}
