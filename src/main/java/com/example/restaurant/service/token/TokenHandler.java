package com.example.restaurant.service.token;

import com.example.restaurant.helper.JwtToken;
import com.example.restaurant.service.dto.AccountDto;
import com.example.restaurant.service.securityService.AccountService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Duration;
import java.util.Date;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class TokenHandler {


    private String secretKey;

    private Duration time;


   private JwtBuilder jwtBuilder;
   private JwtParser  jwtParser;

   private AccountService accountService;


   @Autowired
    public TokenHandler(JwtToken jwtToken , AccountService accountService) {

      this.accountService = accountService;

        secretKey = jwtToken.getSecret();
        time = jwtToken.getTime();
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        jwtBuilder = Jwts.builder().signWith(key);
        jwtParser = Jwts.parserBuilder().setSigningKey(key).build();

    }



    public String createToken(AccountDto accountDto) {

       Date issueDate = new Date();
       Date expiratiionDate = Date.from(issueDate.toInstant().plus(time));
       jwtBuilder.setSubject(accountDto.getUsername());
       jwtBuilder.setIssuedAt(issueDate);
       jwtBuilder.setExpiration(expiratiionDate);
        jwtBuilder.claim("roles", accountDto.getRoles().stream()
                .map(roleDto -> roleDto.getCode()).collect(Collectors.toList()));
        return jwtBuilder.compact();

    }

    public AccountDto validateToken(String token) {
        if (jwtParser.isSigned(token)) {
            Claims claims = jwtParser.parseClaimsJws(token).getBody();

            String username = claims.getSubject();
            Date issueDate = claims.getIssuedAt();
            Date expirationDate = claims.getExpiration();

            AccountDto accountDto = accountService.getAccountByUsername(username);

            boolean isValidToken = Objects.nonNull(accountDto)  && expirationDate.after(new Date())&& issueDate.before(expirationDate)
                   ;

            if (!isValidToken) {
                return null;
            }
            return accountDto;
        }
        return null;
    }

}
