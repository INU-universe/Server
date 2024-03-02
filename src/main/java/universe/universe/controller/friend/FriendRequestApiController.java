package universe.universe.controller.friend;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import universe.universe.common.exception.Exception400;
import universe.universe.common.exception.Exception500;
import universe.universe.common.reponse.ApiResponse;
import universe.universe.dto.friend.FriendRequestResponseDTO;
import universe.universe.entitiy.user.User;
import universe.universe.service.auth.AuthenticationService;
import universe.universe.service.friend.FriendRequestServiceImpl;

@RestController
@RequestMapping("/api/friendRequest")
@RequiredArgsConstructor
@Slf4j
public class FriendRequestApiController {
    final private FriendRequestServiceImpl friendRequestService;
    final private AuthenticationService authenticationService;
    @Value(("${jwt.secret}"))
    private String secretKey;

    @GetMapping("/send")
    public ResponseEntity<?> getURL() {
        try {
            String userEmail = getUserEmail();
            FriendRequestResponseDTO.FriendRequestGetURLDTO friendRequestSendDTO = friendRequestService.getURL(userEmail);
            return ResponseEntity.ok().body(ApiResponse.SUCCESS(HttpStatus.CREATED.value(), "친구 링크 요청이 완료되었습니다.", friendRequestSendDTO));
        } catch (Exception400 e) {
            return ResponseEntity.badRequest().body(ApiResponse.FAILURE(e.status().value(), e.getMessage()));
        } catch (Exception500 e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.ERROR(e.status().value(), e.getMessage()));
        }
    }

    @GetMapping("/accept")
    public ResponseEntity<?> acceptURL(@RequestParam("token") String token) {
        try {
            String userEmail = getUserEmail();

            FriendRequestResponseDTO.FriendRequestAcceptURLDTO friendRequestAcceptURLDTO = friendRequestService.acceptURL(userEmail, token);
            return ResponseEntity.ok().body(ApiResponse.SUCCESS(HttpStatus.CREATED.value(), "친구 링크 수락이 완료되었습니다.", friendRequestAcceptURLDTO));
        } catch (Exception400 e) {
            return ResponseEntity.badRequest().body(ApiResponse.FAILURE(e.status().value(), e.getMessage()));
        } catch (Exception500 e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.ERROR(e.status().value(), e.getMessage()));
        }
    }

    /** 친구 토글 안 쓸 예정 **/
//    // 친구 토글
//    @PostMapping("/toggle/{toUserId}")
//    public ResponseEntity<?> toggle(@PathVariable Long toUserId) {
//        try {
//            String userEmail = getUserEmail();
//            FriendRequestResponseDTO.FriendRequestToggleDTO friendRequestToggleDTO = friendRequestService.toggle(userEmail, toUserId);
//            if(friendRequestToggleDTO == null) {
//                return ResponseEntity.ok().body(ApiResponse.SUCCESS(HttpStatus.CREATED.value(), "친구 신청이 취소되었습니다.", null));
//            }
//            else {
//                return ResponseEntity.ok().body(ApiResponse.SUCCESS(HttpStatus.CREATED.value(), "친구 신청이 완료되었습니다.", friendRequestToggleDTO));
//            }
//        } catch (Exception400 e) {
//            return ResponseEntity.badRequest().body(ApiResponse.FAILURE(e.status().value(), e.getMessage()));
//        } catch (Exception500 e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.ERROR(e.status().value(), e.getMessage()));
//        }
//    }
//
//    // 친구 수락
//    @PostMapping("/accept/{toUserId}")
//    public ResponseEntity<?> accept(@PathVariable Long toUserId) {
//        try {
//            String userEmail = getUserEmail();
//            FriendRequestResponseDTO.FriendRequestAcceptDTO friendRequestAcceptDTO = friendRequestService.accept(userEmail, toUserId);
//            return ResponseEntity.ok().body(ApiResponse.SUCCESS(HttpStatus.CREATED.value(), "친구 신청 수락이 완료되었습니다.", friendRequestAcceptDTO));
//        } catch (Exception400 e) {
//            return ResponseEntity.badRequest().body(ApiResponse.FAILURE(e.status().value(), e.getMessage()));
//        } catch (Exception500 e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.ERROR(e.status().value(), e.getMessage()));
//        }
//    }
//
//    // 친구 거절
//    @PostMapping("/reject/{toUserId}")
//    public ResponseEntity<?> reject(@PathVariable Long toUserId) {
//        try {
//            String userEmail = getUserEmail();
//            FriendRequestResponseDTO.FriendRequestRejectDTO friendRequestRejectDTO = friendRequestService.reject(userEmail, toUserId);
//            return ResponseEntity.ok().body(ApiResponse.SUCCESS(HttpStatus.CREATED.value(), "친구 신청 거절이 완료되었습니다.", friendRequestRejectDTO));
//        } catch (Exception400 e) {
//            return ResponseEntity.badRequest().body(ApiResponse.FAILURE(e.status().value(), e.getMessage()));
//        } catch (Exception500 e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.ERROR(e.status().value(), e.getMessage()));
//        }
//    }
//    // 친구 요청 목록 조회
//    @GetMapping("/findAll")
//    public ResponseEntity<?> findAll() {
//        try {
//            String userEmail = getUserEmail();
//            FriendRequestResponseDTO.FriendRequestFindAllDTO friendRequestFindAllDTO = friendRequestService.findAll(userEmail);
//            return ResponseEntity.ok().body(ApiResponse.SUCCESS(HttpStatus.CREATED.value(), "친구 요청 목록 조회가 완료되었습니다.", friendRequestFindAllDTO));
//        } catch (Exception400 e) {
//            return ResponseEntity.badRequest().body(ApiResponse.FAILURE(e.status().value(), e.getMessage()));
//        } catch (Exception500 e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.ERROR(e.status().value(), e.getMessage()));
//        }
//    }


    private String getUserEmail() {
        User user = authenticationService.getCurrentAuthenticatedUser();
        String userEmail = user.getUserEmail();
        return userEmail;
    }
}
