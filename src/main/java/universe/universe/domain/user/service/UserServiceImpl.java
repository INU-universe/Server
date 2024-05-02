package universe.universe.domain.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import universe.universe.global.common.exception.CustomException;
import universe.universe.domain.user.dto.UserRequestDTO;
import universe.universe.domain.user.dto.UserResponseDTO;
import universe.universe.domain.user.entity.User;
import universe.universe.domain.user.repository.UserRepository;
import universe.universe.global.common.reponse.ErrorCode;
import universe.universe.global.common.CommonMethod;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    final private UserRepository userRepository;
    final private CommonMethod commonMethod;
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
            throw new CustomException(ErrorCode.SERVER_ERROR, "[Exception500] UserServiceImpl join : " + e.getMessage());
        }
    }

    @Override
    public UserResponseDTO.UserDeleteDTO delete(String userEmail) {
        log.info("[UserServiceImpl] delete");
        try {
            if (!userRepository.existsByUserEmail(userEmail)) {
                throw new CustomException(ErrorCode.USER_NOT_FOUND);
            }

            User findUser = commonMethod.getUser("email" ,userEmail);
            userRepository.delete(findUser.getId());
            return new UserResponseDTO.UserDeleteDTO(findUser);
        } catch (CustomException ce){
            log.info("[CustomException] UserServiceImpl delete");
            throw ce;
        } catch (Exception e){
            log.info("[Exception500] UserServiceImpl delete");
            throw new CustomException(ErrorCode.SERVER_ERROR, "[Exception500] UserServiceImpl delete : " + e.getMessage());
        }
    }

    @Override
    public UserResponseDTO.UserFindDTO findOne(String userEmail) {
        try {
            log.info("[UserServiceImpl] findOne");
            return userRepository.findOne(userEmail);
        } catch (CustomException ce){
            log.info("[CustomException] UserServiceImpl delete");
            throw ce;
        } catch (Exception e) {
            log.info("[Exception500] UserServiceImpl findOne");
            throw new CustomException(ErrorCode.SERVER_ERROR, "[Exception500] UserServiceImpl findOne : " + e.getMessage());
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
}
