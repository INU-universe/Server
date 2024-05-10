package universe.universe.domain.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import universe.universe.domain.user.entity.UserStatus;
import universe.universe.global.common.exception.CustomException;
import universe.universe.domain.user.dto.UserRequestDTO;
import universe.universe.domain.user.dto.UserResponseDTO;
import universe.universe.domain.user.entity.User;
import universe.universe.domain.user.repository.UserRepository;
import universe.universe.global.common.exception.Exception500;
import universe.universe.global.common.reponse.ErrorCode;
import universe.universe.global.common.CommonMethod;

import static universe.universe.domain.user.entity.UserStatus.NOT_SCHOOL;
import static universe.universe.domain.user.entity.UserStatus.SCHOOL;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    final private UserRepository userRepository;
    final private CommonMethod commonMethod;
    final private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    @Transactional
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
    @Transactional
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
    @Transactional
    public UserResponseDTO.UserUpdateSchoolDTO updateSchool(UserRequestDTO.UserUpdateSchoolDTO userUpdateSchoolDTO, String userEmail) {
        try {
            log.info("[UserServiceImpl] updateSchool");
            User findUser = commonMethod.getUser("email", userEmail);
            if(!userUpdateSchoolDTO.getUserStatus().equals("SCHOOL")) {
                throw new CustomException(ErrorCode.USER_STATUS_NOT_FOUND);
            }
            findUser.updateUserStatus(SCHOOL);
            return new UserResponseDTO.UserUpdateSchoolDTO(findUser);
        } catch (CustomException ce){
            log.info("[CustomException] UserServiceImpl updateSchool");
            throw ce;
        } catch (Exception e){
            log.info("[Exception500] UserServiceImpl updateSchool");
            throw new CustomException(ErrorCode.SERVER_ERROR, "[Exception500] UserServiceImpl updateSchool : " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public UserResponseDTO.userUpdateNotSchoolDTO updateNotSchool(UserRequestDTO.UserUpdateNotSchoolDTO userUpdateNotSchoolDTO, String userEmail) {
        try {
            log.info("[UserServiceImpl] updateNotSchool");
            User findUser = commonMethod.getUser("email", userEmail);
            if(!userUpdateNotSchoolDTO.getUserStatus().equals("NOT_SCHOOL")) {
                throw new CustomException(ErrorCode.USER_STATUS_NOT_FOUND);
            }
            findUser.updateUserStatus(NOT_SCHOOL);
            return new UserResponseDTO.userUpdateNotSchoolDTO(findUser);
        } catch (CustomException ce){
            log.info("[CustomException] UserServiceImpl updateNotSchool");
            throw ce;
        } catch (Exception e){
            log.info("[Exception500] UserServiceImpl updateNotSchool");
            throw new CustomException(ErrorCode.SERVER_ERROR, "[Exception500] UserServiceImpl updateNotSchool : " + e.getMessage());
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
}
