package com.automationanywhere.botcommand.samples.commands.utils;

import com.automationanywhere.botcommand.exception.BotCommandException;
import org.apache.logging.log4j.core.util.StringEncoder;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import okhttp3.*;


public class httpHelper {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public static String getHttpText(HttpURLConnection cn) throws IOException {
        BufferedReader br = null;
        br = new BufferedReader(new InputStreamReader(cn.getInputStream()));

        StringBuilder sb = new StringBuilder();
        String output;
        while ((output = br.readLine()) != null) {
            sb.append(output);
        }
        return sb.toString();
    }
    public static String getHttpErrorText(HttpURLConnection cn) throws IOException {
        BufferedReader br = null;
        br = new BufferedReader(new InputStreamReader(cn.getErrorStream()));

        StringBuilder sb = new StringBuilder();
        String output;
        while ((output = br.readLine()) != null) {
            sb.append(output);
        }
        return sb.toString();
    }



    public static String get(String url) {
        Map<String, String> headers = new HashMap<>();
        return httpHelper.get(url,headers);
    }

    public static String get(String url, Map<String, String> headers){
        try {
            URL Myurl = new URL(url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) Myurl.openConnection();
            httpURLConnection.setRequestMethod("GET");
            for(String key: headers.keySet()){
                httpURLConnection.setRequestProperty(key, headers.get(key));
            }

            int responseCode = httpURLConnection.getResponseCode();
            if(responseCode != 200){
                String jsonText = httpHelper.getHttpErrorText(httpURLConnection);
                throw new BotCommandException("Http Error:" + jsonText);
            }
            return httpHelper.getHttpText(httpURLConnection);
        }
        catch (MalformedURLException e) {
            throw new RuntimeException("You've entered an invalid URL here: " + url);
        }
        catch (IOException e) {
            throw new RuntimeException("Error processing request", e);
        }


    }

    public static String post(String url, Map<String, String> headers, JSONObject json){

        try {
            final OkHttpClient client = new OkHttpClient.Builder()
                    .build();



            String jsonString = json.toString();
            RequestBody body2 = RequestBody.create(jsonString, JSON);

            Request.Builder build = new Request.Builder()
                    .url(url)
                    .post(body2);

            for (String key : headers.keySet()) {
                build.addHeader(key, headers.get(key));
            }
            Request request = build.build();

            Response response = client.newCall(request).execute();
            Integer status = response.code();
            if (status != 200) {
                throw new BotCommandException("Error at CardAddRq:" + response.body().string());
            }
            String responseBodyString = response.body().string();
//            System.out.println("Status:" + status);
//            System.out.println("Body:" + responseBodyString);
            //        jsonObject = JsonUtils.XmlToObj(responseBodyString,forceString);
            return jsonString;
        }catch (IOException e) {
            throw new RuntimeException("Error processing request", e);
        }




    }

}
