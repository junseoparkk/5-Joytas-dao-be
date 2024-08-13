package com.example.daobe.lounge.service;

import com.example.daobe.lounge.dto.LoungeCreateRequestDto;
import com.example.daobe.lounge.dto.LoungeCreateResponseDto;
import com.example.daobe.lounge.dto.LoungeInfoDto;
import com.example.daobe.lounge.entity.Lounge;
import com.example.daobe.lounge.entity.LoungeStatus;
import com.example.daobe.lounge.entity.LoungeType;
import com.example.daobe.lounge.repository.LoungeRepository;
import com.example.daobe.user.entity.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoungeService {

    private final LoungeRepository loungeRepository;

    public LoungeCreateResponseDto create(LoungeCreateRequestDto request, User user) {
        Lounge lounge = Lounge.builder()
                .user(user)
                .name(request.name())
                .type(LoungeType.from(request.type()))  // 라운지 타입
                .status(LoungeStatus.ACTIVE)    // 라운지 활성화
                .build();

        loungeRepository.save(lounge);
        return LoungeCreateResponseDto.of(lounge);
    }

    public List<LoungeInfoDto> findLoungeByUserId(Long userId) {
        return loungeRepository.findLoungeByUserId(userId).stream()
                .map(LoungeInfoDto::of)
                .toList();
    }
}
