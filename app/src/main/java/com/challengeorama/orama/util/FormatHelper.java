package com.challengeorama.orama.util;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FormatHelper {

    public String FloatToReais(Float number) {


        if (number != null) {
            DecimalFormat formatter = new DecimalFormat("#,###.##");

            return formatter.format(number);
        } else {
            return "0";
        }


    }

    public String FloatToPercent(Float number) {

        if (number != null && number != 0f) {
            DecimalFormat formatter = new DecimalFormat("#,##0.0000");

            return formatter.format(number);
        } else {
            return "0,00";
        }

    }


    public String StringToDateString(String date) {

        try {


            if (date.length() < 11 ){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                Date newDate = sdf.parse(date);

                SimpleDateFormat newSdf = new SimpleDateFormat("dd/MM/yy", Locale.getDefault());
                return newSdf.format(newDate);
            } else {
                return null;
            }
        } catch (ParseException ex) {
            return null;
        }
    }


}
