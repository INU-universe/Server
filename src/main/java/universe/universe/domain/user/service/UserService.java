package universe.universe.domain.user.service;

import universe.universe.domain.user.dto.UserRequestDTO;
import universe.universe.domain.user.dto.UserResponseDTO;

public interface UserService {
    // 회원 가입
    UserResponseDTO.UserJoinDTO join(UserRequestDTO.UserJoinDTO userJoinDTO);

    // 회원 탈퇴
    void delete(String userEmail);

//    // 회원 수정
//    UserResponseDTO.UserUpdateDTO update(UserRequestDTO.UserUpdateDTO userUpdateDTO, String userEmail);

    // 회원 조회
    UserResponseDTO.UserFindDTO findOne(String userEmail);
}
