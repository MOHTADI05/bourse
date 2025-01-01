package tn.esprit.mfb.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.io.Serializable;
import java.util.Set;

@Entity
public class Partner implements Serializable {

  @Getter
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long partnerId;

  @Getter
  @NotBlank(message = "Le nom du partenaire ne peut pas être vide")
  @Size(max = 50, message = "La taille maximale du nom du partenaire est de 50 caractères")
  private String partnerName;
  @Getter
  @NotBlank(message = "Le secteur du partenaire  ne peut pas être vide")
  private String sectorPartner;
  @Getter
  @Email(message = "Adresse email invalide")
  @NotBlank(message = "L'email ne peut pas être vide")
  private String emailPartner;
  @Getter
  @NotNull(message = "L'activité du partnaire ne peut pas être nuls")
  private String activitePartner ;
  @Getter
  @JsonIgnore
  @OneToMany(mappedBy = "partner", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private Set<Product> products;
/*
  @Getter
  @OneToMany(cascade = CascadeType.ALL,mappedBy = "partner")
  private List<ProductRequest> productRequests;
*/

  public void setPartnerId(Long partnerId) {
    this.partnerId = partnerId;
  }

  public void setPartnerName(String partnerName) {
    this.partnerName = partnerName;
  }
  public void setSectorPartner(String sectorPartner) {
    this.sectorPartner = sectorPartner;
  }

  public void setEmailPartner(String emailPartner) {
    this.emailPartner = emailPartner;
  }

  public void setActivitePartner(String activitePartner) {
    this.activitePartner = activitePartner;
  }

  public void setProducts(Set<Product> products) {
    this.products = products;
  }
}
