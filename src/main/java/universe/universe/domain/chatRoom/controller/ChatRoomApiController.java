package universe.universe.domain.chatRoom.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import universe.universe.global.common.exception.Exception500;
import universe.universe.global.common.reponse.ApiResponse;
import universe.universe.domain.chatRoom.dto.ChatRoomRequestDTO;
import universe.universe.domain.chatRoom.dto.ChatRoomResponseDTO;
import universe.universe.domain.user.entity.User;
import universe.universe.global.auth.service.AuthenticationService;
import universe.universe.domain.chatRoom.service.ChatRoomServiceImpl;

@RestController
@RequestMapping("/api/v1/user/chatRoom")
@RequiredArgsConstructor
@Slf4j
public class ChatRoomApiController {
    final private ChatRoomServiceImpl chatRoomService;
    final private AuthenticationService authenticationService;
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody ChatRoomRequestDTO.ChatRoomCreateDTO chatRoomCreateDTO) {
        try {
            log.info("[ChatRoomApiController] create");
            String userEmail = getUserEmail();
            ChatRoomResponseDTO.ChatRoomCreateDTO result = chatRoomService.create(userEmail, chatRoomCreateDTO);
            return ResponseEntity.ok().body(ApiResponse.SUCCESS(HttpStatus.CREATED.value(), "chatRoom create success", result));
        } catch (Exception500 e) {
            log.info("[Exception500] ChatRoomApiController create");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.ERROR(e.status().value(), e.getMessage()));
        }
    }

    @PostMapping("/delete/{chatRoomId}")
    public ResponseEntity<?> delete(@PathVariable Long chatRoomId) {
        try {
            log.info("[ChatRoomApiController] delete");
            String userEmail = getUserEmail();
            chatRoomService.delete(userEmail, chatRoomId);
            return ResponseEntity.ok().body(ApiResponse.SUCCESS(HttpStatus.CREATED.value(), "chatRoom delete success"));
        } catch (Exception500 e) {
            log.info("[Exception500] ChatRoomApiController delete");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.ERROR(e.status().value(), e.getMessage()));
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll() {
        try {
            log.info("[ChatRoomApiController] findAll");
            String userEmail = getUserEmail();
            ChatRoomResponseDTO.ChatRoomFindAllDTO result = chatRoomService.findAll(userEmail);
            return ResponseEntity.ok().body(ApiResponse.SUCCESS(HttpStatus.CREATED.value(), "chatRoom findAll success", result));
        } catch (Exception500 e) {
            log.info("[Exception500] ChatRoomApiController findAll");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.ERROR(e.status().value(), e.getMessage()));
        }
    }

    private String getUserEmail() {
        User user = authenticationService.getCurrentAuthenticatedUser();
        String userEmail = user.getUserEmail();
        return userEmail;
    }
}
