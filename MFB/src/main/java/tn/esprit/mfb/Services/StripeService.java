package tn.esprit.mfb.Services;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Getter
@Service

public class StripeService {
    @Value("${stripe.api.key}")
    private String apiKey;

    // Vous pouvez également ajouter d'autres méthodes liées à Stripe ici
}
