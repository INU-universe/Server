package universe.universe.domain.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import universe.universe.global.common.exception.CustomException;
import universe.universe.global.common.exception.Exception500;
import universe.universe.domain.user.dto.UserRequestDTO;
import universe.universe.domain.user.dto.UserResponseDTO;
import universe.universe.domain.user.entity.User;
import universe.universe.domain.user.repository.UserRepository;
import universe.universe.global.common.reponse.ErrorCode;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    final private UserRepository userRepository;
    final private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public UserResponseDTO.UserJoinDTO join(UserRequestDTO.UserJoinDTO userJoinDTO) {
        try {
            log.info("[UserServiceImpl] join");
            if (userRepository.existsByUserEmail(userJoinDTO.getUserEmail())) {
                throw new CustomException(ErrorCode.USER_EXIST);
            }

            userJoinDTO.setUserPassword(bCryptPasswordEncoder.encode(userJoinDTO.getUserPassword()));

            User user = userJoinDTO.toEntity();
            userRepository.save(user);
            return new UserResponseDTO.UserJoinDTO(user);
        } catch (CustomException ce){
            log.info("[CustomException] UserServiceImpl join");
            throw ce;
        } catch (Exception e){
            log.info("[Exception500] UserServiceImpl join");
            throw new CustomException(ErrorCode.SERVER_ERROR);
        }
    }

    @Override
    public void delete(String userEmail) {
        log.info("[UserServiceImpl] delete");
        try {
            if (!userRepository.existsByUserEmail(userEmail)) {
                throw new CustomException(ErrorCode.USER_NOT_FOUND);
            }

            Long findUserId = getUser_Email(userEmail).getId();
            userRepository.delete(findUserId);
        } catch (CustomException ce){
            log.info("[CustomException] UserServiceImpl delete");
            throw ce;
        } catch (Exception e){
            log.info("[Exception500] UserServiceImpl delete");
            throw new Exception500("user delete fail : " + e.getMessage());
        }
    }

    @Override
    public UserResponseDTO.UserFindDTO findOne(String userEmail) {
        try {
            log.info("[UserServiceImpl] findOne");
            UserResponseDTO.UserFindDTO result = userRepository.findOne(userEmail);
            return result;
        } catch (CustomException ce){
            log.info("[CustomException] UserServiceImpl delete");
            throw ce;
        } catch (Exception e) {
            log.info("[Exception500] UserServiceImpl findOne");
            throw new Exception500("user findOne fail : " + e.getMessage());
        }
    }

    //    @Override
//    public UserResponseDTO.UserUpdateDTO update(UserRequestDTO.UserUpdateDTO userUpdateDTO, String userEmail) {
//        if(!userUpdateDTO.getUserEmail().equals(userEmail)) {
//            throw new CustomException("userEmail", "회원이 맞지 않습니다.");
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

    private User getUser_Email(String userEmail) throws Exception {
        Optional<User> findUser = userRepository.findByUserEmail(userEmail);
        if(findUser == null) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }
        return findUser.get();
    }

    private User getUser_Id(Long userId) throws Exception {
        Optional<User> findUser = userRepository.findById(userId);
        if(!findUser.isPresent()) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }
        return findUser.get();
    }
}
