package universe.universe.controller.location;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import universe.universe.common.exception.Exception400;
import universe.universe.common.exception.Exception500;
import universe.universe.common.reponse.ApiResponse;
import universe.universe.dto.location.LocationRequestDTO;
import universe.universe.dto.location.LocationResponseDTO;
import universe.universe.entitiy.user.User;
import universe.universe.service.auth.AuthenticationService;
import universe.universe.service.location.LocationServiceImpl;
import universe.universe.service.user.UserServiceImpl;

@RestController
@RequestMapping("/api/location")
@RequiredArgsConstructor
@Slf4j
public class LocationApiController {
    final private UserServiceImpl userService;
    final private AuthenticationService authenticationService;
    final private LocationServiceImpl locationService;

    @PostMapping("/update")
    public ResponseEntity<?> update(@RequestBody LocationRequestDTO.LocationUpdateDTO locationUpdateDTO) {
        try {
            String userEmail = getUserEmail();
            LocationResponseDTO.LocationUpdateDTO updateLocation = locationService.update(locationUpdateDTO, userEmail);
            return ResponseEntity.ok().body(ApiResponse.SUCCESS(HttpStatus.CREATED.value(), "위치 변이 완료되었습니다.", updateLocation));
        } catch (Exception400 e) {
            return ResponseEntity.badRequest().body(ApiResponse.FAILURE(e.status().value(), e.getMessage()));
        } catch (Exception500 e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.ERROR(e.status().value(), e.getMessage()));
        }
    }

    @GetMapping("/findOne")
    public ResponseEntity<?> findOne() {
        try {
            String userEmail = getUserEmail();
            LocationResponseDTO.LocationFindOneDTO findLocation = locationService.findOne(userEmail);
            return ResponseEntity.ok().body(ApiResponse.SUCCESS(HttpStatus.CREATED.value(), "위치 조회 성공입니다.", findLocation));
        } catch (Exception400 e) {
            return ResponseEntity.badRequest().body(ApiResponse.FAILURE(e.status().value(), e.getMessage()));
        } catch (Exception500 e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.ERROR(e.status().value(), e.getMessage()));
        }
    }
    @GetMapping("notFavorite/findAll")
    public ResponseEntity<?> notFavoriteFindAll() {
        try {
            String userEmail = getUserEmail();
            LocationResponseDTO.LocationFindAllDTO findLocationList = locationService.notFavoriteFindAll(userEmail);
            return ResponseEntity.ok().body(ApiResponse.SUCCESS(HttpStatus.CREATED.value(), "위치 조회 성공입니다.", findLocationList));
        } catch (Exception400 e) {
            return ResponseEntity.badRequest().body(ApiResponse.FAILURE(e.status().value(), e.getMessage()));
        } catch (Exception500 e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.ERROR(e.status().value(), e.getMessage()));
        }
    }

    @GetMapping("favorite/findAll")
    public ResponseEntity<?> favoriteFindAll() {
        try {
            String userEmail = getUserEmail();
            LocationResponseDTO.LocationFindAllDTO findLocationList = locationService.favoriteFindAll(userEmail);
            return ResponseEntity.ok().body(ApiResponse.SUCCESS(HttpStatus.CREATED.value(), "위치 조회 성공입니다.", findLocationList));
        } catch (Exception400 e) {
            return ResponseEntity.badRequest().body(ApiResponse.FAILURE(e.status().value(), e.getMessage()));
        } catch (Exception500 e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.ERROR(e.status().value(), e.getMessage()));
        }
    }


    private String getUserEmail() {
        User user = authenticationService.getCurrentAuthenticatedUser();
        String userEmail = user.getUserEmail();
        return userEmail;
    }
}
