package com.feamorx.cookebook.utils;

import android.content.Context;
import android.util.Log;

import com.feamorx.cookebook.model.MasterData;
import com.feamorx.cookebook.parsers.DataJsonSerializer;
import com.feamorx.cookebook.parsers.ParserFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by x86 on 08.01.2017.
 */

public class DataStorage {
    public MasterData loadFromFile(String path) {
        MasterData result;
        try {
            FileInputStream fileInputStream = new FileInputStream(path);
            StringBuilder builder = new StringBuilder();
            byte [] buffer = new byte [2048];
            int count;
            while ((count = fileInputStream.read(buffer, 0, buffer.length)) != -1) {
                builder.append(new String(buffer, 0 ,count));
            }
            fileInputStream.close();

            JSONObject json = new JSONObject(builder.toString());

            DataJsonSerializer serializer = (DataJsonSerializer) ParserFactory.get().forClass(DataJsonSerializer.class);
            result = (MasterData) serializer.load(json);
        }catch (Exception ex) {
            Log.e(DataStorage.class.getSimpleName(), "fail to load from : "+path, ex);
            result = null;
        }
        System.gc();
        return result;
    }

    public boolean saveToFile(MasterData data, String path) {
        boolean result;
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(path);

            DataJsonSerializer serializer = (DataJsonSerializer) ParserFactory.get().forClass(DataJsonSerializer.class);
            JSONObject json = (JSONObject) serializer.save(data);
            String str = json.toString();
            fileOutputStream.write(str.getBytes());
            fileOutputStream.close();
            result = true;
        }catch (Exception ex) {
            Log.e(DataStorage.class.getSimpleName(), "fail to save to: "+path, ex);
            result = false;
        }
        System.gc();
        return result;
    }

    public MasterData loadFromUrl(String url) {
        MasterData result;
        try {
            URL u = new URL(url);
            HttpURLConnection httpURLConnection = null;
            StringBuilder builder = null;
            try {
                httpURLConnection = (HttpURLConnection) u.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                builder = new StringBuilder();
                byte[] buffer = new byte[2048];
                int count;
                while ((count = inputStream.read(buffer, 0, buffer.length)) != -1) {
                    builder.append(new String(buffer, 0, count));
                }
            } catch (IOException iex) {
                Log.e(DataStorage.class.getSimpleName(), "Fail load data from url : "+url, iex);
                builder = null;
            } finally {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
            }

            if (builder != null) {
                try {
                    JSONObject json = new JSONObject(builder.toString());
                    DataJsonSerializer serializer = (DataJsonSerializer) ParserFactory.get().forClass(DataJsonSerializer.class);
                    result = (MasterData) serializer.load(json);
                }catch (JSONException jex) {
                    Log.e(DataStorage.class.getSimpleName(), "Fail to parse data from url : "+url, jex);
                    result = null;
                }
            } else {
                result = null;
            }
        } catch (MalformedURLException ex) {
            Log.e(DataStorage.class.getSimpleName(), "Invalid url: "+url, ex);
            result = null;
        }
        System.gc();
        return result;
    }

    public MasterData loadFromAssets(Context context, String fileName) {
        MasterData result;
        try {
            InputStream inputStream = context.getAssets().open(fileName);
            StringBuilder builder = new StringBuilder();
            byte [] buffer = new byte [2048];
            int count;
            while ((count = inputStream.read(buffer, 0, buffer.length)) != -1) {
                builder.append(new String(buffer, 0 ,count));
            }
            inputStream.close();

            JSONObject json = new JSONObject(builder.toString());

            DataJsonSerializer serializer = (DataJsonSerializer) ParserFactory.get().forClass(DataJsonSerializer.class);
            result = (MasterData) serializer.load(json);
        }catch (Exception ex) {
            Log.e(DataStorage.class.getSimpleName(), "fail to load from : "+fileName, ex);
            result = null;
        }
        System.gc();
        return result;
    }
}
