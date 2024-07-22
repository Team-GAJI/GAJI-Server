package gaji.service.aws.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import gaji.service.config.AmazonConfig;
import gaji.service.global.exception.RestApiException;
import gaji.service.global.exception.code.status.GlobalErrorStatus;
import gaji.service.global.util.uuid.Uuid;
import gaji.service.global.util.uuid.UuidRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class AmazonS3Manager{

    private final AmazonS3 amazonS3;

    private final AmazonConfig amazonConfig;

    private final UuidRepository uuidRepository;

    public String uploadFile(String keyName, MultipartFile file) {
        // generateKeyName() 메소드로 keyName 받아오기
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());

        InputStream stream;
        try{
            stream = file.getInputStream();
        }catch (IOException e){
            throw new RestApiException(GlobalErrorStatus._FALIED_READ_FILE);
        }

        try {
            amazonS3.putObject(new PutObjectRequest(amazonConfig.getBucket(), keyName, stream, metadata));
        }catch (AmazonS3Exception e){
            throw new RestApiException(GlobalErrorStatus._S3_UPLOAD_ERROR);
        }

        return amazonS3.getUrl(amazonConfig.getBucket(), keyName).toString();
    }

    public void deleteFile(String keyName) {
        try {
            amazonS3.deleteObject(amazonConfig.getBucket(), keyName);
        }catch (AmazonS3Exception e){
            throw new RestApiException(GlobalErrorStatus._S3_DELETE_ERROR);
        }

    }

    public String generateKeyName(MultipartFile file, String directoryPath) { // 추후 서비스 단에서 디렉터리에 따른 패스 가져오기
        String uuid = UUID.randomUUID().toString();
        Uuid savedUuid = uuidRepository.save(Uuid.builder()
                .uuid(uuid).build());

        return directoryPath + '/' + file.getOriginalFilename() + "-" + savedUuid;
    }

}