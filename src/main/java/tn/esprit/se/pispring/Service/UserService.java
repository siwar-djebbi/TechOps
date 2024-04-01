package tn.esprit.se.pispring.Service;

import org.springframework.stereotype.Service;
import tn.esprit.se.pispring.DTO.Request.*;
import tn.esprit.se.pispring.DTO.Response.CurrentUserResponse;
import tn.esprit.se.pispring.DTO.Response.PageResponse;
import tn.esprit.se.pispring.DTO.Response.UserResponse;
import tn.esprit.se.pispring.entities.Prime;
import tn.esprit.se.pispring.entities.User;

import java.util.List;

public interface UserService {


    String signup(UserSignupRequest userReq) throws Exception;

    User findByEmail(String username);
    User createNewUser(String token, UserRequest userRequest) throws Exception;


    CurrentUserResponse getCurrentUserInfos(String token) throws Exception;

    CurrentUserResponse editCurrentUserInfos(String token, CurrentUserRequest request)throws Exception;

    String editPassword(String token, EditPasswordRequest request)throws Exception;

    List<UserResponse> getUsers(String token)throws Exception;
    List<User> getAllUsers();
    User retrieveUser(Long idUser);

    List<UserResponse> searchUsers(String token, SearchRequest searchRequest)throws Exception;

    void deleteUsers(DeleteUsersRequest deleteUsersRequest) throws Exception;

    PageResponse<UserResponse> findAll(Long page, Long size, String criteria, String direction, String searchTerm);
    UserResponse addUser(AddUserRequest request);



}
