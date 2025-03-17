package sansan.auth.Service;

import sansan.auth.Entity.UserAuth;
import sansan.utility.lib.DTO.AuthDTO;
import sansan.utility.lib.DTO.LoginDTO;
import sansan.utility.lib.DTO.UserDTO;

public interface AuthService {
    LoginDTO generalLogin(AuthDTO authDTO);

    void postCreateUser(UserDTO userDTO);

    LoginDTO postResetPassword(AuthDTO authDTO);
}
