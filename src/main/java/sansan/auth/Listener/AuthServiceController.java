package sansan.auth.Listener;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sansan.auth.Service.AuthService;
import sansan.utility.lib.DTO.UserDTO;
import sansan.utility.lib.Service.AuthApi;

import javax.annotation.Resource;

@RestController
@RequestMapping("/service-api")
public class AuthServiceController implements AuthApi {
    @Resource
    private AuthService authService;

    @Override
    @PostMapping("/postCreateUser")
    public void postCreateUser(@RequestBody UserDTO userDTO) {
        authService.postCreateUser(userDTO);
    }
}
