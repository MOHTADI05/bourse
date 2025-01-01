package tn.esprit.mfb.config;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class JwtBlacklistService {
    private Set<String> blacklistedTokens = new HashSet<>();


    public void blacklistCode(Integer code) {
        blacklistedTokens.add(String.valueOf(code));
    }
    public void blacklistToken(String token) {
        blacklistedTokens.add(token);
    }

    public boolean isTokenBlacklisted(String token) {
        return blacklistedTokens.contains(token);
    }
    public Set<String> getBlacklistedTokens() {
        return blacklistedTokens;
    }
}
