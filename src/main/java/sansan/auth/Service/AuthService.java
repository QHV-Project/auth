package sansan.auth.Service;

import sansan.auth.Entity.UserAuth;
import sansan.utility.lib.DTO.AuthDTO;

public interface AuthService {
    AuthDTO generalLogin(UserAuth userAuth);
}
