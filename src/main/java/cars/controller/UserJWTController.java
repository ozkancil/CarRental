package cars.controller;

import cars.dto.request.RegisterRequest;
import cars.dto.response.CRResponse;
import cars.dto.response.ResponseMessage;
import cars.security.jwt.JwtUtils;
import cars.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserJWTController {

    ////-> create another repository and initialize it with spring initializer
    //or u can use our pom.xml file to create
    //or u can clone nighttime repository and copy it to another folder and change the name
    //make another example of cotact message crud examples with new databse
    // such as library, school, e-devlet

    private UserService userService;

    private AuthenticationManager authenticationManager;

    private JwtUtils jwtUtils;

public UserJWTController( UserService userService,  @Lazy AuthenticationManager authenticationManager, JwtUtils jwtUtils){
    this.userService=userService;
    this.authenticationManager=authenticationManager;
    this.jwtUtils=jwtUtils;
}
    public ResponseEntity<CRResponse> registerUser( @Valid @RequestBody RegisterRequest registerRequest){
        userService.saveUser(registerRequest);
        CRResponse response=new CRResponse();
        response.setMessage(ResponseMessage.REGISTER_RESPONSE_MESSAGE);
        response.setSuccess(true);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
