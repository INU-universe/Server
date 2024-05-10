package universe.universe.domain.user.service;

import universe.universe.domain.user.dto.UserRequestDTO;
import universe.universe.domain.user.dto.UserResponseDTO;

public interface UserService {
    // 회원 가입
    UserResponseDTO.UserJoinDTO join(UserRequestDTO.UserJoinDTO userJoinDTO);

    // 회원 탈퇴
    UserResponseDTO.UserDeleteDTO delete(String userEmail);

    // 회원 학교 상태 수정
    UserResponseDTO.UserUpdateSchoolDTO updateSchool(UserRequestDTO.UserUpdateSchoolDTO userUpdateSchoolDTO, String userEmail);

    // 회원 학교 상태 수정
    UserResponseDTO.userUpdateNotSchoolDTO updateNotSchool(UserRequestDTO.UserUpdateNotSchoolDTO userUpdateNotSchoolDTO, String userEmail);

    // 회원 조회
    UserResponseDTO.UserFindDTO findOne(String userEmail);
}
