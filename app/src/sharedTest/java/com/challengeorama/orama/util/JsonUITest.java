package com.challengeorama.orama.util;

import android.app.Application;
import android.content.res.AssetManager;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import javax.inject.Inject;

import okio.BufferedSource;
import okio.Okio;

public class JsonUITest {


    Application app;

    @Inject
    public JsonUITest(Application app) {
        this.app = app;
    }


    public String readJsonFromAsset(String filename) {

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
