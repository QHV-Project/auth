package sansan.auth.Controller;

import org.springframework.web.bind.annotation.*;
import sansan.auth.Service.AuthService;
import sansan.auth.Entity.UserAuth;
import sansan.utility.lib.DTO.AuthDTO;
import sansan.utility.lib.DTO.LoginDTO;

import javax.annotation.Resource;

@RestController
@RequestMapping("/client-api")
public class AuthController {
    @Resource
    private AuthService authService;

    @PostMapping("/login")
    public LoginDTO login(@RequestBody AuthDTO authDTO) {
        return authService.generalLogin(authDTO);
    }

    @PostMapping("/reset-pass")
    public LoginDTO postResetPassword(@RequestBody AuthDTO authDTO) {
        return authService.postResetPassword(authDTO);
    }
}