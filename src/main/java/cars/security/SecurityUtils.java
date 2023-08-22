package cars.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;


/*
utils for security fucntion that can be used in service layer.
class to implement.
 */
public class SecurityUtils {

    /*
    private constructor not to initialize any SecurityUtils Object,
    since the method that will be called getCurrentUserLogin() is static.
     */
    private SecurityUtils(){

    }

    /*
    helper method to fetch user information from SECURITY CONTEXT.
     */


    //we are reaching security container
    public static Optional<String> getCurrentUserLogin(){
        SecurityContext securityContext=SecurityContextHolder.getContext();
        Authentication authentication=securityContext.getAuthentication();
        return Optional.ofNullable(extractPrinciple(authentication));

    }

    /*
    authentication.getPrinciple() -> the identity of the principle being authenticated.
    in case of an authentication request with username  and password, this would be the username.
     */
    private static String extractPrinciple(Authentication authentication){
        if(authentication==null){
            return null;
        }else if(authentication.getPrincipal() instanceof UserDetails){
            UserDetails securedUser= (UserDetails) authentication.getPrincipal();
            //actually this is an email in our app
            securedUser.getUsername();
        }else if (authentication.getPrincipal() instanceof  String){
            return (String) authentication.getPrincipal();
        }
        return null;
    }
}
