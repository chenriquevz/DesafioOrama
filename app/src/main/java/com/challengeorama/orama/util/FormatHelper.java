package com.challengeorama.orama.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FormatHelper {

    public String FloatToReais(Float number) {

        DecimalFormat formatter = new DecimalFormat("#,###.##");

        return formatter.format(number);

    }

    public String FloatToPercent(Float number) {

        DecimalFormat formatter = new DecimalFormat("#,##0.00");

        return formatter.format(number);

    }

    public String StringToDateString(String date) {

      try {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date newDate = sdf.parse(date);

            SimpleDateFormat newSdf = new SimpleDateFormat("dd/MM/yy", Locale.getDefault());
            return newSdf.format(newDate);

        } catch (ParseException ex) {
            return null;
        }


    }

}
