package universe.universe.service.friend;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import universe.universe.common.exception.Exception404;
import universe.universe.common.exception.Exception500;
import universe.universe.dto.friend.FriendResponseDTO;
import universe.universe.entitiy.user.User;
import universe.universe.repository.friend.FriendRepository;
import universe.universe.repository.user.UserRepository;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class FriendServiceImpl implements FriendService {
    final private UserRepository userRepository;
    final private FriendRepository friendRepository;
    @Override
    public FriendResponseDTO.FriendFindAllDTO findAll(String userEmail) {
        try {
            User findUser = getUser(userEmail);
            FriendResponseDTO.FriendFindAllDTO findFriendList = friendRepository.friendFindAll(findUser.getId());
            return findFriendList;
        } catch (Exception e) {
            throw new Exception500("findAll fail : " + e.getMessage());
        }
    }
    private User getUser(String userEmail) {
        User findUser = userRepository.findByUserEmail(userEmail);
        if(findUser == null) {
            throw new Exception404("해당 유저를 찾을 수 없습니다.");
        }
        return findUser;
    }
}
