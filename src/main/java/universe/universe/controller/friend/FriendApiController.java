package universe.universe.controller.friend;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import universe.universe.common.exception.Exception400;
import universe.universe.common.exception.Exception500;
import universe.universe.common.reponse.ApiResponse;
import universe.universe.dto.friend.FriendRelationShipResponseDTO;
import universe.universe.entitiy.user.User;
import universe.universe.service.auth.AuthenticationService;
import universe.universe.service.friend.FriendRelationShipServiceImpl;

@RestController
@RequestMapping("/api/friend")
@RequiredArgsConstructor
@Slf4j
public class FriendApiController {
    final private FriendRelationShipServiceImpl friendService;
    final private AuthenticationService authenticationService;

    // 친구 토글
    @PostMapping("/toggle/{toUserId}")
    public ResponseEntity<?> toggle(@PathVariable Long toUserId) {
        try {
            String userEmail = getUserEmail();
            FriendRelationShipResponseDTO.FriendRelationShipToggleDTO friendRelationShipToggleDTO = friendService.toggle(userEmail, toUserId);
            if(friendRelationShipToggleDTO == null) {
                return ResponseEntity.ok().body(ApiResponse.SUCCESS(HttpStatus.CREATED.value(), "친구 신청이 취소되었습니다.", null));
            }
            else {
                return ResponseEntity.ok().body(ApiResponse.SUCCESS(HttpStatus.CREATED.value(), "친구 신청이 완료되었습니다.", friendRelationShipToggleDTO));
            }
        } catch (Exception400 e) {
            return ResponseEntity.badRequest().body(ApiResponse.FAILURE(e.status().value(), e.getMessage(), null));
        } catch (Exception500 e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.ERROR(e.status().value(), e.getMessage(), null));
        }
    }

    private String getUserEmail() {
        User user = authenticationService.getCurrentAuthenticatedUser();
        String userEmail = user.getUserEmail();
        return userEmail;
    }
}
