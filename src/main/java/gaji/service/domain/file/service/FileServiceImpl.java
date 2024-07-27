package gaji.service.domain.file.service;

import gaji.service.aws.s3.AmazonS3Manager;
import gaji.service.domain.file.domain.Files;
import gaji.service.domain.file.dto.response.FileCreateResponse;
import gaji.service.domain.file.mapper.FilesMapper;
import gaji.service.domain.file.repository.FileRepository;
import gaji.service.global.common.enums.FileCategory;
import gaji.service.global.common.exception.RestApiException;
import gaji.service.global.common.exception.code.status.FilesErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FileServiceImpl implements FileService {

    private final AmazonS3Manager amazonS3Manager;
    private final FileRepository filesRepository;
    private final FilesMapper filesMapper;

    @Override
    @Transactional
    public FileCreateResponse createFile(FileCategory fIleCategory, final MultipartFile file) {
        String imageUrl = amazonS3Manager.uploadFile(fIleCategory.getCategory(), file);
        // 파일 정보 추출 및 엔티티 생성
        Files filesEntity = filesMapper.toEntity(file, imageUrl);

        // 파일 정보 저장
        filesRepository.save(filesEntity);

        return new FileCreateResponse(imageUrl);
    }

    @Override
    @Transactional
    public void deleteFile(String fileUrl) {
        Files files = filesRepository.findByPath(fileUrl);
        if (files == null) {
            throw new RestApiException(FilesErrorStatus._NOT_FOUND_FILE_URL);
        }
        filesRepository.delete(files);
        amazonS3Manager.deleteFile(fileUrl);
    }
}
