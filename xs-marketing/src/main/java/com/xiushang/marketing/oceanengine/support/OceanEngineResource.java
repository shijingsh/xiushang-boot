package com.xiushang.marketing.oceanengine.support;

import com.alibaba.fastjson.JSON;
import com.xiushang.marketing.oceanengine.api.bean.OceanEngineResponse;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;



public class OceanEngineResource {

    public static final int defaultConnectionTimeoutSecs = 30;
    public static final int defaultReadTimeoutSecs = 120;


    public static <T extends OceanEngineResponse> T execute(HttpMethod httpMethod, String apiUrl, String payLoad,
                                                            Class<T> clazz, String accessToken) throws OceanEngineRestException {
        String response = request(httpMethod, apiUrl, payLoad, accessToken);
        return JSON.parseObject(response, clazz);
    }

    private static HttpsURLConnection httpsConnect(HttpMethod httpMethod, String apiUrl, String accessToken) throws IOException {
        URL url = new URL(apiUrl);
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.setConnectTimeout(defaultConnectionTimeoutSecs * 1000);
        connection.setReadTimeout(defaultReadTimeoutSecs * 1000);
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestMethod(httpMethod.name());
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Api-Version", "0.1");
        connection.setRequestProperty("User-Agent", "OceanEngine Marketing-api Java SDK");
        if (accessToken != null) {
            connection.setRequestProperty("Access-Token", accessToken);
        }
        return connection;
    }

    public static String request(HttpMethod httpMethod, String apiUrl, String payLoad, String token) throws OceanEngineRestException {
        try {
            HttpsURLConnection connection = httpsConnect(httpMethod, apiUrl, token);
            if (httpMethod == HttpMethod.POST) {
                connection.setDoOutput(true);
                Writer wr = new OutputStreamWriter(connection.getOutputStream(), StandardCharsets.UTF_8);
                wr.write(payLoad);
                wr.flush();
                wr.close();
            }

            boolean error = false;
            int responseCode = connection.getResponseCode();
            InputStream is;
            if (responseCode >= 200 && responseCode < 300) {
                is = connection.getInputStream();
            } else {
                is = connection.getErrorStream();
                error = true;
            }
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer response = new StringBuffer();
            while ((line = rd.readLine()) != null) {
                response.append(line);
            }
            rd.close();
            String responseString = response.toString();
            if (error) {
                throw new OceanEngineRestException("api call error response: " + responseCode + " body " + responseString + " url=" + apiUrl);
            }
            return responseString;
        } catch (IOException e) {
            throw new OceanEngineRestException("api request io error, url=" + apiUrl, e);
        }
    }

}
