package universe.universe.controller.chatRoom;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import universe.universe.common.exception.Exception400;
import universe.universe.common.exception.Exception500;
import universe.universe.common.reponse.ApiResponse;
import universe.universe.dto.chatRoom.ChatRoomRequestDTO;
import universe.universe.dto.chatRoom.ChatRoomResponseDTO;
import universe.universe.entitiy.user.User;
import universe.universe.service.auth.AuthenticationService;
import universe.universe.service.chatRoom.ChatRoomServiceImpl;

@RestController
@RequestMapping("/api/chatRoom")
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
            ChatRoomResponseDTO.ChatRoomCreateDTO createChatRoomRelation = chatRoomService.create(chatRoomCreateDTO);
            return ResponseEntity.ok().body(ApiResponse.SUCCESS(HttpStatus.CREATED.value(), "채팅방 생성 성공입니다.", createChatRoomRelation));
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
