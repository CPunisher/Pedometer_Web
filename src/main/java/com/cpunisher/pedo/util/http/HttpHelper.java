package com.cpunisher.pedo.util.http;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class HttpHelper {

    private HttpClient httpClient;

    public HttpHelper(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public HttpResponse sendPost(String url, NameValuePair... args) {
        HttpPost httpPost = new HttpPost(url);
        List<NameValuePair> list = Arrays.asList(args);
        HttpResponse response = null;

        try {
            httpPost.setEntity(new UrlEncodedFormEntity(list, "utf-8"));
            response = httpClient.execute(httpPost);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
}
