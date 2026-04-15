package PFE.project.ForestFire.repository;

import PFE.project.ForestFire.entities.RoleEntity;
import PFE.project.ForestFire.entities.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import  java.util.List;
@Repository
public interface RoleRepo extends JpaRepository<RoleEntity,Long> {

    List<RoleEntity> findAllByRoleName(RoleName roleName);

    Optional<RoleEntity> findByRoleName(RoleName roleName);
}
