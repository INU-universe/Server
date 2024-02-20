package universe.universe.service.friend;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import universe.universe.common.exception.Exception401;
import universe.universe.common.exception.Exception404;
import universe.universe.common.exception.Exception500;
import universe.universe.dto.friend.FriendRequestResponseDTO;
import universe.universe.entitiy.friend.Friend;
import universe.universe.entitiy.friend.FriendRequest;
import universe.universe.entitiy.user.User;
import universe.universe.repository.friend.FriendRepository;
import universe.universe.repository.friend.FriendRequestRepository;
import universe.universe.repository.user.UserRepository;

import java.util.Objects;
import java.util.Optional;

import static universe.universe.entitiy.friend.FriendStatus.NOT_FAVORITE;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class FriendRequestServiceImpl implements FriendRequestService {
    final private UserRepository userRepository;
    final private FriendRepository friendRepository;
    final private FriendRequestRepository friendRequestRepository;
    @Override
    public FriendRequestResponseDTO.FriendRequestToggleDTO toggle(String userEmail, Long toUserId) {
        try {
            User fromUser = getUser(userEmail);
            Optional<User> findToUser = userRepository.findById(toUserId);
            if(findToUser.isEmpty()) {
                throw new Exception404("해당 유저를 찾을 수 없습니다.");
            }
            else if(Objects.equals(fromUser.getId(), toUserId)) {
                throw new Exception401("본인에게 친구 추가는 불가합니다.");
            }
            User toUser = findToUser.get();

            Optional<FriendRequest> friendRequestExist = friendRequestRepository.findByFromUserAndToUser(fromUser, toUser);
            Optional<Friend> friendExist = friendRepository.findByFromUserAndToUser(fromUser, toUser);

            if(friendExist.isPresent()) {
                return null;
            }
            else if(friendRequestExist.isPresent()) {
                FriendRequest deleteFindExist = friendRequestExist.get();
                friendRequestRepository.delete(deleteFindExist);
                return null;
            }
            else {
                FriendRequest friendRequest = new FriendRequest(fromUser, toUser);
                friendRequestRepository.save(friendRequest);
                return new FriendRequestResponseDTO.FriendRequestToggleDTO(friendRequest);
            }
        } catch (Exception e) {
            throw new Exception500("toggle fail : " + e.getMessage());
        }
    }

    @Override
    public FriendRequestResponseDTO.FriendRequestAcceptDTO accept(String userEmail, Long friendRelationShipId) {
        try {
            User findUser = getUser(userEmail);
            Optional<FriendRequest> findExist = friendRequestRepository.findById(friendRelationShipId);
            if(!Objects.equals(findUser.getId(), findExist.get().getToUser().getId())) {
                throw new Exception401("권한이 없습니다.");
            }

            if(findExist.isPresent()) {
                FriendRequest findFriendRequest = findExist.get();
                Friend friend1 = new Friend(findFriendRequest.getFromUser(), findFriendRequest.getToUser(), NOT_FAVORITE);
                Friend friend2 = new Friend(findFriendRequest.getToUser(), findFriendRequest.getFromUser(), NOT_FAVORITE);
                friendRepository.save(friend1);
                friendRepository.save(friend2);
                friendRequestRepository.delete(findFriendRequest);
                return new FriendRequestResponseDTO.FriendRequestAcceptDTO(findFriendRequest);
            }
            else {
                throw new Exception404("해당 관계를 찾을 수 없습니다.");
            }
        } catch (Exception e) {
            throw new Exception500("accept fail : " + e.getMessage());
        }
    }

    @Override
    public FriendRequestResponseDTO.FriendRequestRejectDTO reject(String userEmail, Long friendRelationShipId) {
        try {
            User findUser = getUser(userEmail);
            Optional<FriendRequest> findExist = friendRequestRepository.findById(friendRelationShipId);
            if(!Objects.equals(findUser.getId(), findExist.get().getToUser().getId())) {
                throw new Exception401("권한이 없습니다.");
            }

            if(findExist.isPresent()) {
                FriendRequest findFriendRequest = findExist.get();
                friendRequestRepository.delete(findFriendRequest);
                return new FriendRequestResponseDTO.FriendRequestRejectDTO(findFriendRequest);
            }
            else {
                throw new Exception404("해당 관계를 찾을 수 없습니다.");
            }
        } catch (Exception e) {
            throw new Exception500("reject fail : " + e.getMessage());
        }
    }

    @Override
    public FriendRequestResponseDTO.FriendRequestFindAllDTO findAll(String userEmail) {
        try {
            User findUser = getUser(userEmail);
            FriendRequestResponseDTO.FriendRequestFindAllDTO findFriendRequestList = friendRequestRepository.findAll(findUser.getId());
            return findFriendRequestList;
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
