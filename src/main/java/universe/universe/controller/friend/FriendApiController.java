package universe.universe.controller.friend;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import universe.universe.common.exception.Exception400;
import universe.universe.common.exception.Exception500;
import universe.universe.common.reponse.ApiResponse;
import universe.universe.dto.friend.FriendRequestResponseDTO;
import universe.universe.dto.friend.FriendResponseDTO;
import universe.universe.entitiy.user.User;
import universe.universe.service.auth.AuthenticationService;
import universe.universe.service.friend.FriendRequestServiceImpl;
import universe.universe.service.friend.FriendServiceImpl;

@RestController
@RequestMapping("/api/friend")
@RequiredArgsConstructor
@Slf4j
public class FriendApiController {
    final private FriendServiceImpl friendService;
    final private AuthenticationService authenticationService;

    // 친구 목록 조회
    @GetMapping("/findAll")
    public ResponseEntity<?> findAll() {
        try {
            String userEmail = getUserEmail();
            FriendResponseDTO.FriendFindAllDTO friendFindAllDTO = friendService.findAll(userEmail);
            return ResponseEntity.ok().body(ApiResponse.SUCCESS(HttpStatus.CREATED.value(), "친구 목록 조회가 완료되었습니다.", friendFindAllDTO));
        } catch (Exception400 e) {
            return ResponseEntity.badRequest().body(ApiResponse.FAILURE(e.status().value(), e.getMessage()));
        } catch (Exception500 e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.ERROR(e.status().value(), e.getMessage()));
        }
    }

    // 등교 중인 친구 목록 조회
    @GetMapping("/findInSchool")
    public ResponseEntity<?> findInSchool() {
        try {
            String userEmail = getUserEmail();
            FriendResponseDTO.FriendFindInSchoolDTO friendFindInSchoolDTO = friendService.findInSchool(userEmail);
            return ResponseEntity.ok().body(ApiResponse.SUCCESS(HttpStatus.CREATED.value(), "등교 중인 친구 목록 조회가 완료되었습니다.", friendFindInSchoolDTO));
        } catch (Exception400 e) {
            return ResponseEntity.badRequest().body(ApiResponse.FAILURE(e.status().value(), e.getMessage()));
        } catch (Exception500 e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.ERROR(e.status().value(), e.getMessage()));
        }
    }
    // 친구 삭제
    @PostMapping("/delete/{userId}")
    public ResponseEntity<?> delete(@PathVariable Long userId) {
        try {
            String userEmail = getUserEmail();
            friendService.delete(userEmail, userId);
            return ResponseEntity.ok().body(ApiResponse.SUCCESS(HttpStatus.CREATED.value(), "친구 삭제가 완료되었습니다."));
        } catch (Exception400 e) {
            return ResponseEntity.badRequest().body(ApiResponse.FAILURE(e.status().value(), e.getMessage()));
        } catch (Exception500 e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.ERROR(e.status().value(), e.getMessage()));
        }
    }
    // 친한 친구 토글
    @PostMapping("/toggle/{userId}")
    public ResponseEntity<?> toggle(@PathVariable Long userId) {
        try {
            String userEmail = getUserEmail();
            FriendResponseDTO.FriendToggleDTO friendToggleDTO = friendService.toggle(userEmail, userId);
            return ResponseEntity.ok().body(ApiResponse.SUCCESS(HttpStatus.CREATED.value(), "친한 친구 토글이 완료되었습니다.", friendToggleDTO));
        } catch (Exception400 e) {
            return ResponseEntity.badRequest().body(ApiResponse.FAILURE(e.status().value(), e.getMessage()));
        } catch (Exception500 e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.ERROR(e.status().value(), e.getMessage()));
        }
    }
    private String getUserEmail() {
        User user = authenticationService.getCurrentAuthenticatedUser();
        String userEmail = user.getUserEmail();
        return userEmail;
    }
}
