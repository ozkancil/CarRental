package cars.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {

    @Size(max=50)
    @NotBlank(message = "please provide your first name")
    private String firstName;
    @Size(max = 50)
    @NotBlank(message = "please provide your last name")
    private String lastName;
    @Email(message = "please provide valid email")
    @Size(min=4,max=80)
    private String email;
    @Size(min = 4,max = 20,message = "please provide correct size for password")
    @NotBlank(message = "please provide your password")
    private String password;
    @Size(min = 14,max = 14)
    @NotBlank(message = "Please provide your phone number ")
    @Pattern(regexp = "\\d{3}-\\d{3}-\\d{4}", message = "Invalid phone number format")
    private String phoneNumber;
    @Size(max=100)
    @NotBlank(message = "please provide your address")
    private String address;
    @Size(max = 15)
    @NotBlank(message = "please provide your address")
    private String zipCode;

}
