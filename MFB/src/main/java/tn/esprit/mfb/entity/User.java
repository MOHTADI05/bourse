package tn.esprit.mfb.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "cin")

@Entity
public class User implements Serializable, UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cin;

    @Size(min = 2, max = 30)
    private String nom;

    private String prenom;

    private Integer age;

    @Email
    @Column(unique = true)
    private String email;

    private String password;

    private String adresse;

    private String gender;

    @Min(value = 10000000)
    @Max(value= 99999999)
    private Integer phoneNum;

    @Enumerated(EnumType.STRING)
    private TypeUser role;

    public void setRole(TypeUser role) {
        this.role = role;
    }

    private boolean isbloked;

    private Integer code;

    private String performance;
    private String potentiel;

    private double NetIncome ;

    public void setNetIncome(double netIncome) {
        NetIncome = netIncome;
    }

    public double getNetIncome() {
        return NetIncome;
    }

    @Enumerated(EnumType.STRING)
    private TalentReview classification;


    public void setCredit_authorization(Boolean credit_authorization) {
        Credit_authorization = credit_authorization;
    }


    public Boolean getCredit_authorization() {
        return Credit_authorization;
    }



    private Boolean Credit_authorization;


    @OneToMany(cascade = CascadeType.ALL, mappedBy="client",fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<DemandeCredit> credits;

    public Account getACC() {
        return ACC;
    }

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Account ACC;

    @OneToMany(mappedBy = "userc",cascade = CascadeType.ALL)
    private List<Complaint> complaints;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Notification> notifications;

    @OneToOne
    private Product product;

    @ManyToOne
    @JoinColumn(name = "due_date_id")
    private DueDate dueDate;

    public void setProduct(Product product) {
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }

    public void setClassification(TalentReview classification) {
        this.classification = classification;
    }

    public TalentReview getClassification() {
        return classification;
    }

    public void setPerformance(String performance) {
        this.performance = performance;
    }

    public void setPotentiel(String potentiel) {
        this.potentiel = potentiel;
    }

    public String getPerformance() {
        return performance;
    }

    public String getPotentiel() {
        return potentiel;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer token) {
        this.code = token;
    }

    public void setIsbloked(boolean isbloked) { this.isbloked = isbloked; }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }


    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Long getCin() {
        return cin;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public Integer getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }
    public String getAdresse() {
        return adresse;
    }

    public String getGender() {
        return gender;
    }

    public Integer getPhoneNum() {
        return phoneNum;
    }

    public TypeUser getRole() {
        return role;
    }

    public boolean isIsbloked()  { return isbloked; }


    public void setPassword(String password) {
        this.password = password;
    }



    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }
}
