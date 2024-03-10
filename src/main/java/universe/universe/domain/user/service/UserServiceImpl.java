package universe.universe.domain.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import universe.universe.global.exception.Exception400;
import universe.universe.global.exception.Exception404;
import universe.universe.global.exception.Exception500;
import universe.universe.domain.user.dto.UserRequestDTO;
import universe.universe.domain.user.dto.UserResponseDTO;
import universe.universe.domain.user.entity.User;
import universe.universe.domain.user.repository.UserRepository;

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
            throw new Exception500("user join fail : " + e.getMessage());
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
            throw new Exception500("user delete fail : " + e.getMessage());
        }
    }

    @Override
    public UserResponseDTO.UserFindDTO findOne(String userEmail) {
        try {
            UserResponseDTO.UserFindDTO result = userRepository.findOne(userEmail);
            return result;
        } catch (Exception e) {
            throw new Exception404("user findOne fail : " + e.getMessage());
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
}
