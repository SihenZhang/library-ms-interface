package com.sihenzhang.library.system.security.token;

import cn.hutool.crypto.asymmetric.RSA;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.sihenzhang.library.system.user.entity.SysUser;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;

public class TokenUtil {

    private static final RSA rsa = new RSA();
    private static final String ISSUER = "sihenzhang";
    private static final long EXPIRATION = 24 * 60 * 60 * 1000;

    public static String sign(SysUser user) {
        try {
            Date expirationDate = new Date(System.currentTimeMillis() + EXPIRATION);
            String token = JWT.create()
                    .withIssuer(ISSUER)
                    .withClaim("username", user.getUsername())
                    .withExpiresAt(expirationDate)
                    .sign(Algorithm.RSA256((RSAPublicKey) rsa.getPublicKey(), (RSAPrivateKey) rsa.getPrivateKey()));
            return String.format("Bearer %s", token);
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean verify(String token) {
        token = token.substring(7);
        try {
            JWTVerifier verifier = JWT.require(Algorithm.RSA256((RSAPublicKey) rsa.getPublicKey(), (RSAPrivateKey) rsa.getPrivateKey()))
                    .withIssuer(ISSUER)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
