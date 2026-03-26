package PFE.project.ForestFire.repository;

import PFE.project.ForestFire.entities.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileRepository extends JpaRepository<FileEntity,Long> {
    Optional<FileEntity> findByFileName(String fileName);
    boolean existsByFileName(String fileName);
}
