package cars.service;

import cars.domain.Role;
import cars.domain.enums.RoleType;
import cars.exception.ResourceNotFoundException;
import cars.exception.message.ErrorMessage;
import cars.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    RoleRepository roleRepository;

    public Role findByType(RoleType roleType){
        return roleRepository.findByType(roleType).orElseThrow(()->
                new ResourceNotFoundException(String.format(ErrorMessage.ROLE_NOT_FOUND_MESSAGE,roleType.getName())));
    }

}
