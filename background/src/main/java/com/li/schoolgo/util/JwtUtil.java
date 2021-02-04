package com.li.schoolgo.util;

import io.jsonwebtoken.*;

import java.util.Map;

public class JwtUtil {
    public static String encode(String key,Map<String,Object> param){

        JwtBuilder jwtBuilder = Jwts.builder().signWith(SignatureAlgorithm.HS256,key);

        jwtBuilder = jwtBuilder.setClaims(param);

        String token = jwtBuilder.compact();
        return token;

    }


    public  static Map<String,Object> decode(String token , String key){

        Claims claims=null;

        try {
            claims= Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
        } catch ( JwtException e) {
            return null;
        }
        return  claims;
    }

}
