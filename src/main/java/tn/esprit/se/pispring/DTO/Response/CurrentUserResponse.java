package tn.esprit.se.pispring.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CurrentUserResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
}
