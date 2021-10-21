package hu.ponte.hr.service.image;

import hu.ponte.hr.controller.image.dto.ImageMetadataResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {

    List<ImageMetadataResponseDto> getAllMetadata();

    void signAndStore(MultipartFile file);

    byte[] getByteArrayById(Long id);

}
