package com.korit.post_mini_project_back.service;

import com.korit.post_mini_project_back.entity.User;
import com.korit.post_mini_project_back.mapper.UserMapper;
import com.korit.post_mini_project_back.security.PrincipalUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;

    public User findUserByOauth2Id(String oauth2Id) {
        return userMapper.findByOauth2Id(oauth2Id);
    }
                                        // authentication = 로그인한 user 정보
    public User createUser(Authentication authentication) {

        // authentication 안에서 PrincipalUser 이거 정보만 추출하는 과정
        PrincipalUser principalUser = (PrincipalUser) authentication.getPrincipal();

        User user = principalUser.getUser();
        user.setNickname(createNickname());
        userMapper.insert(user);

        return user;
    }

    public String createNickname() {
        String newNickname = null;
        while (true) {
            newNickname = userMapper.createNickname();
            if (Objects.isNull(userMapper.findByNickname(newNickname))) {
                break;
            }
        }
        return newNickname;
    }
}
