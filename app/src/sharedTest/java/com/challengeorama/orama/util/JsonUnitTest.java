package com.challengeorama.orama.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import okio.BufferedSource;
import okio.Okio;

public class JsonUnitTest {


    public String readJsonFromAsset(String filename) {

        try {
            InputStream input = getClass().getClassLoader().getResourceAsStream("responses/" + filename);
            BufferedSource source = Okio.buffer(Okio.source(input));
            return source.readByteString().string(Charset.forName("utf-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

}
