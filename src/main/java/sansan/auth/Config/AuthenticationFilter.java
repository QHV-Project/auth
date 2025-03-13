package sansan.auth.Config;

import com.nimbusds.jwt.SignedJWT;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import sansan.utility.lib.DTO.UserDTO;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;

public class AuthenticationFilter extends OncePerRequestFilter {
    private final TokenProvider jwtTokenProvider;

    public AuthenticationFilter(TokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            jwtTokenProvider.validateToken(token);
            token = token.substring(7);
            try {
                SignedJWT signedJWT = SignedJWT.parse(token);
                UserDTO userDTO = new UserDTO();
                userDTO.setUsername(signedJWT.getJWTClaimsSet().getSubject());

                // Xác thực user
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDTO, null, null);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (ParseException e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}



