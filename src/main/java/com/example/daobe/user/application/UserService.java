package com.example.daobe.user.application;

import static com.example.daobe.user.exception.UserExceptionType.DUPLICATE_NICKNAME;
import static com.example.daobe.user.exception.UserExceptionType.NOT_EXIST_USER;

import com.example.daobe.user.application.dto.UserInfoResponseDto;
import com.example.daobe.user.domain.User;
import com.example.daobe.user.domain.UserStatus;
import com.example.daobe.user.domain.repository.UserRepository;
import com.example.daobe.user.exception.UserException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserInfoResponseDto getUserInfoWithId(Long userId) {
        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(NOT_EXIST_USER));
        return UserInfoResponseDto.of(findUser);
    }

    public List<UserInfoResponseDto> searchUserByNickname(String nickname) {
        List<User> findUserList = userRepository.findByNicknameContainingAndStatus(nickname, UserStatus.ACTIVE);
        return findUserList.stream()
                .map(UserInfoResponseDto::of)
                .toList();
    }

    public void checkValidateByNickname(String nickname) {
        if (userRepository.existsByNickname(nickname)) {
            throw new UserException(DUPLICATE_NICKNAME);
        }
    }
}
