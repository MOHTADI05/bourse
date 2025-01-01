package tn.esprit.mfb.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "productId")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Product implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @NotBlank(message = "Le nom du produit ne peut pas être vide")
    @Size(max = 255, message = "La taille maximale du nom du produit est de 255 caractères")
    @Column(name = "product_name")
    private String productName;
    @NotBlank(message = "La description du produit ne peut pas être vide")
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    @NotNull(message = "La valeur du produit ne peut pas être nul")
    @Positive(message = "La valeur du produit doit être positif")
    private float valueProduct;
    @NotNull(message = "La valeur d'echange du produit ne peut pas être nulle")
    private Float valueEXC;
    private String isAvailable;
    @ManyToOne
    private Partner partner;
    private  String filename;

    @NotNull(message = "Le type de produit ne peut pas être nul")
    @Enumerated(EnumType.STRING)
    private ProductType productType;

    @OneToOne(mappedBy = "product")
    private User productOwner;

    public Product() {
    }
    public Product(Partner partner) {
        this.partner = partner;
    }

    public void setValueProduct(float valueProduct) {
        this.valueProduct = valueProduct;
    }

    public void setValueEXC(Float valueEXC) {
        this.valueEXC = valueEXC;
    }
    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public void setPartner(Partner partner) {
        this.partner = partner;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }


}
