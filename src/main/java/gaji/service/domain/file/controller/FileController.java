package gaji.service.domain.file.controller;

import gaji.service.domain.file.dto.response.FileCreateResponse;
import gaji.service.domain.file.servie.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "File API", description = "파일 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/files")
public class FileController {
    private final FileService fileService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "파일 생성 API")
    public FileCreateResponse createFile(
            @Valid @Parameter(
                    content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE) )
            @RequestPart("file") MultipartFile file
    ) {
        return fileService.createFile("test", file);
    }

    @DeleteMapping
    @Operation(summary = "파일 삭제 API")
    public void deleteFile(@RequestParam("fileUrl") String fileUrl) {
        fileService.deleteFile(fileUrl);
    }
}
