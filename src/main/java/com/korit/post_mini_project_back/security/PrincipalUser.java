package com.korit.post_mini_project_back.security;

import com.korit.post_mini_project_back.entity.User;
import lombok.Getter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import java.util.Collection;
import java.util.Map;

@Getter
public class PrincipalUser extends DefaultOAuth2User {

    // DB에서 가져온 실제 유저 정보를 담아둘 변수
    private User user;

    // 부모 클래스(시큐리티)가 필요한 권한(authorities), 속성(attributes) 등과 함께 유저 객체를 받아서 생성
    // 대충 신분증 양식이라고 생각하면 편함
    public PrincipalUser(Collection<? extends GrantedAuthority> authorities, Map<String, Object> attributes, String nameAttributeKey, User user) {
        super(authorities, attributes, nameAttributeKey);
        this.user = user;
    }

    public static PrincipalUser getAuthenticatedPrincipalUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PrincipalUser principalUser = (PrincipalUser) authentication.getPrincipal();

        return principalUser;
    }

}
