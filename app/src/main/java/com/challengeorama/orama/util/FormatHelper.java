package com.challengeorama.orama.util;

import android.nfc.FormatException;

import androidx.annotation.Nullable;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FormatHelper {

    public String FloatToReais(@Nullable  Float number) {

        try {
            if (number == null) {
                DecimalFormat formatter = new DecimalFormat("#,###.##");

                return formatter.format(number);
            } else {
                return "0";
            }

        } catch (NullPointerException e) {
            return "0";
        }



    }

    public String FloatToPercent(@Nullable Float number) {

        try {
            if (number == null) {
                DecimalFormat formatter = new DecimalFormat("#,##0.00");

                return formatter.format(number);
            } else {
                return "0,00";
            }
        } catch (NullPointerException e) {
            return "0";
        }



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
