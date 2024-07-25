package gaji.service.domain.file.service;

import gaji.service.aws.s3.AmazonS3Manager;
import gaji.service.domain.file.dto.response.FileCreateResponse;
import gaji.service.global.common.enums.FileCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FileServiceImpl implements FileService {

    private final AmazonS3Manager amazonS3Manager;

    @Transactional
    @Override
    public FileCreateResponse createFile(FileCategory fIleCategory, final MultipartFile file) {
        String imageUrl = amazonS3Manager.uploadFile(fIleCategory.getCategory(), file);
        return new FileCreateResponse(imageUrl);
    }

    @Override
    public void deleteFile(String fileUrl) {
        amazonS3Manager.deleteFile(fileUrl);
    }


}
