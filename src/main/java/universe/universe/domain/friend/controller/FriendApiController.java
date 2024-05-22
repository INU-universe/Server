package universe.universe.domain.friend.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import universe.universe.global.common.exception.Exception500;
import universe.universe.global.common.reponse.ApiResponse;
import universe.universe.domain.friend.dto.FriendResponseDTO;
import universe.universe.domain.user.entity.User;
import universe.universe.global.auth.service.AuthenticationService;
import universe.universe.domain.friend.service.FriendServiceImpl;

@RestController
@RequestMapping("/api/v1/user/friend")
@RequiredArgsConstructor
@Slf4j
public class FriendApiController {
    final private FriendServiceImpl friendService;
    final private AuthenticationService authenticationService;

    // 친구 목록 조회
    @GetMapping("/findAll")
    public ResponseEntity<?> findAll() {
        try {
            log.info("[FriendApiController] findAll");
            String userEmail = getUserEmail();
            FriendResponseDTO.FriendFindAllDTO result = friendService.findAll(userEmail);
            return ResponseEntity.ok().body(ApiResponse.SUCCESS(HttpStatus.CREATED.value(), "[SUCCESS] FriendApiController findAll", result));
        } catch (Exception500 e) {
            log.info("[Exception500] FriendApiController findAll");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.ERROR(e.status().value(), e.getMessage()));
        }
    }

    // 등교 중인 친구 목록 조회
    @GetMapping("/findInSchool")
    public ResponseEntity<?> findInSchool() {
        try {
            log.info("[FriendApiController] findInSchool");
            String userEmail = getUserEmail();
            FriendResponseDTO.FriendFindInSchoolDTO result = friendService.findInSchool(userEmail);
            return ResponseEntity.ok().body(ApiResponse.SUCCESS(HttpStatus.CREATED.value(), "[SUCCESS] FriendApiController findInSchool", result));
        } catch (Exception500 e) {
            log.info("[Exception500] FriendApiController findInSchool");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.ERROR(e.status().value(), e.getMessage()));
        }
    }
    // 친구 삭제
    @PostMapping("/delete/{userId}")
    public ResponseEntity<?> delete(@PathVariable Long userId) {
        try {
            log.info("[FriendApiController] delete");
            String userEmail = getUserEmail();
            friendService.delete(userEmail, userId);
            return ResponseEntity.ok().body(ApiResponse.SUCCESS(HttpStatus.CREATED.value(), "[SUCCESS] FriendApiController delete"));
        } catch (Exception500 e) {
            log.info("[Exception500] FriendApiController delete");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.ERROR(e.status().value(), e.getMessage()));
        }
    }
    private String getUserEmail() {
        User user = authenticationService.getCurrentAuthenticatedUser();
        String userEmail = user.getUserEmail();
        return userEmail;
    }
}
