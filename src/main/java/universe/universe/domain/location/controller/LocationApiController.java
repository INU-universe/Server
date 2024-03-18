package universe.universe.domain.location.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import universe.universe.global.common.exception.Exception500;
import universe.universe.global.common.reponse.ApiResponse;
import universe.universe.domain.location.dto.LocationRequestDTO;
import universe.universe.domain.location.dto.LocationResponseDTO;
import universe.universe.domain.user.entity.User;
import universe.universe.global.auth.service.AuthenticationService;
import universe.universe.domain.location.service.LocationServiceImpl;
import universe.universe.domain.user.service.UserServiceImpl;

@RestController
@RequestMapping("/api/v1/user/location")
@RequiredArgsConstructor
@Slf4j
public class LocationApiController {
    final private UserServiceImpl userService;
    final private AuthenticationService authenticationService;
    final private LocationServiceImpl locationService;

    @PostMapping("/update")
    public ResponseEntity<?> update(@RequestBody LocationRequestDTO.LocationUpdateDTO locationUpdateDTO) {
        try {
            log.info("[LocationApiController] update");
            String userEmail = getUserEmail();
            LocationResponseDTO.LocationUpdateDTO result = locationService.update(locationUpdateDTO, userEmail);
            return ResponseEntity.ok().body(ApiResponse.SUCCESS(HttpStatus.CREATED.value(), "location update success", result));
        } catch (Exception500 e) {
            log.info("[Exception500] LocationApiController update");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.ERROR(e.status().value(), e.getMessage()));
        }
    }

    @GetMapping("/findOne")
    public ResponseEntity<?> findOne() {
        try {
            log.info("[LocationApiController] findOne");
            String userEmail = getUserEmail();
            LocationResponseDTO.LocationFindOneDTO result = locationService.findOne(userEmail);
            return ResponseEntity.ok().body(ApiResponse.SUCCESS(HttpStatus.CREATED.value(), "location findOne success", result));
        } catch (Exception500 e) {
            log.info("[Exception500] LocationApiController findOne");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.ERROR(e.status().value(), e.getMessage()));
        }
    }
    @GetMapping("notFavorite/findAll")
    public ResponseEntity<?> notFavoriteFindAll() {
        try {
            log.info("[LocationApiController] notFavoriteFindAll");
            String userEmail = getUserEmail();
            LocationResponseDTO.LocationFindAllDTO result = locationService.notFavoriteFindAll(userEmail);
            return ResponseEntity.ok().body(ApiResponse.SUCCESS(HttpStatus.CREATED.value(), "location notFavoriteFindAll success", result));
        } catch (Exception500 e) {
            log.info("[Exception500] LocationApiController notFavoriteFindAll");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.ERROR(e.status().value(), e.getMessage()));
        }
    }

    @GetMapping("favorite/findAll")
    public ResponseEntity<?> favoriteFindAll() {
        try {
            log.info("[LocationApiController] favoriteFindAll");
            String userEmail = getUserEmail();
            LocationResponseDTO.LocationFindAllDTO result = locationService.favoriteFindAll(userEmail);
            return ResponseEntity.ok().body(ApiResponse.SUCCESS(HttpStatus.CREATED.value(), "location favoriteFindAll success", result));
        } catch (Exception500 e) {
            log.info("[Exception500] LocationApiController favoriteFindAll");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.ERROR(e.status().value(), e.getMessage()));
        }
    }


    private String getUserEmail() {
        User user = authenticationService.getCurrentAuthenticatedUser();
        String userEmail = user.getUserEmail();
        return userEmail;
    }
}
