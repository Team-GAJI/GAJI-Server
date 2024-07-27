package gaji.service.domain.file.controller;

import gaji.service.domain.file.dto.response.FileCreateResponse;
import gaji.service.domain.file.service.FileService;
import gaji.service.global.common.base.BaseResponse;
import gaji.service.global.common.enums.FileCategory;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

    @PostMapping(value = "/{fileCategory}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "파일 생성 API")
    public BaseResponse<FileCreateResponse> createFile (@RequestPart("file") MultipartFile file, @PathVariable("fileCategory") FileCategory fileCategory )
    {
        return BaseResponse.onSuccess(fileService.createFile(fileCategory, file));
    }

    @DeleteMapping
    @Operation(summary = "파일 삭제 API")
    public BaseResponse<String> deleteFile(@RequestParam("fileUrl") String fileUrl) {
        fileService.deleteFile(fileUrl);
        return BaseResponse.onSuccess("삭제 성공");
    }
}
