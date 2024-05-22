package universe.universe.domain.friendRequest.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import universe.universe.global.common.exception.Exception500;
import universe.universe.global.common.reponse.ApiResponse;
import universe.universe.domain.friendRequest.dto.FriendRequestResponseDTO;
import universe.universe.domain.user.entity.User;
import universe.universe.global.auth.service.AuthenticationService;
import universe.universe.domain.friendRequest.service.FriendRequestServiceImpl;

@RestController
@RequestMapping("/api/v1/user/friendRequest")
@RequiredArgsConstructor
@Slf4j
public class FriendRequestApiController {
    final private FriendRequestServiceImpl friendRequestService;
    final private AuthenticationService authenticationService;
    @Value(("${jwt.secret}"))
    private String secretKey;

    @GetMapping("/get")
    public ResponseEntity<?> getURL() {
        try {
            log.info("[FriendRequestApiController] getURL");
            String userEmail = getUserEmail();
            FriendRequestResponseDTO.FriendRequestGetURLDTO result = friendRequestService.getURL(userEmail);
            return ResponseEntity.ok().body(ApiResponse.SUCCESS(HttpStatus.CREATED.value(), "[SUCCESS] FriendRequestApiController getURL", result));
        } catch (Exception500 e) {
            log.info("[Exception500] FriendRequestApiController getURL");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.ERROR(e.status().value(), e.getMessage()));
        }
    }

    @GetMapping("/accept")
    public ResponseEntity<?> acceptURL(@RequestParam("token") String token) {
        try {
            log.info("[FriendRequestApiController] acceptURL");
            String userEmail = getUserEmail();
            FriendRequestResponseDTO.FriendRequestAcceptURLDTO result = friendRequestService.acceptURL(userEmail, token);
            return ResponseEntity.ok().body(ApiResponse.SUCCESS(HttpStatus.CREATED.value(), "[SUCCESS] FriendRequestApiController acceptURL", result));
        } catch (Exception500 e) {
            log.info("[Exception500] FriendRequestApiController acceptURL");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.ERROR(e.status().value(), e.getMessage()));
        }
    }

    private String getUserEmail() {
        User user = authenticationService.getCurrentAuthenticatedUser();
        String userEmail = user.getUserEmail();
        return userEmail;
    }
}
