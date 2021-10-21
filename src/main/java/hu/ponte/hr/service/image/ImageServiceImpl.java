package hu.ponte.hr.service.image;

import hu.ponte.hr.controller.image.dto.ImageMetadataResponseDto;
import hu.ponte.hr.entity.Image;
import hu.ponte.hr.exception.ContentTypeNotSupportedException;
import hu.ponte.hr.mapper.ImageMapper;
import hu.ponte.hr.repository.ImageRepository;
import hu.ponte.hr.service.signer.SignerService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final ImageMapper imageMapper;
    private final SignerService signService;

    @Override
    public List<ImageMetadataResponseDto> getAllMetadata() {
        return imageMapper.imageMetadataModelsToDtos(imageRepository.findAllMetadata());
    }

    @Override
    @SneakyThrows
    public void signAndStore(MultipartFile multipartFile) {
        validateImageContentType(multipartFile.getContentType());
        byte[] signedByteArray = signService.execute(multipartFile.getBytes());
        String signedFileInBase64 = new String(Base64.encodeBase64(signedByteArray), StandardCharsets.UTF_8);
        imageRepository.save(imageMapper.multiPartFileToImageEntity(multipartFile, signedFileInBase64));
    }

    @Override
    public byte[] getByteArrayById(Long id) {
        return findById(id).getContent();
    }

    public Image findById(Long id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Image not found with id: {%d}", id)));
    }

    private void validateImageContentType(String contentType) {
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new ContentTypeNotSupportedException(contentType);
        }
    }

}
