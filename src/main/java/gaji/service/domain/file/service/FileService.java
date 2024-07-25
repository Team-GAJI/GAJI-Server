package gaji.service.domain.file.service;

import gaji.service.domain.file.dto.response.FileCreateResponse;
import gaji.service.global.common.enums.FIleCategory;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    FileCreateResponse createFile(FIleCategory fIleCategory , final MultipartFile file);

    void deleteFile(String fileUrl);
}