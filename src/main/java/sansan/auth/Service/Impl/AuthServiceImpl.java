package sansan.auth.Service.Impl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import sansan.auth.Entity.Repository.RequestEntityRepository;
import sansan.auth.Entity.Repository.UserAuthRepository;
import sansan.auth.Entity.RequestEntity;
import sansan.auth.Entity.UserAuth;
import sansan.auth.Mapper.RequestMapper;
import sansan.auth.Mapper.UserAuthMapper;
import sansan.auth.Service.AuthService;
import sansan.utility.lib.Config.TokenProvider;
import sansan.utility.lib.DTO.AuthDTO;
import sansan.utility.lib.DTO.LoginDTO;
import sansan.utility.lib.DTO.UserDTO;
import sansan.utility.lib.Enum.RequestStatus;
import sansan.utility.lib.Enum.RequestType;
import sansan.utility.lib.Enum.UserStatus;
import sansan.utility.lib.ErrorCode;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthServiceImpl.class);

    @Resource
    private TokenProvider tokenProvider;
    @Resource
    private UserAuthRepository userAuthRepository;

    @Resource
    private RequestEntityRepository requestEntityRepository;

//    @Resource
//    @Qualifier("jdbcCustomer")
//    private JdbcTemplate jdbcCustomer;

    @Resource
    private UserAuthMapper userAuthMapper = new UserAuthMapper();
    @Resource
    private RequestMapper requestMapper = new RequestMapper();

    @Override
    public LoginDTO generalLogin(AuthDTO authDTO) {
        LOGGER.info("[generalLogin] login user: {}", authDTO.getUsername());
//        verifyUser(authDTO.getUsername());
        UserAuth userAuthDB = userAuthRepository.findByUsername(authDTO.getUsername());
        if (ObjectUtils.isEmpty(userAuthDB)
                || StringUtils.isBlank(userAuthDB.getStatus())) {
            throw new RuntimeException(ErrorCode.USER_NOT_FOUND);
        }
        LoginDTO loginDTO = new LoginDTO();
        if (userAuthDB.getStatus().equals(UserStatus.LOCKED.name())) {
            loginDTO.setStatus(userAuthDB.getStatus());
            return loginDTO;
        }
        if (!userAuthDB.getPassword().equals(authDTO.getPassword())) {
            throw new RuntimeException(ErrorCode.PASSWORD_INCORRECT);
        }
        if (userAuthDB.getStatus().equals(UserStatus.CREATE.name())) {
            RequestEntity requestEntity = requestMapper.createDefaultRequest();
            requestEntity.setUsername(userAuthDB.getUsername());
            requestEntity.setRequestType(RequestType.RESET_PASSWORD.name());
            requestEntity = requestEntityRepository.save(requestEntity);
            LOGGER.info("[generalLogin] login user create requestId: {}", requestEntity.getRequestId());

            loginDTO.setRequestId(requestEntity.getRequestId());
        }
        String token = tokenProvider.generateToken(authDTO.getUsername());
        LOGGER.info("[generalLogin] generateToken success");
        loginDTO.setToken(token);
        loginDTO.setStatus(userAuthDB.getStatus());
        return loginDTO;
    }

    @Override
    public void postCreateUser(UserDTO userDTO) {
        LOGGER.info("[postCreateUser] postCreateUser begin user: {}", userDTO.getUsername());
        UserAuth userAuth = userAuthMapper.mapperCreateUser(userDTO);
        userAuthRepository.save(userAuth);
    }

    @Override
    public LoginDTO postResetPassword(AuthDTO authDTO) {
        LOGGER.info("[postResetPassword] postResetPassword begin user: {} and request: {}", authDTO.getUsername(), authDTO.getRequestId());
        //validate pass
        UserAuth userAuthDB = userAuthRepository.findByUsername(authDTO.getUsername());
        RequestEntity requestEntity = requestEntityRepository.findByRequestIdAndUsernameAndStatus(authDTO.getRequestId(), authDTO.getUsername(), RequestStatus.INIT.name());
        try {
//            verifyUser(authDTO.getUsername());
            if (ObjectUtils.isEmpty(requestEntity)) {
                LOGGER.error("[postCreateUser] request Id and user not found");
                throw new RuntimeException(ErrorCode.INVALID_REQUEST);
            }
            if (ObjectUtils.isEmpty(userAuthDB)
                    || StringUtils.isBlank(userAuthDB.getStatus())) {
                throw new RuntimeException(ErrorCode.USER_NOT_FOUND);
            }
            LoginDTO loginDTO = new LoginDTO();
            if (userAuthDB.getStatus().equals(UserStatus.LOCKED.name())) {
                loginDTO.setStatus(userAuthDB.getStatus());
                return loginDTO;
            }
            if (!userAuthDB.getPassword().equals(authDTO.getPassword())) {
                throw new RuntimeException(ErrorCode.PASSWORD_INCORRECT);
            }
            userAuthDB.setPassword(authDTO.getPasswordNew());
            requestEntity.setStatus(RequestStatus.SUCCESS.name());

            return loginDTO;
        } catch (Exception e) {
            LOGGER.error("[postCreateUser] error process: {}",e.getMessage());
            requestEntity.setStatus(RequestStatus.FAILED.name());
            throw new RuntimeException(e.getMessage());
        } finally {
            userAuthRepository.save(userAuthDB);
            requestEntityRepository.save(requestEntity);
            tokenProvider.clearToken();
        }
    }

//    private void verifyUser(String username) {
//        List<String> statusUsers = List.of(UserStatus.ACTIVE.name());
//        List<Object> params = new ArrayList<>();
//        params.add(username);
//        params.addAll(statusUsers);
//        String sql = "SELECT u.id FROM users u WHERE username = ? and status IN (" + statusUsers.stream().map(s -> "?").collect(Collectors.joining(",")) + ")";
//        try {
//            String usersId = jdbcCustomer.queryForObject(sql, new Object[]{username, statusUsers}, String.class);
//            if (ObjectUtils.isEmpty(usersId)) {
//                throw new RuntimeException(ErrorCode.USER_NOT_FOUND);
//            }
//        } catch (Exception e) {
//            throw new RuntimeException(ErrorCode.USER_NOT_FOUND);
//        }
//    }
}
