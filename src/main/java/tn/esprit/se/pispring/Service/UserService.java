package tn.esprit.se.pispring.Service;

import tn.esprit.se.pispring.DTO.LoginDTO;
import tn.esprit.se.pispring.DTO.UserDTO;
import tn.esprit.se.pispring.Entity.User;

import java.util.List;

public interface UserService {
    User getUserById(Long userId);

    User save(UserDTO userDTO);

    User update(Long userId, User userDTO);

    UserDTO loginUser(LoginDTO loginDTO);

    User addPreferencesToUser(Long userId, List<String> selectedTypes);



    User disconnect(User user);

}
