package com.example.community2.community2.controller;

import com.example.community2.community2.dto.AccessTokenDTO;
import com.example.community2.community2.dto.GithubUser;
import com.example.community2.community2.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String secret;

    @Value("${github.redirect.uri}")
    private String uri;

    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code ,
                            @RequestParam(name="state") String state
                           ){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id("clientId");
        accessTokenDTO.setClient_secret("secret");
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setRedirect_uri("uri");
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser user = githubProvider.getUser(accessToken);
        System.out.println(user.getName());
        return "index";
    }
}
