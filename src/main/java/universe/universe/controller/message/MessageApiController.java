package universe.universe.controller.message;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import universe.universe.common.exception.Exception400;
import universe.universe.common.exception.Exception500;
import universe.universe.common.reponse.ApiResponse;
import universe.universe.dto.message.MessageRequestDTO;
import universe.universe.dto.message.MessageResponseDTO;
import universe.universe.entitiy.user.User;
import universe.universe.service.auth.AuthenticationService;
import universe.universe.service.message.MessageServiceImpl;

@RestController
@RequestMapping("/api/message")
@RequiredArgsConstructor
@Slf4j
public class MessageApiController {
    final private MessageServiceImpl messageService;
    final private AuthenticationService authenticationService;

    // 메세지 저장
    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody MessageRequestDTO.MessageSaveDTO messageSaveDTO) {
        try {
            String userEmail = getUserEmail();
            MessageResponseDTO.MessageSaveDTO saveMessage = messageService.save(messageSaveDTO, userEmail);
            return ResponseEntity.ok().body(ApiResponse.SUCCESS(HttpStatus.CREATED.value(), "메세지 저장이 완료되었습니다.", saveMessage));
        } catch (Exception400 e) {
            return ResponseEntity.badRequest().body(ApiResponse.FAILURE(e.status().value(), e.getMessage()));
        } catch (Exception500 e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.ERROR(e.status().value(), e.getMessage()));
        }
    }

    // 메세지 삭제
    @PostMapping("/delete/{messageId}")
    public ResponseEntity<?> delete(@PathVariable Long messageId) {
        try {
            String userEmail = getUserEmail();
            messageService.delete(userEmail, messageId);
            return ResponseEntity.ok().body(ApiResponse.SUCCESS(HttpStatus.CREATED.value(), "메세지 삭제가 완료되었습니다."));
        } catch (Exception400 e) {
            return ResponseEntity.badRequest().body(ApiResponse.FAILURE(e.status().value(), e.getMessage()));
        } catch (Exception500 e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.ERROR(e.status().value(), e.getMessage()));
        }
    }

    // 메세지 전체 조회
//    @PostMapping("/findAll")
//    public ResponseEntity<?> findAll(@RequestBody UserRequestDTO.UserUpdateDTO userUpdateDTO) {
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
