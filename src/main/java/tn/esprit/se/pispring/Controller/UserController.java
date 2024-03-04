package tn.esprit.se.pispring.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tn.esprit.se.pispring.DTO.Request.CurrentUserRequest;
import tn.esprit.se.pispring.DTO.Request.EditPasswordRequest;
import tn.esprit.se.pispring.DTO.Request.SearchRequest;
import tn.esprit.se.pispring.DTO.Response.CurrentUserResponse;
import tn.esprit.se.pispring.DTO.Response.UserResponse;
import tn.esprit.se.pispring.Repository.UserRepository;
import tn.esprit.se.pispring.Service.UserService;
import tn.esprit.se.pispring.entities.User;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final UserRepository userRepo;

    @GetMapping("/getCurrent")
    public ResponseEntity<?> getCurrentUserInfos(@RequestHeader(name = "Authorization") String token) throws Exception {
        try {
            CurrentUserResponse user = userService.getCurrentUserInfos(token);
            return ResponseEntity.ok(user);
        }catch (Exception e) {
            throw new Exception(e);
        }
    }

    @PutMapping("/editCurrent")
    public ResponseEntity<?> editCurrent(@RequestHeader(name = "Authorization") String token, @RequestBody CurrentUserRequest request) throws Exception {
        try {
            CurrentUserResponse user = userService.editCurrentUserInfos(token, request);
            return ResponseEntity.ok(user);
        }catch (Exception e) {
            throw new Exception(e);
        }
    }

    @PostMapping("/editPassword")
    public ResponseEntity<?> editPassword(@RequestHeader("Authorization") String token, @RequestBody EditPasswordRequest request) throws Exception {
        try {
            String result = userService.editPassword(token, request);
            return ResponseEntity.ok(result);
        }catch (Exception e) {
            throw new Exception(e);
        }
    }

    @GetMapping("/alll")
    //@PreAuthorize("hasAnyRole('ROLE_OWNER', 'ROLE_STAFF')")
    public ResponseEntity<?> getUsers(@RequestHeader(name = "Authorization") String token) throws Exception {
        try {
            List<UserResponse> users = userService.getUsers(token);
            return ResponseEntity.ok(users);
        }catch (Exception e) {
            throw new Exception(e);
        }

    }

    @PostMapping("/search")
    //@PreAuthorize("hasAnyRole('ROLE_OWNER', 'ROLE8STAFF')")
    public ResponseEntity<?> searchUsers(@RequestHeader(name = "Authorization") String token, @RequestBody SearchRequest searchRequest) throws Exception {

        try {

            List<UserResponse> users = userService.searchUsers(token, searchRequest);
            return ResponseEntity.ok(users);


        }catch (Exception e) {
            throw new Exception(e);
        }


    }

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<User> findConnectedUsers() {
        return userRepo.findAll();
    }


}
