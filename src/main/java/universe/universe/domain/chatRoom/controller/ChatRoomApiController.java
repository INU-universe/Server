package universe.universe.domain.chatRoom.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import universe.universe.global.exception.Exception400;
import universe.universe.global.exception.Exception500;
import universe.universe.global.reponse.ApiResponse;
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
            log.info("[ChatRoomApiController]");
            String userEmail = getUserEmail();
            ChatRoomResponseDTO.ChatRoomCreateDTO result = chatRoomService.create(userEmail, chatRoomCreateDTO);
            return ResponseEntity.ok().body(ApiResponse.SUCCESS(HttpStatus.CREATED.value(), "채팅방 생성 성공입니다.", result));
        } catch (Exception400 e) {
            return ResponseEntity.badRequest().body(ApiResponse.FAILURE(e.status().value(), e.getMessage()));
        } catch (Exception500 e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.ERROR(e.status().value(), e.getMessage()));
        }
    }

    @PostMapping("/delete/{chatRoomId}")
    public ResponseEntity<?> delete(@PathVariable Long chatRoomId) {
        try {
            String userEmail = getUserEmail();
            chatRoomService.delete(userEmail, chatRoomId);
            return ResponseEntity.ok().body(ApiResponse.SUCCESS(HttpStatus.CREATED.value(), "채팅방 삭제 성공입니다."));
        } catch (Exception400 e) {
            return ResponseEntity.badRequest().body(ApiResponse.FAILURE(e.status().value(), e.getMessage()));
        } catch (Exception500 e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.ERROR(e.status().value(), e.getMessage()));
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll() {
        try {
            String userEmail = getUserEmail();
            ChatRoomResponseDTO.ChatRoomFindAllDTO result = chatRoomService.findAll(userEmail);
            return ResponseEntity.ok().body(ApiResponse.SUCCESS(HttpStatus.CREATED.value(), "채팅방 조회 성공입니다.", result));
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
