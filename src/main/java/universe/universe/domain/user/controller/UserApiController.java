package universe.universe.domain.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
            UserResponseDTO.UserJoinDTO result = userService.join(userJoinDTO);
            return ResponseEntity.ok().body(ApiResponse.SUCCESS(HttpStatus.CREATED.value(), "user join success", result));
        }  catch (Exception500 e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.ERROR(e.status().value(), e.getMessage()));
        }
    }

    // 회원 탈퇴
    @PostMapping("/delete")
    public ResponseEntity<?> delete() {
        try {
            String userEmail = getUserEmail();
            userService.delete(userEmail);
            return ResponseEntity.ok().body(ApiResponse.SUCCESS(HttpStatus.CREATED.value(), "user delete success"));
        }  catch (Exception500 e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.ERROR(e.status().value(), e.getMessage()));
        }
    }
    @GetMapping("/findOne")
    public ResponseEntity<?> findOne() {
        try {
            String userEmail = getUserEmail();
            UserResponseDTO.UserFindDTO result = userService.findOne(userEmail);
            return ResponseEntity.ok().body(ApiResponse.SUCCESS(HttpStatus.CREATED.value(), "user findOne success", result));
        }  catch (Exception500 e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.ERROR(e.status().value(), e.getMessage()));
        }
    }

//    // 회원 수정
//    @PostMapping("/update")
//    public ResponseEntity<?> update(@RequestBody UserRequestDTO.UserUpdateDTO userUpdateDTO) {
//        try {
//            String userEmail = getUserEmail();
//            UserResponseDTO.UserUpdateDTO updateUser = userService.update(userUpdateDTO, userEmail);
//            return ResponseEntity.ok().body(ApiResponse.SUCCESS(HttpStatus.CREATED.value(), "회원 수정이 완료되었습니다.", updateUser));
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
