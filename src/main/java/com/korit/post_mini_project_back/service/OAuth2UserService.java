package com.korit.post_mini_project_back.service;

import com.korit.post_mini_project_back.entity.User;
import com.korit.post_mini_project_back.security.PrincipalUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class OAuth2UserService extends DefaultOAuth2UserService {

    @Override
    // 이것도 콜백 ㅁㅔ서드(민재 ㅎ)
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);
        //네이버에서 받아온 제이슨이 OAuth2User 이 형태로 담김 !

        String clientName = userRequest.getClientRegistration().getClientName();
        //현재 로그인을 시도하는 플랫폼을 확인하는 코드

        Map<String, Object> attributes = new LinkedHashMap<>();

        Collection<? extends  GrantedAuthority> authorities = oAuth2User.getAuthorities();
        String nameAttributeKey = null;
        User user = null;

        if ("NAVER".equalsIgnoreCase(clientName)) {
            Map<String, Object> response = (Map<String, Object>) oAuth2User.getAttributes().get("response");//네이버만 이럼
            attributes.putAll(response);
            nameAttributeKey = "id";
            user = User.builder() //db에 담을 준비
                    .oauth2Id((String) response.get("id"))
                    .name((String) response.get("name"))
                    .email((String) response.get("email"))
                    .provider(clientName)
                    .role(authorities.stream().findFirst().get().toString())
                    .imgUrl((String) response.get("profile_image"))
                    .build();

        }

        return new PrincipalUser(authorities, attributes, nameAttributeKey, user);
    }
}
