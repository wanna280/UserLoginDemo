package com.example.demopro.utils;

import io.jsonwebtoken.*;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {
    public static final long TOKEN_EXPIRE_TIME = 30 * 24 * 60 * 60;  //设置过期时间为30days
    public static final String jwtId = "tokenId";   //tokenId
    private static final String JWT_SECRET = "1234567890";  //JWT的密钥（盐）

    /**
     * 根据声明和失效时间生成jwt
     *
     * @param claims 声明
     * @param time   过期时间
     * @return 生成的token
     */
    public static String CreateJWT(Map<String, Object> claims, Long time) {   //利用claims创建JWT字符串和过期时间
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;  //使用HS256作为加密算法
        Date now = new Date(System.currentTimeMillis());   //获取当前时间
        SecretKey secretKey = GeneralKey();  //生成密钥
        long nowMills = System.currentTimeMillis();   //获取当前时间的Unix时间
        JwtBuilder builder = Jwts.builder()
                .setClaims(claims)   //创建声明，声明是json格式的，使用Map参数传入
                .setId(jwtId)  //设置JWT的ID
                .setIssuedAt(now)  //设置签发时间为当前时间
                .signWith(signatureAlgorithm, secretKey);  //使用HS256对密钥secret进行加密
        if (time >= 0) {
            long expireMillis = nowMills + time;   //过期时间
            Date expire = new Date(expireMillis);  //转换为日期类型
            builder.setExpiration(expire);  //设置jwt的过期时间
        }
        return builder.compact();   //使用compact()方法生成JWT字符串

    }

    /**
     * 利用JWT_SECRET生成密钥
     *
     * @return 密钥
     */
    private static SecretKey GeneralKey() {   //对密钥进行BASE64加密
        String strKey = JWT_SECRET;  //设置JWT的密钥
        byte[] encodedKey = Base64.getDecoder().decode(strKey);  //使用BASE64进行加密
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");  //使用密钥类生成密钥
        return key;
    }

    /**
     * 根据Token和密钥Key得到声明Claims
     *
     * @param token 产生的Token去校验
     * @return Token对应的声明
     */
    public static Claims VerifyJwt(String token) {  //签名密钥，和生成的签名的密钥一模一样
        SecretKey key = GeneralKey();
        Claims claims;
        try {
            //如果能够解析出来才会过去，如果不能解析出来，得到的声明是null
            claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
        } catch (Exception ex) {
            claims = null;
            //throw ex;
        }
        return claims;
    }

    /**
     * 根据OpenId和userId生成Token
     *
     * @param openId
     * @param userId
     * @return 创建的JWT
     */
    public static String GenerateToken(String openId, Integer userId) { // 根据userID和openID生成token
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("openId", openId);
        map.put("sub", openId);
        return CreateJWT(map, TOKEN_EXPIRE_TIME);
    }

    /**
     * 根据声明创建JWT
     * @param claims
     * @return
     */

    public static String GenerateToken(Map<String,Object> claims) {
        return CreateJWT(claims,TOKEN_EXPIRE_TIME);
    }


}
