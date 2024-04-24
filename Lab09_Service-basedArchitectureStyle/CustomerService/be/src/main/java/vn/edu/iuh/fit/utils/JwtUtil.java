package vn.edu.iuh.fit.utils;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.experimental.NonFinal;
import org.springframework.stereotype.Component;
import vn.edu.iuh.fit.dto.UserCredentialsDTO;
import vn.edu.iuh.fit.exception.ErrorCode;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtUtil {
    @NonFinal
    protected static String SIGNER_KEY = "1TjXchw5FloESb63Kc+DFhTARvpWL4jUGCwfGWxuG5SIf/1y";

    public static String generateToken(UserCredentialsDTO userCredentials) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(userCredentials.getPhone())
                .issuer("devteria.com")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
                ))
                .jwtID(UUID.randomUUID().toString())
//                .claim("scope", buildScope(user))
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            System.err.println("Cannot create token" + e);
            throw new RuntimeException(e);
        }
    }

//    private String buildScope(UserCredentialsDTO userCredentials){
//        StringJoiner stringJoiner = new StringJoiner(" ");
//
//        List.of("ROLE_USER").forEach(role -> {
//            stringJoiner.add(role);
//            if (!CollectionUtils.isEmpty(role.getPermissions()))
//                role.getPermissions()
//                        .forEach(permission -> stringJoiner.add(permission.getName()));
//        });
//
//        return stringJoiner.toString();
//    }


    public static SignedJWT verifyToken(String token) throws Exception {
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier);

        if (!(verified && expiryTime.after(new Date())))
            throw new Exception(String.valueOf(ErrorCode.UNAUTHENTICATED));

        return signedJWT;
    }
}
