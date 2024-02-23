package tn.esprit.se.pispring.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tn.esprit.se.pispring.DTO.Request.UserSignupRequest;
import tn.esprit.se.pispring.Repository.RoleRepo;
import tn.esprit.se.pispring.Repository.UserRepository;
import tn.esprit.se.pispring.entities.ERole;
import tn.esprit.se.pispring.entities.Role;
import tn.esprit.se.pispring.entities.User;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@RequiredArgsConstructor
@Service
public class UserImp implements UserService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final RoleRepo roleRepo;

    @Override
    public String signup(UserSignupRequest userReq) throws Exception {
        try {
            User user = new User();
            user.setEmail(userReq.getEmail());
            user.setPassword(passwordEncoder.encode(userReq.getPassword()));
            Role role = roleRepo.findRoleByRoleName(ERole.ROLE_USER);
            List<Role> roles = Collections.singletonList(role);
            user.setRoles(roles);
            User  u = userRepository.save(user);
            return "Done";
        } catch (Exception e) {
            throw new Exception("error adding the new user");
        }

    }

    @Override
    public User findByEmail(String username) {
        return userRepository.findByEmail(username);
    }
}
