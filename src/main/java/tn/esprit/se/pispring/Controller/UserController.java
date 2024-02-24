package tn.esprit.se.pispring.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.se.pispring.DTO.Request.CurrentUserRequest;
import tn.esprit.se.pispring.DTO.Request.EditPasswordRequest;
import tn.esprit.se.pispring.DTO.Response.CurrentUserResponse;
import tn.esprit.se.pispring.Service.UserService;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping("/getCurrent")
    public ResponseEntity<?> getCurrentUserInfos(@RequestHeader(name = "Authorization") String token) throws Exception {
        try {
            CurrentUserResponse user = userService.getCurrentUserInfos(token);
            return ResponseEntity.ok(user);
        }catch (Exception e) {
            throw new Exception(e);
        }
    }

    @PostMapping("/editCurrent")
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


}
