package tn.esprit.se.pispring.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private String firstName;
    private String LastName;
    private String email;
    private Long id;
    //private String timing;
    private String role;

}
