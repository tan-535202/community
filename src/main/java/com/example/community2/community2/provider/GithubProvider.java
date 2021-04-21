package com.example.community2.community2.provider;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.alibaba.fastjson.JSON;
import com.example.community2.community2.dto.AccessTokenDTO;
import com.example.community2.community2.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GithubProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String tokenstring = response.body().string();

            String token = tokenstring.split("&")[0].split("=")[1];
            System.out.println(token);
            return token;
        } catch (IOException e) {
        }
        return null;

    }
    public GithubUser getUser(String accessToken)  {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                //github新版请求方式
                .url("https://api.github.com/user")
                .header("Authorization","token "+accessToken)
                // .url("https://api.github.com/user?access_token=" + accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String tstring = response.body().string();
            GithubUser githubUser = JSON.parseObject(tstring , GithubUser.class);
            return githubUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

}
