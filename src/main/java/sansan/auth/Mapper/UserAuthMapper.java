package sansan.auth.Mapper;

import org.springframework.stereotype.Component;
import sansan.auth.AuthUtils.AuthUtils;
import sansan.auth.Entity.UserAuth;
import sansan.utility.lib.DTO.UserDTO;
import sansan.utility.lib.Enum.UserStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class UserAuthMapper {
    public UserAuth mapperCreateUser(UserDTO userDTO){
        UserAuth userAuth=new UserAuth();
        userAuth.setId(UUID.randomUUID().toString());
        userAuth.setUsername(userDTO.getUsername());
        userAuth.setStatus(UserStatus.CREATE.name());
        userAuth.setCreateTime(LocalDateTime.now());
        userAuth.setUpdateTime(LocalDateTime.now());
        userAuth.setPassword(AuthUtils.generatePassword());
        return userAuth;
    }
}
