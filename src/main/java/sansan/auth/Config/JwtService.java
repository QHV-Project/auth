package sansan.auth.Config;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import sansan.utility.lib.Enum.UserStatus;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JwtService {
    @JsonIgnore
    private static final String SECRET_KEY = "eGlhbmdxdWFuZzk2OTk=";
    @Qualifier("jdbcCustomer")
    private JdbcTemplate jdbcCustomer;
    @Value("${sansan.time-session}")
    private int timeSession;

    public String generateToken(String username) {
        verifyUser(username);
        try {
            JWSSigner signer = new MACSigner(SECRET_KEY.getBytes());

            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder().subject(username).issueTime(new Date()).expirationTime(new Date(System.currentTimeMillis() + timeSession)) // 1 giờ
                    .build();

            SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
            signedJWT.sign(signer);

            return signedJWT.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException("Lỗi khi tạo JWT", e);
        }
    }

    private void verifyUser(String username) {
        List<String> statusUsers = List.of(UserStatus.ACTIVE.name());
        List<Object> params = new ArrayList<>();
        params.add(username);
        params.addAll(statusUsers);
        String sql = "SELECT u.id FROM users u WHERE username = ? and status IN (" + statusUsers.stream().map(s -> "?").collect(Collectors.joining(",")) + ")";
        String usersId = jdbcCustomer.queryForObject(sql, new Object[]{username, statusUsers}, String.class);
        if (ObjectUtils.isEmpty(usersId)) {
            throw new RuntimeException("Người dùng không tồn tại");
        }
    }

    public boolean validateToken(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            JWSVerifier verifier = new MACVerifier(SECRET_KEY.getBytes());

            return signedJWT.verify(verifier) && new Date().before(signedJWT.getJWTClaimsSet().getExpirationTime());
        } catch (JOSEException | ParseException e) {
            return false;
        }
    }

    public String extractUsername(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            return signedJWT.getJWTClaimsSet().getSubject();
        } catch (ParseException e) {
            throw new RuntimeException("Lỗi khi đọc JWT", e);
        }
    }
}
