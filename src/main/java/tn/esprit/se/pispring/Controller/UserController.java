package tn.esprit.se.pispring.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tn.esprit.se.pispring.DTO.Request.*;
import tn.esprit.se.pispring.DTO.Response.CurrentUserResponse;
import tn.esprit.se.pispring.DTO.Response.UserResponse;
import tn.esprit.se.pispring.Service.RoleService;
import tn.esprit.se.pispring.Service.UserService;
import tn.esprit.se.pispring.entities.Role;
import tn.esprit.se.pispring.entities.ERole;
import tn.esprit.se.pispring.entities.User;
import tn.esprit.se.pispring.events.NewUserAddedEvent;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins ="http://localhost:4200")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    @Value("${asserter.default.secret}")
    private String DEFAULT_PASSWORD;
    private final UserService userService;
    private final UserRepository userRepo;
    private final ApplicationEventPublisher publisher;
    private final RoleService roleService;


    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_HR_ADMIN', 'ROLE_CRM_ADMIN', 'ROLE_PROJECT_ADMIN', 'ROLE_PRODUCT_ADMIN')")
    public ResponseEntity<?> createNewUser(final HttpServletRequest request, @RequestHeader(name = "Authorization") String token, @RequestBody @Valid UserRequest userRequest) throws Exception {
        try {
            User newUser = userService.createNewUser(token, userRequest);
            if (newUser != null) {
                publisher.publishEvent(new NewUserAddedEvent(applicationUrl(request), newUser, DEFAULT_PASSWORD));

            }
            Role role = roleService.findRoleByRoleName(ERole.ROLE_USER);
            assert newUser != null;
            return ResponseEntity.ok(new UserResponse(newUser.getId(),newUser.getFirstName(), newUser.getFirstName(), newUser.getEmail(),  newUser.getTelephone()));

        }catch (Exception e) {
            throw new Exception(e);
        }
    }

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
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_HR_ADMIN', 'ROLE_CRM_ADMIN', 'ROLE_PROJECT_ADMIN', 'ROLE_PRODUCT_ADMIN')")
    public ResponseEntity<?> getUsers(@RequestHeader(name = "Authorization") String token) throws Exception {
        try {
            List<UserResponse> users = userService.getUsers(token);
            return ResponseEntity.ok(users);
        }catch (Exception e) {
            throw new Exception(e);
        }

    }

    @PostMapping("/search")
    // @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_HR_ADMIN', 'ROLE_CRM_ADMIN', 'ROLE_PROJECT_ADMIN', 'ROLE_PRODUCT_ADMIN')")
    public ResponseEntity<?> searchUsers(@RequestHeader(name = "Authorization") String token, @RequestBody SearchRequest searchRequest) throws Exception {

        try {

            List<UserResponse> users = userService.searchUsers(token, searchRequest);
            return ResponseEntity.ok(users);


        }catch (Exception e) {
            throw new Exception(e);
        }


    }

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_HR_ADMIN', 'ROLE_CRM_ADMIN', 'ROLE_PROJECT_ADMIN', 'ROLE_PRODUCT_ADMIN')")
    public Iterable<User> findConnectedUsers() {
        return userRepo.findAll();
    }

    private String applicationUrl(HttpServletRequest httpServletRequest) {
        return "http://" + httpServletRequest.getServerName() + ":" + httpServletRequest.getServerPort() + httpServletRequest.getContextPath();
    }

    @PostMapping("/delete")
    //@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_HR_ADMIN', 'ROLE_CRM_ADMIN', 'ROLE_PROJECT_ADMIN', 'ROLE_PRODUCT_ADMIN')")
    public ResponseEntity<?> deleteUser(@RequestBody @Valid DeleteUsersRequest deleteUsersRequest) throws Exception {

        try {
            userService.deleteUsers(deleteUsersRequest);

            return ResponseEntity.ok("success deleting users");
        }catch (Exception e) {
            throw new Exception(e);
        }

    }

}
