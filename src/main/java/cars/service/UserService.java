package cars.service;

import cars.domain.Role;
import cars.domain.User;
import cars.domain.enums.RoleType;
import cars.dto.request.RegisterRequest;
import cars.exception.ConflictException;
import cars.exception.ResourceNotFoundException;
import cars.exception.message.ErrorMessage;
import cars.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
//@RequiredArgsConstructor
public class UserService {

    //@Autowired
    private final UserRepository userRepository;
    //@Autowired

    private final RoleService roleService;
    //@Autowired

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,RoleService roleService, @Lazy PasswordEncoder passwordEncoder){
        this.userRepository= userRepository;
        this.roleService= roleService;
        this.passwordEncoder= passwordEncoder;
    }

    public User getUserByEmail(String email){
        return userRepository.findByEmail(email).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessage.USER_NOT_FOUND_MESSAGE,email)));

    }

    public void saveUser(RegisterRequest registerRequest){
        if(userRepository.existsByEmail(registerRequest.getEmail())){
            throw new ConflictException(String.format(ErrorMessage.EMAIL_ALREADY_EXIST_MESSAGE,registerRequest.getEmail()));
        }
        Role role= roleService.findByType(RoleType.ROLE_CUSTOMER);
        Set<Role>roles=new HashSet<>();
        roles.add(role);

        //in database encoded format of the password should be saved
        String encodePassword=passwordEncoder.encode(registerRequest.getPassword());

        User user=new User();
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(encodePassword);
        user.setPhoneNumber(registerRequest.getPhoneNumber());
        user.setAddress(registerRequest.getAddress());
        user.setZipCode(registerRequest.getZipCode());
        user.setRoles(roles);
        userRepository.save(user);

    }
}
