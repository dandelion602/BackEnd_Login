package SecurityTest.SecurityTest.utils;

import SecurityTest.SecurityTest.domain.member.Member;
import SecurityTest.SecurityTest.domain.role.MemberRole;
import io.jsonwebtoken.*;
import lombok.extern.log4j.Log4j2;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Log4j2
public final class TokenUtils {

    private static final String secretKey = "Dandelion602";

    public static String generateJwtToken(Member member) {
        JwtBuilder builder = Jwts.builder()
                .setSubject(member.getId())
                .setHeader(createHeader())
                .setClaims(createClaims(member))
                .setExpiration(createExpireDateForOneHour())
                .signWith(SignatureAlgorithm.HS256, createSigningKey());

        return builder.compact();
    }

    public static boolean isValidToken(String token) {
        try {
            Claims claims = getClaimsFormToken(token);
            log.info("expireTime :" + claims.getExpiration());
            log.info("id :" + claims.get("id"));
            log.info("role :" + claims.get("role"));
            return true;

            // 만료된 토큰 Exception
        } catch (ExpiredJwtException exception) {
            log.error("Token Expired");
            return false;
        } catch (JwtException exception) {
            log.error("Token Tampered");
            return false;
        } catch (NullPointerException exception) {
            log.error("Token is null");
            return false;
        }
    }

    public static String getTokenFromHeader(String header) {
        return header.split(" ")[1];
    }

    private static Date createExpireDateForOneHour() {
        // 토큰 만료시간은 30일으로 설정
        Calendar c = Calendar.getInstance();
        c.add(Calendar.HOUR, 1);
        return c.getTime();
    }

    private static Map<String, Object> createHeader() {
        Map<String, Object> header = new HashMap<>();

        header.put("typ", "JWT");
        header.put("alg", "HS256");

        return header;
    }

    private static Map<String, Object> createClaims(Member member) {
        // 공개 클레임에 사용자의 이름과 아이디를 설정하여 정보를 조회할 수 있다.
        Map<String, Object> claims = new HashMap<>();

        // 일반적으로 주는 값 ( 유효시간 = 발급시간 + 1시간 )
        claims.put("iss", "dandelion602");
        claims.put("exp", System.currentTimeMillis() + 1000*60*60 );  

        // dandelion602 전용 주는 값
        claims.put("id", member.getId());
        claims.put("name", member.getName());
        claims.put("role", member.getRole().getValue());

        return claims;
    }

    private static Key createSigningKey() {
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secretKey);
        return new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());
    }

    private static Claims getClaimsFormToken(String token) {
        return Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(secretKey))
                .parseClaimsJws(token).getBody();
    }

    private static String getUserIdFromToken(String token) {
        Claims claims = getClaimsFormToken(token);
        return (String) claims.get("id");
    }

    private static MemberRole getRoleFromToken(String token) {
        Claims claims = getClaimsFormToken(token);
        return (MemberRole) claims.get("role");
    }
}
