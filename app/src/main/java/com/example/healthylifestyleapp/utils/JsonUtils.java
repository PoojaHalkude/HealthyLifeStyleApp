package com.example.healthylifestyleapp.utils;

import android.content.Context;

import androidx.annotation.RawRes;

import com.crashlytics.android.Crashlytics;
import com.example.healthylifestyleapp.BuildConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

public final class JsonUtils {
    public static String readLocalJson(Context context, @RawRes int id) {
        InputStream is = context.getResources().openRawResource(id);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } catch (IOException e) {
            Crashlytics.logException(e);
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                Crashlytics.logException(e);
                if (BuildConfig.DEBUG) {
                    e.printStackTrace();
                }
            }
        }
        return writer.toString();
    }
}