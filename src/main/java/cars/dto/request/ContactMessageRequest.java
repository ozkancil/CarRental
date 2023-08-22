package cars.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

//@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContactMessageRequest {

    /*
    we have to add the same properties that exist in the db.
    these will be saved into the t_cmessage table.
     */
    @Size(min=1,max=50,message = "your name '${validatedValue}' must be between {min} and {max} chars long")
    @NotBlank(message = "Please provide a Name")
    private String name;
    @Size(min = 5,max = 50,message = "Subject '${validatedValue}' must be between {min} and {max} chars long")
    @NotBlank(message = "Please provide a subject")
    private String subject;
    @Size(min=20,max=200,message = "Message body '${validatedValue}' must be between {min} and {max} chars long")
    @NotBlank(message = "please provide a body")
    private String body;

    @NotBlank(message = "please provide a valid email")
    @Email
    private String email;

}
