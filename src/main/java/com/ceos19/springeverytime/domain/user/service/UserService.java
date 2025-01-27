package com.ceos19.springeverytime.domain.user.service;

import com.ceos19.springeverytime.domain.user.domain.User;
import com.ceos19.springeverytime.domain.user.dto.request.UserCreateRequest;
import com.ceos19.springeverytime.domain.user.dto.response.CustomUserDetails;
import com.ceos19.springeverytime.domain.user.repository.UserRepository;
import com.ceos19.springeverytime.global.exception.BadRequestException;
import com.ceos19.springeverytime.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public User register(UserCreateRequest request) {
        User user = new User(
                request.getLoginId(),
                passwordEncoder.encode(request.getPassword()),
                request.getNickname(),
                request.getName(),
                request.getMajor(),
                request.getAdmissionYear(),
                request.getEmail()
        );
        validateDuplicatedUser(user);
        return userRepository.save(user);
    }

    public void delete() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        String loginId = userDetails.getUsername();
        User user = userRepository.findByLoginId(loginId).orElseThrow(
                () -> new BadRequestException(ExceptionCode.NOT_FOUND_LOGIN_ID)
        );
        userRepository.delete(user);
    }

    public User findOne(Long userId) {
        Optional<User> findUser = userRepository.findById(userId);
        if (findUser.isPresent()) return findUser.get();
        throw new IllegalArgumentException("잘못된 유저 ID 입니다.");
    }

    private void validateDuplicatedUser(User user) {
        if (userRepository.existsByLoginId(user.getLoginId())) {
            throw new BadRequestException(ExceptionCode.DUPLICATED_LOGIN_ID);
        }
    }
}
