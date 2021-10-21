package hu.ponte.hr.controller.image;

import hu.ponte.hr.controller.image.dto.ImageMetadataResponseDto;
import hu.ponte.hr.service.image.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/images")
public class ImagesController {

    private final ImageService imageService;

    @GetMapping("meta")
    public List<ImageMetadataResponseDto> getAllImagesMetadata() {
        return imageService.getAllMetadata();
    }

    @GetMapping("{id}/preview")
    public byte[] getImageById(@PathVariable Long id) {
        return imageService.getByteArrayById(id);
    }

    @PostMapping
    public void signAndStore(MultipartFile file) {
        imageService.signAndStore(file);
    }

}
