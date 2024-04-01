package tn.esprit.se.pispring.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tn.esprit.se.pispring.DTO.Request.*;
import tn.esprit.se.pispring.DTO.Response.CurrentUserResponse;
import tn.esprit.se.pispring.DTO.Response.PageResponse;
import tn.esprit.se.pispring.DTO.Response.UserResponse;
import tn.esprit.se.pispring.Repository.RoleRepo;
import tn.esprit.se.pispring.Repository.UserRepository;
import tn.esprit.se.pispring.entities.ERole;
import tn.esprit.se.pispring.entities.Role;
import tn.esprit.se.pispring.entities.User;
import tn.esprit.se.pispring.secConfig.JwtUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
@Slf4j
public class UserImp implements UserService {


    @Value("${asserter.default.secret}")
    private  String DEFAULT_PASSWORD;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final RoleRepo roleRepo;
    private final JwtUtils jwtUtils;

    @Override
    public String signup(UserSignupRequest userReq)  {
        // Check if the password is null
        if (userReq.getPassword() == null) {
            // Log the error or handle it appropriately
            log.error("Password is null in signup request");
            return "Error: Password cannot be null";
        }

        // Proceed with user registration
        User user = new User();
        user.setEmail(userReq.getEmail());
        user.setPassword(passwordEncoder.encode(userReq.getPassword()));
        Role role = roleRepo.findRoleByRoleName(ERole.ROLE_USER);
        List<Role> roles = Collections.singletonList(role);
        user.setRoles(roles);
        User  u = userRepository.save(user);
        return "Done";
    }

    @Override
    public User findByEmail(String username) {
        return userRepository.findByEmail(username);
    }

    @Override
    public User createNewUser(String token, UserRequest userRequest) throws Exception {
        log.info(userRequest.toString());
        try {

            User newUser = new User();
            List<Role> roles = new ArrayList<>();
            roles.add(roleRepo.findRoleByRoleName(ERole.ROLE_USER));
            if(userRequest.getRole().equalsIgnoreCase("hre"))
                roles.add(roleRepo.findRoleByRoleName(ERole.ROLE_HRE));
            newUser.setRoles(roles);
            newUser.setPassword(passwordEncoder.encode(DEFAULT_PASSWORD));
            newUser.setFirstName(userRequest.getFirstName());
            newUser.setLastName(userRequest.getLastName());

            newUser.setEmail(userRequest.getEmail());

            newUser.setEnabled(true);
            return userRepository.save(newUser);
        } catch (Exception e) {
            throw new Exception(e);
        }

    }


    @Override
    public CurrentUserResponse getCurrentUserInfos(String token) throws Exception {
        try {
            User user = userRepository.findByEmail(jwtUtils.getUsernameFromToken(token.split(" ")[1].trim()));
            return new CurrentUserResponse(
                    user.getId(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getEmail()
            );
        }catch (Exception e) {
            throw new Exception(e);

        }
    }

    @Override
    public CurrentUserResponse editCurrentUserInfos(String token, CurrentUserRequest request) throws Exception {
        try {
            User user = userRepository.findByEmail(jwtUtils.getUsernameFromToken(token.split(" ")[1].trim()));
//            if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())){
//                throw new Exception("incorrect password");
//            }
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            user.setEmail(request.getEmail());
            User savedUser = userRepository.save(user);
            return new CurrentUserResponse(
                    savedUser.getId(),
                    savedUser.getFirstName(),
                    savedUser.getLastName(),
                    savedUser.getEmail()
            );
        }catch (Exception e) {
            throw new Exception(e);
        }
    }

    @Override
    public String editPassword(String token, EditPasswordRequest request) throws Exception {
        try {
            if (!request.getNewPassword().equals(request.getRetypedNewPassword())) {
                throw new Exception("passwords don't match");
            }
            User user = userRepository.findByEmail(jwtUtils.getUsernameFromToken(token.split(" ")[1].trim()));
            if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
                throw new Exception("incorrect password");
            }
            user.setPassword(passwordEncoder.encode(request.getNewPassword()));
            userRepository.save(user);
            return "success";
        }catch (Exception e) {
            throw new Exception(e);
        }
    }

    @Override
    public List<UserResponse> getUsers(String token) throws Exception {
        try {

            return userRepository.findUsers(userRepository.
                            findByEmail(jwtUtils.getUsernameFromToken(token.split(" ")[1].trim())).
                            getPortfolio()).stream().filter(User -> !User.getRoles().
                            contains(roleRepo.findRoleByRoleName(ERole.ROLE_USER)))
                    .map(User -> new UserResponse(
                    ))
                    .collect(Collectors.toList());

        }catch (Exception e) {
            throw new Exception(e);
        }
    }

    @Override

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User retrieveUser(Long idUser) {
        return userRepository.findById(idUser).get();
    }

    @Override

    public List<UserResponse> searchUsers(String token, SearchRequest searchRequest) throws Exception {
        try {
            // Retrieve users based on the search keyword
            List<User> users = userRepository.searchUsers(searchRequest.getKeyword());

            // Filter out users with roles containing ROLE_ADMIN
            List<User> filteredUsers = users.stream()
                    .filter(user -> user.getRoles().stream().noneMatch(role -> role.getRoleName() == ERole.ROLE_ADMIN))
                    .collect(Collectors.toList());

            // Map the filtered users to UserResponse objects
            List<UserResponse> userResponses = filteredUsers.stream()
                    .map(user -> new UserResponse(
                            user.getId(),
                            user.getFirstName(),
                            user.getLastName(),
                            user.getEmail(),
                            user.getTelephone() // Assuming role should always be "user"
                    ))
                    .collect(Collectors.toList());

            return userResponses;
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @Override
    public void deleteUsers(DeleteUsersRequest deleteUsersRequest) throws Exception {
        log.info("#########################");
        log.info("this is the userRequestEmailsSize {}", deleteUsersRequest.getEmails().size());
        try {
            if (deleteUsersRequest.getEmails().size() == 1) {
                String email = deleteUsersRequest.getEmails().get(0);
                User user = userRepository.findAppUserByEmail(email);
                if (user != null) {
                    userRepository.delete(user);
                }
            } else {
                for (String email : deleteUsersRequest.getEmails()) {
                    User user = userRepository.findAppUserByEmail(email);
                    if (user != null) {
                        userRepository.delete(user);
                    }
                }
            }
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @Override
    public PageResponse<UserResponse> findAll(Long page, Long size, String criteria, String direction, String searchTerm) {

        Sort sort = Sort.by(Objects.equals(direction, "asc") ? Sort.Direction.ASC : Sort.Direction.DESC,criteria);

        PageRequest pageRequest = PageRequest.of(Math.toIntExact(page), Math.toIntExact(size), sort);
        Page<User> result;
        if (searchTerm != null && !searchTerm.isEmpty()) {
            result = userRepository.findAll(pageRequest);
        }else {
            result = userRepository.searchBy(pageRequest, searchTerm);
        }


        PageResponse<UserResponse> response = new PageResponse<>();
        response.setContent(result.getContent().stream().map(user -> new UserResponse(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getTelephone())).toList());
        response.setPageNumber(result.getNumber());
        response.setTotalElements(result.getTotalElements());
        response.setTotalPages(response.getTotalPages());

        return response;
    }

    @Override
    public UserResponse addUser(AddUserRequest request) {


        User user = new User();
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setTelephone(request.getPhoneNumber());
        user.setPassword(passwordEncoder.encode("123456789"));

        User saved = userRepository.save(user);

        return new UserResponse(saved.getId(), saved.getFirstName(), saved.getLastName(), saved.getEmail(), saved.getTelephone());
    }


}
