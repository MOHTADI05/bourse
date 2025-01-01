package tn.esprit.mfb.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.sql.Date;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class CoursDoc {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_doc;

    private int ordre;

    private String description;

    private Long id_cours;

    private String fileName;

    private String fileType;

    private Long fileSize;

    private Date uploadDate;

    @Lob
    @Column(name = "file_data", columnDefinition = "LONGBLOB")
    private byte[] fileData;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "id_cours", referencedColumnName = "id_cours", nullable = false, insertable = false, updatable = false)
    private Cours coursByIdCour ;
}
