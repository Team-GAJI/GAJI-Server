package gaji.service.domain.file.servie;

import gaji.service.aws.s3.AmazonS3Manager;
import gaji.service.domain.file.dto.response.FileCreateResponse;
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
    public FileCreateResponse createFile(String domain , final MultipartFile file) {
        String imageUrl = amazonS3Manager.uploadFile(domain, file);
        return new FileCreateResponse(imageUrl);
    }

    @Override
    public void deleteFile(String fileUrl) {
        amazonS3Manager.deleteFile(fileUrl);
    }


}
