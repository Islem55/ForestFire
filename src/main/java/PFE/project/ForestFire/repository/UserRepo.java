package PFE.project.ForestFire.repository;

import PFE.project.ForestFire.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, Long> {

    /* NAMED METHOD */
    boolean existsByNom(String nom);

    boolean existsByEmail(String email);

    List<UserEntity> findByNom(String nom);

    UserEntity findByEmail(String email);

    /* JPQL METHOD */
    @Query("SELECT u FROM UserEntity u WHERE u.nom = :nom")
    UserEntity findByUsernameJPQL(@Param("nom") String nom);

    @Query("SELECT CASE WHEN COUNT(u)>0 THEN true ELSE false END FROM UserEntity u WHERE u.nom = :nom")
    boolean existsByUsernameJPQL(@Param("nom") String nom);

    /* SQL METHOD */
    @Query(value="SELECT CASE WHEN COUNT(*)>0 THEN true ELSE false END FROM users WHERE nom = :nom", nativeQuery=true)
    boolean existsByUsernameSQL(@Param("nom") String nom);
}
