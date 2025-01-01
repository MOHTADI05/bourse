package tn.esprit.mfb.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
@Service
@AllArgsConstructor
public class JwtService {

    private static final String SECRET_KEY="9E9YcOHovjs3QvOeyGLW7411eUo8d0KPV085ASgf0jZlobwOIj8jPz4kMekmz3ttYvTif6hNkY4kbscloVfXHBDcPN28DWeQzaAA3asVTnkuDy+JymB+/Wt9ilNCTEr8q6555HEYHaSuOvh+m8WxyZx6XG+hwh01Ul1P1cgIp4CNfCqL4Oiwtcex1R4UIzI2WZ8QcCUMZiOEhsa+CDG+YDO0xBlFygnRu6ktsfCNVUVAsaRSC8afzm6lrPdYLBqPrFLaMp3X+XUkJA3E3opjko3a0MKUOOmGy2sdJOPT/rDTldSti87ykwZIcZG7ARlek64WtUuklipBV9lMWekFlX9gDHYW4Qisnm3z3z/FRQE=";
    private final JwtBlacklistService jwtBlacklistService;
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    public void printTokenClaims(String token) {
        Claims claims = extractAllClaims(token);
        System.out.println("Revendications du token : " + claims);
    }


    public String extractUserRole(String token) {
        printTokenClaims(token);

        //Claims claims = extractAllClaims(token);
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails) {

        return generateToken(new HashMap<>(), userDetails);
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ) {
        return Jwts
            .builder()
            .setClaims(extraClaims)
            .setSubject(userDetails.getUsername())
            .setIssuedAt(new Date(System.currentTimeMillis()))
                //.setExpiration(new Date(System.currentTimeMillis() + 1000*60)) // 1 minute
            .setExpiration(new Date(System.currentTimeMillis() + 1000*60*24)) //24 heure
            .signWith(getSignInKey(), SignatureAlgorithm.HS256)
            .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {

        return extractExpiration(token).before(new Date());
    }

    private Claims extractAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

        private Key getSignInKey() {
            byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
            return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generatePasswordResetToken(UserDetails userDetails) {

        return generateToken(new HashMap<>(), userDetails);
    }

    public String generatePasswordResetToken(  Map<String, Object> extraClaims,
                                               UserDetails userDetails) {
        // Configuration des revendications du token


        // Génération du token avec les revendications
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                //.setExpiration(new Date(System.currentTimeMillis() + 1000*60)) // 1 minute
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*24)) //24 heure
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();}


    // Méthode pour valider si deux tokens sont égaux
    public boolean areTokensEqual(String token1, String token2) {
        System.out.println(token1);
        System.out.println(token2);
        boolean b=token1.equals(token2);
        System.out.println(b);
        return b ;


    }

//    public boolean isResetTokenValid(String token) {
//        System.out.println("dkhalet fel fonction isresttokenvalide JWTSERVICE");
//
//        System.out.println(token);
//
//        try {
//            // Vérifiez la signature du token
//            Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes())).build().parseClaimsJws(token);
//            System.out.println(token);
//            return true;
//        } catch (Exception e) {
//            System.out.println("ghalet");
//            // La signature est invalide ou le token a expiré
//            return false;
//        }
//    }

    // Méthode pour récupérer les revendications (claims) du token
    public Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes())).build().parseClaimsJws(token).getBody();
    }
}
