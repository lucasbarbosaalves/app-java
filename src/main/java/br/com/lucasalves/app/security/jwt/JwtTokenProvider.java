package br.com.lucasalves.app.security.jwt;

import br.com.lucasalves.app.data.vo.security.TokenVO;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.auth0.jwt.algorithms.Algorithm;

import java.util.Base64;
import java.util.Date;
import java.util.List;

@Service
public class JwtTokenProvider {

    @Value("${security.jwt.token.secret-key:secret}")
    private String secretKey = "secret";

    @Value("${security.jwt.token.expire-length:3600000}")
    private long validityInMilliseconds = 3600000;

    private UserDetailsService userDetailsService;

    public JwtTokenProvider() {
    }

    public JwtTokenProvider(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
    Algorithm algorithm = null;

    @PostConstruct // Inicializa o objeto após a injeção de dependência
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        algorithm = Algorithm.HMAC256(secretKey.getBytes());
    }

    public TokenVO createAccessToken(String username, List<String> roles) {

        Date now = new Date(); // Momento atual
        Date validity = new Date(now.getTime() + validityInMilliseconds); // Momento daqui uma hora
        var accessToken = getAccessToken(username, now, validity, roles);
        var refreshToken = getAccessToken(username, roles, now);
        return new TokenVO(username, true, now, validity, accessToken.toString(), refreshToken.toString());
    }

    private String getAccessToken(String username, List<String> roles, Date now) {
        return null;
    }

    private String getAccessToken(String username, Date now, Date validity, List<String> roles) {
        return null;
    }

}
