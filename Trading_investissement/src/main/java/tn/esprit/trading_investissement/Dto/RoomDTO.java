package tn.esprit.trading_investissement.Dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL) // Inclut uniquement les champs non nuls dans la réponse JSON
public class RoomDTO {
    private Long id;
    private String name;
    private String creatorName;

    // Constructeur
    public RoomDTO(Long id, String name, String creatorName) {
        this.id = id;
        this.name = name;
        this.creatorName = creatorName;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCreatorName() {
        return creatorName;
    }

    // Si nécessaire, ajoutez des setters
}

