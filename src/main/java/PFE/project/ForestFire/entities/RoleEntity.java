package PFE.project.ForestFire.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;


@Entity
@Data
@Table(name="roles")
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRole;

    @Enumerated(EnumType.STRING)
    private RoleName roleName;


    @JsonIgnore
    @OneToMany(mappedBy = "role", cascade =CascadeType.ALL)
    private List<UserEntity> users =new ArrayList<>();


    @Override
    public int hashCode() {
        return Objects.hashCode(roleName);
    }

}
