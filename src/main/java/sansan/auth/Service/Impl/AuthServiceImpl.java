package sansan.auth.Service.Impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import sansan.auth.Config.TokenProvider;
import sansan.auth.Entity.Repository.UserAuthRepository;
import sansan.auth.Entity.UserAuth;
import sansan.auth.Service.AuthService;
import sansan.utility.lib.DTO.AuthDTO;
import sansan.utility.lib.Enum.UserStatus;
import sansan.utility.lib.ErrorCode;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {
    @Resource
    private TokenProvider jwtTokenProvider;
    @Resource
    private UserAuthRepository userAuthRepository;

    @Resource
    @Qualifier("jdbcCustomer")
    private JdbcTemplate jdbcCustomer;

    @Override
    public AuthDTO generalLogin(AuthDTO authDTO) {
        verifyUser(authDTO.getUsername());
        UserAuth userAuthDB = userAuthRepository.findByUsername(authDTO.getUsername());
        if (ObjectUtils.isEmpty(userAuthDB)) {
            throw new RuntimeException(ErrorCode.USER_NOT_FOUND);
        }
        if (!userAuthDB.getPassword().equals(authDTO.getPassword())) {
            throw new RuntimeException(ErrorCode.PASSWORD_INCORRECT);
        }
        String token = jwtTokenProvider.generateToken(authDTO.getUsername());
        authDTO.setToken(token);
        return authDTO;
    }

    private void verifyUser(String username) {
        List<String> statusUsers = List.of(UserStatus.ACTIVE.name());
        List<Object> params = new ArrayList<>();
        params.add(username);
        params.addAll(statusUsers);
        String sql = "SELECT u.id FROM users u WHERE username = ? and status IN (" + statusUsers.stream().map(s -> "?").collect(Collectors.joining(",")) + ")";
        try {
            String usersId = jdbcCustomer.queryForObject(sql, new Object[]{username, statusUsers}, String.class);
            if (ObjectUtils.isEmpty(usersId)) {
                throw new RuntimeException(ErrorCode.USER_NOT_FOUND);
            }
        } catch (Exception e) {
            throw new RuntimeException(ErrorCode.USER_NOT_FOUND);
        }
    }
}
