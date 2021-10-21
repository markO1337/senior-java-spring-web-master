package hu.ponte.hr.repository;

import hu.ponte.hr.entity.Image;
import hu.ponte.hr.service.image.model.ImageMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    @Query("select new hu.ponte.hr.service.image.model.ImageMetadata(i.id, i.name, i.mimeType, i.size, i.digitalSign)" +
            " from Image i")
    List<ImageMetadata> findAllMetadata();
}
