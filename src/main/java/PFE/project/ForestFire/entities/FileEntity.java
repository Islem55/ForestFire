package PFE.project.ForestFire.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "files")
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;

    private String contentType;

    private Long size;

    @Lob
    private byte[] data;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

   }