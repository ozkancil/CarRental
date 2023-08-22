package cars.security.service;

import cars.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {

    private String email;
    private String password;

    //we add all these to userdetials to say hey when user tries to access our application plz authenticate it with its emial and password. and when it is authentecated
    //please fullfil all the ifnormation about hte rolses to this collection cuz userdetails service implementation will be added JWT and JWT will be added to the CONFIGURATION.
    //WE check hey userDetails are accurate with the JWT
    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
    public static UserDetailsImpl build(User user){
        List<SimpleGrantedAuthority>authorities=user.getRoles().stream().map(role ->
                new SimpleGrantedAuthority(role.getType().name())).collect(Collectors.toList());

        /**
         * What we have done here is that its a simple method where in this class we are getting information from UserDetails interface.
         * We are adding the detials for authentication.
         * Here we are giving simpleGrantedAuthority so it can know.
         */
        return new UserDetailsImpl(user.getEmail(),user.getPassword(),authorities);
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    //methods that return true can be changed according to business needs.

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
