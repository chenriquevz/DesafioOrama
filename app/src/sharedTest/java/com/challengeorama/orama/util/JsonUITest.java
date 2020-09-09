package com.challengeorama.orama.util;

import android.app.Application;
import android.content.res.AssetManager;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import okio.BufferedSource;
import okio.Okio;

public class JsonUITest {


    public String readJsonFromAsset(Application app, String filename) {

        try {
            InputStream input = ((AssetManager) app.getAssets()).open(filename);
            BufferedSource source = Okio.buffer(Okio.source(input));
            return source.readByteString().string(Charset.forName("utf-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

}
