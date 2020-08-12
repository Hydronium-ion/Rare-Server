package com.codesquad.rare.config;

import com.codesquad.rare.domain.account.Account;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class JwtService {

  public static final Integer EXPIRE_TIME = 1000 * 60 * 500;
  private String secretKey = "ThisIsRareServiceSecretKey";
  private byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secretKey);
  private SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
  private final Key KEY = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

  public String makeJwt(Account account) throws Exception {
    Date expireTime = new Date();
    expireTime.setTime(expireTime.getTime() + EXPIRE_TIME);

    Map<String, Object> headerMap = new HashMap<>();

    headerMap.put("typ", "JWT");
    headerMap.put("alg", "HS256");

    Map<String, Object> map = new HashMap<>();

//    map.put("id", account.getId().toString());
    map.put("name", account.getName());
    map.put("email", account.getEmail());
    map.put("avatarUrl", account.getAvatarUrl());

    JwtBuilder builder = Jwts.builder().setHeader(headerMap)
        .setClaims(map)
        .setExpiration(expireTime)
        .signWith(signatureAlgorithm, KEY);

    return builder.compact();
  }

  public boolean checkJwt(String jwt) throws Exception {
    try {
      Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(secretKey))
          .parseClaimsJws(jwt).getBody(); // 정상 수행된다면 해당 토큰은 정상토큰

      log.info("expireTime :" + claims.getExpiration());
      log.info("id :" + claims.get("id"));
      log.info("name :" + claims.get("name"));
      log.info("avatarUrl :" + claims.get("avatarUrl"));

      return true;
    } catch (ExpiredJwtException exception) {
      log.info("토큰 만료");
      return false;
    } catch (JwtException exception) {
      log.info("토큰 변조");
      return false;
    }
  }

  public Object getUserName(String jwt) throws RuntimeException {
    try {
      Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(secretKey))
          .parseClaimsJws(jwt).getBody();
      return claims.get("name");
    } catch (Exception e) {
      return null;
    }
  }
}
