package universe.universe.domain.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import universe.universe.global.common.exception.CustomException;
import universe.universe.global.common.exception.Exception500;
import universe.universe.global.common.reponse.ApiResponse;
import universe.universe.domain.user.dto.UserRequestDTO;
import universe.universe.domain.user.dto.UserResponseDTO;
import universe.universe.domain.user.entity.User;
import universe.universe.global.auth.service.AuthenticationService;
import universe.universe.domain.user.service.UserServiceImpl;

@RestController
@RequestMapping("/api/v1/user/user")
@RequiredArgsConstructor
@Slf4j
public class UserApiController {
    final private UserServiceImpl userService;
    final private AuthenticationService authenticationService;

    // 회원 가입
    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody UserRequestDTO.UserJoinDTO userJoinDTO) {
        try {
            log.info("[UserApiController] join");
            UserResponseDTO.UserJoinDTO result = userService.join(userJoinDTO);
            return ResponseEntity.ok().body(ApiResponse.SUCCESS(HttpStatus.CREATED.value(), "[SUCCESS] UserApiController join", result));
        }  catch (Exception500 e) {
            log.info("[Exception500] UserApiController join");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.ERROR(e.status().value(), e.getMessage()));
        }
    }

    // 회원 탈퇴
    @PostMapping("/delete")
    public ResponseEntity<?> delete() {
        try {
            log.info("[UserApiController] delete");
            String userEmail = getUserEmail();
            userService.delete(userEmail);
            return ResponseEntity.ok().body(ApiResponse.SUCCESS(HttpStatus.CREATED.value(), "[SUCCESS] UserApiController delete"));
        }  catch (Exception500 e) {
            log.info("[Exception500] UserApiController delete");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.ERROR(e.status().value(), e.getMessage()));
        }
    }
    @GetMapping("/findOne")
    public ResponseEntity<?> findOne() {
        try {
            log.info("[UserApiController] findOne");
            String userEmail = getUserEmail();
            UserResponseDTO.UserFindDTO result = userService.findOne(userEmail);
            return ResponseEntity.ok().body(ApiResponse.SUCCESS(HttpStatus.CREATED.value(), "[SUCCESS] UserApiController findOne", result));
        }  catch (Exception500 e) {
            log.info("[Exception500] UserApiController findOne");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.ERROR(e.status().value(), e.getMessage()));
        }
    }

    // 회원 이모션 수정
    @PostMapping("/updateEmotion")
    public ResponseEntity<?> updateEmotion(@RequestBody UserRequestDTO.UserUpdateEmotionDTO userUpdateEmotionDTO) {
        try {
            log.info("[UserApiController] updateEmotion");
            String userEmail = getUserEmail();
            UserResponseDTO.UserUpdateEmotionDTO result = userService.updateEmotion(userUpdateEmotionDTO, userEmail);
            return ResponseEntity.ok().body(ApiResponse.SUCCESS(HttpStatus.CREATED.value(), "[SUCCESS] UserApiController updateEmotion", result));
        } catch (Exception500 e) {
            log.info("[Exception500] UserApiController updateEmotion");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.ERROR(e.status().value(), e.getMessage()));
        }
    }

    // 회원 학교 상태 수정
    @PostMapping("/updateSchool")
    public ResponseEntity<?> updateSchool(@RequestBody UserRequestDTO.UserUpdateSchoolDTO userUpdateSchoolDTO) {
        try {
            log.info("[UserApiController] updateSchool");
            String userEmail = getUserEmail();
            UserResponseDTO.UserUpdateSchoolDTO result = userService.updateSchool(userUpdateSchoolDTO, userEmail);
            return ResponseEntity.ok().body(ApiResponse.SUCCESS(HttpStatus.CREATED.value(), "[SUCCESS] UserApiController updateSchool", result));
        } catch (Exception500 e) {
            log.info("[Exception500] UserApiController updateSchool");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.ERROR(e.status().value(), e.getMessage()));
        }
    }

    // 회원 학교 상태 수정
    @PostMapping("/updateNotSchool")
    public ResponseEntity<?> updateNotSchool(@RequestBody UserRequestDTO.UserUpdateNotSchoolDTO userUpdateNotSchoolDTO) {
        try {
            log.info("[UserApiController] updateNotSchool");
            String userEmail = getUserEmail();
            UserResponseDTO.userUpdateNotSchoolDTO result = userService.updateNotSchool(userUpdateNotSchoolDTO, userEmail);
            return ResponseEntity.ok().body(ApiResponse.SUCCESS(HttpStatus.CREATED.value(), "[SUCCESS] UserApiController userUpdateNotSchoolDTO", result));
        } catch (Exception500 e) {
            log.info("[Exception500] UserApiController updateNotSchool");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.ERROR(e.status().value(), e.getMessage()));
        }
    }

    private String getUserEmail() {
        User user = authenticationService.getCurrentAuthenticatedUser();
        String userEmail = user.getUserEmail();
        return userEmail;
    }
}
