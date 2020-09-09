package com.challengeorama.orama.utilTest;

import com.challengeorama.orama.util.FormatHelper;

import org.junit.Assert;
import org.junit.Test;

public class FormatHelperTests {


    @Test
    public void FloatToReaisTest_input_0 () {

        Float numberInTest = 0f;
        String result = new FormatHelper().FloatToReais(numberInTest);

        Assert.assertEquals("0", result);

    }

    @Test
    public void FloatToReaisTest_input_null () {

        Float numberInTest = null;
        String result = new FormatHelper().FloatToReais(numberInTest);

        Assert.assertEquals("0", result);

    }

    @Test
    public void FloatToReaisTest_input_valid () {

        float numberInTest = 1000.23f;
        String result = new FormatHelper().FloatToReais(numberInTest);

        Assert.assertEquals("1.000,23", result);

        numberInTest = 90.42f;
        result = new FormatHelper().FloatToReais(numberInTest);

        Assert.assertEquals("90,42", result);

        numberInTest = 1236813.882444f;
        result = new FormatHelper().FloatToReais(numberInTest);

        Assert.assertEquals("1.236.813,88", result);

    }


    @Test
    public void FloatToPercentTest_input_valid () {

        float numberInTest = 1000.2312f;
        String result = new FormatHelper().FloatToPercent(numberInTest);

        Assert.assertEquals("1.000,2312", result);

        numberInTest = 90.421212f;
        result = new FormatHelper().FloatToPercent(numberInTest);

        Assert.assertEquals("90,4212", result);

        numberInTest = 1.000000000f;
        result = new FormatHelper().FloatToPercent(numberInTest);

        Assert.assertEquals("1,0000", result);

    }

    @Test
    public void FloatToPercentTest_input_null () {

        Float numberInTest = null;
        String result = new FormatHelper().FloatToPercent(numberInTest);

        Assert.assertEquals("0,00", result);

    }

    @Test
    public void FloatToPercentTest_input_zero () {

        Float numberInTest = 0f;
        String result = new FormatHelper().FloatToPercent(numberInTest);

        Assert.assertEquals("0,00", result);

    }

    @Test
    public void StringToDateStringTest_input_valid_invalid () {

        String numberInTest = "2020-08-09";
        String result = new FormatHelper().StringToDateString(numberInTest);

        Assert.assertEquals("09/08/20", result);

        numberInTest = "2020-08-091";
        result = new FormatHelper().StringToDateString(numberInTest);

        Assert.assertNull(result);

        numberInTest = "blalas";
        result = new FormatHelper().StringToDateString(numberInTest);

        Assert.assertNull(result);

    }



}
