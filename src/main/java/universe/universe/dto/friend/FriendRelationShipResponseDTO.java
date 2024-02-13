package universe.universe.dto.friend;

import lombok.Getter;
import lombok.Setter;
import universe.universe.entitiy.friend.FriendRelationShip;

public class FriendRelationShipResponseDTO {
    @Setter
    @Getter
    public static class FriendRelationShipToggleDTO {
        private Long id;
        private Long toUserId;
        private Long fromUserId;
        public FriendRelationShipToggleDTO(FriendRelationShip friendRelationShip) {
            this.id = friendRelationShip.getId();
            this.toUserId = friendRelationShip.getToUser().getId();
            this.fromUserId = friendRelationShip.getFromUser().getId();
        }
    }
    @Setter
    @Getter
    public static class FriendRelationShipFindAllDTO {
        private Long id;
        private Long toUserId;
        private Long fromUserId;
        public FriendRelationShipFindAllDTO(FriendRelationShip friendRelationShip) {
            this.id = friendRelationShip.getId();
            this.toUserId = friendRelationShip.getToUser().getId();
            this.fromUserId = friendRelationShip.getFromUser().getId();
        }
    }
}
