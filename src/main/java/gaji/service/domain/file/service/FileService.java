package gaji.service.domain.file.service;

import gaji.service.domain.file.dto.response.FileCreateResponse;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    FileCreateResponse createFile(String domain, final MultipartFile file);

    void deleteFile(String fileUrl);
}