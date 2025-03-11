package sansan.auth.Client;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sansan.utility.lib.DTO.UserDTO;
import sansan.utility.lib.Service.CustomerFeignClient;

import javax.annotation.Resource;

@RestController
@RequestMapping("/service-api/auth")
public class CustomerClient {

    @Resource
    private CustomerFeignClient customerFeignClient;

    @PostMapping("/login")
    public UserDTO getUserInfoByUsername(String username) {
        UserDTO userDTO = customerFeignClient.getUserInfoByUsername(username);
        return new UserDTO();
    }
}
