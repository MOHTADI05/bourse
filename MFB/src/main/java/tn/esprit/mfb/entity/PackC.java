package tn.esprit.mfb.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@Entity
@Getter
@Setter
public class PackC implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idP;

    @Enumerated(EnumType.STRING)
    private TypePack typePack;

    private String description;

    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "packC", fetch = FetchType.EAGER)
private Set<Credit> credits;

}
