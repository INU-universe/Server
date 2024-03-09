package universe.universe.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import universe.universe.common.exception.Exception400;
import universe.universe.common.exception.Exception404;
import universe.universe.common.exception.Exception500;
import universe.universe.dto.user.UserRequestDTO;
import universe.universe.dto.user.UserResponseDTO;
import universe.universe.entitiy.user.User;
import universe.universe.repository.user.UserRepository;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    final private UserRepository userRepository;
    final private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public UserResponseDTO.UserJoinDTO join(UserRequestDTO.UserJoinDTO userJoinDTO) {
        if (userRepository.existsByUserEmail(userJoinDTO.getUserEmail())) {
            throw new Exception400("userEmail", "이미 가입된 이메일 주소입니다.");
        }
        userJoinDTO.setUserPassword(bCryptPasswordEncoder.encode(userJoinDTO.getUserPassword()));

        try {
            User user = userJoinDTO.toEntity();
            userRepository.save(user);
            return new UserResponseDTO.UserJoinDTO(user);
        } catch (Exception e){
            throw new Exception500("회원 가입 실패 : "+e.getMessage());
        }
    }

    @Override
    public void delete(String userEmail) {
        if (!userRepository.existsByUserEmail(userEmail)) {
            throw new Exception400("userEmail", "존재하지 않은 이메일입니다.");
        }
        try {
            User findUser = userRepository.findByUserEmail(userEmail);
            userRepository.delete(findUser);
        } catch (Exception e){
            throw new Exception500("회원 탈퇴 실패 : "+e.getMessage());
        }
    }

//    @Override
//    public UserResponseDTO.UserUpdateDTO update(UserRequestDTO.UserUpdateDTO userUpdateDTO, String userEmail) {
//        if(!userUpdateDTO.getUserEmail().equals(userEmail)) {
//            throw new Exception400("userEmail", "회원이 맞지 않습니다.");
//        }
//
//        try {
//            User findUser = userRepository.findByUserEmail(userEmail);
//            findUser.updateUserImg(userUpdateDTO.getUserImg());
//            return new UserResponseDTO.UserUpdateDTO(findUser);
//        } catch (Exception e){
//            throw new Exception500("회원 수정 실패 : "+e.getMessage());
//        }
//    }

    @Override
    public UserResponseDTO.UserFindDTO find(String userEmail) {
        try {
            UserResponseDTO.UserFindDTO findUser = userRepository.findOne(userEmail);
            return findUser;
        } catch (Exception e) {
            throw new Exception404("회원 조회 실패 : "+e.getMessage());
        }
    }
}
