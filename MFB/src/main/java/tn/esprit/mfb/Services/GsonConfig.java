package tn.esprit.mfb.Services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GsonConfig {

    public static void main(String[] args) {
        // Création d'une instance Gson avec une configuration personnalisée
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation() // Exclure les champs sans annotation @Expose
                .create();

        // Utilisation de l'instance Gson pour sérialiser/désérialiser des objets
        // ...
    }
}