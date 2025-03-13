package sansan.auth.Controller;

import org.springframework.web.bind.annotation.*;
import sansan.auth.Service.AuthService;
import sansan.auth.Entity.UserAuth;
import sansan.utility.lib.DTO.AuthDTO;

import javax.annotation.Resource;

@RestController
@RequestMapping("/client-api")
public class AuthController {
    @Resource
    private AuthService authService;

    @PostMapping("/login")
    public AuthDTO login(@RequestBody AuthDTO authDTO) {
        return authService.generalLogin(authDTO);
    }
}