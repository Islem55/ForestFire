package PFE.project.ForestFire.repository;

import PFE.project.ForestFire.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepo extends JpaRepository<UserEntity, Long> {

    // -------- NAMED METHODS --------

    boolean existsByNom(String nom);

    boolean existsByEmail(String email);

    Optional<UserEntity> findByNom(String nom);

    Optional<UserEntity> findByEmail(String email);


    // -------- JPQL --------

    @Query("SELECT u FROM UserEntity u WHERE u.nom = :nom")
    Optional<UserEntity> findByUsernameJPQL(@Param("nom") String nom);

    @Query("SELECT CASE WHEN COUNT(u)>0 THEN true ELSE false END FROM UserEntity u WHERE u.nom = :nom")
    boolean existsByUsernameJPQL(@Param("nom") String nom);



}