package com.hxt.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import okhttp3.*;
import org.json.JSONObject;

import java.io.*;


@Service
@RequiredArgsConstructor
public class ReviewService {
    public static final String IMAGE_API_KEY = "BEGZpCqfSZriSyK2WmQQ3sfo";
    public static final String IMAGE_SECRET_KEY = "sCDZky6NZCeDpsvmrwoX0QJmbHpoZouM";

    public static final String TEXT_API_KEY = "SCyNm75NYenBm7fDPyPJM2lT";
    public static final String TEXT_SECRET_KEY = "AynrV2COVokz7FPYcMJzaMACcf2S8ej0";


    static final OkHttpClient HTTP_CLIENT = new OkHttpClient().newBuilder().build();

    public boolean textReview(String text) throws IOException{
        //
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "grant_type=client_credentials&client_id=" + TEXT_API_KEY
                + "&client_secret=" + TEXT_SECRET_KEY);
        Request request = new Request.Builder()
                .url("https://aip.baidubce.com/oauth/2.0/token")
                .method("POST", body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        Response response = HTTP_CLIENT.newCall(request).execute();
        String accessToken = new JSONObject(response.body().string()).getString("access_token");


        mediaType = MediaType.parse("application/x-www-form-urlencoded");
        body = RequestBody.create(mediaType, "text="+text);
        request = new Request.Builder()
                .url("https://aip.baidubce.com/rest/2.0/solution/v1/text_censor/v2/user_defined?access_token=" + accessToken)
                .method("POST", body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .addHeader("Accept", "application/json")
                .build();
        response = HTTP_CLIENT.newCall(request).execute();
        JSONObject jsonObject = new JSONObject(response.body().string());
        if (!jsonObject.has("conclusion")) {
            return false;
        }
        String conclusion = jsonObject.getString("conclusion");
        return conclusion.equals("合规");
    }

    public boolean imageReview(String url) throws IOException{
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "grant_type=client_credentials&client_id=" + IMAGE_API_KEY
                + "&client_secret=" + IMAGE_SECRET_KEY);
        Request request = new Request.Builder()
                .url("https://aip.baidubce.com/oauth/2.0/token")
                .method("POST", body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        Response response = HTTP_CLIENT.newCall(request).execute();
        String accessToken = new JSONObject(response.body().string()).getString("access_token");

        mediaType = MediaType.parse("application/x-www-form-urlencoded");
        body = RequestBody.create(mediaType, "imgUrl="+url);
        request = new Request.Builder()
                .url("https://aip.baidubce.com/rest/2.0/solution/v1/img_censor/v2/user_defined?access_token=" + accessToken)
                .method("POST", body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .addHeader("Accept", "application/json")
                .build();
        response = HTTP_CLIENT.newCall(request).execute();
        JSONObject jsonObject = new JSONObject(response.body().string());
        if (!jsonObject.has("conclusion")) {
            return false;
        }
        String conclusion = jsonObject.getString("conclusion");
        return conclusion.equals("合规");
    }
}
