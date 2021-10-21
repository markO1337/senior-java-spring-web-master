package hu.ponte.hr.mapper;

import hu.ponte.hr.controller.image.dto.ImageMetadataResponseDto;
import hu.ponte.hr.entity.Image;
import hu.ponte.hr.service.image.model.ImageMetadata;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ImageMapper {

    List<ImageMetadataResponseDto> imageMetadataModelsToDtos(List<ImageMetadata> imageMetadata);

    ImageMetadataResponseDto imageMetadataModelToDto(ImageMetadata imageMetadata);

    @Mapping(target = "name", source = "file.originalFilename")
    @Mapping(target = "mimeType", source = "file.contentType")
    @Mapping(target = "content", source = "file.bytes")
    @Mapping(target = "digitalSign", source = "base64")
    Image multiPartFileToImageEntity(MultipartFile file, String base64) throws IOException;

}
