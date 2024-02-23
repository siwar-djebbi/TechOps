package tn.esprit.se.pispring.Service;

import org.springframework.stereotype.Service;
import tn.esprit.se.pispring.DTO.Request.UserSignupRequest;
import tn.esprit.se.pispring.entities.User;

public interface UserService {


    String signup(UserSignupRequest userReq) throws Exception;

    User findByEmail(String username);
}
