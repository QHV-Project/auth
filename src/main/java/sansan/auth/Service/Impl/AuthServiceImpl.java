package sansan.auth.Service.Impl;

import org.springframework.stereotype.Service;
import sansan.auth.Entity.UserAuth;
import sansan.auth.Service.AuthService;
//import sansan.auth.Config.JwtService;
import sansan.utility.lib.DTO.AuthDTO;

import javax.annotation.Resource;

@Service
public class AuthServiceImpl implements AuthService {
//    @Resource
//    private JwtService jwtService;

    @Override
    public AuthDTO generalLogin(UserAuth userAuth) {


        return null;
    }
}
