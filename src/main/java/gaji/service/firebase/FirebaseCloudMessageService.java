package gaji.service.firebase;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import gaji.service.firebase.FcmMessage;
import lombok.RequiredArgsConstructor;
import okhttp3.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * Firebase Cloud Messaging 서비스 클래스
 * FCM을 통해 안드로이드/iOS 디바이스로 푸시 알림을 전송합니다.
 */
@Component
@RequiredArgsConstructor
public class FirebaseCloudMessageService {

    // FCM API URL (프로젝트 ID를 본인의 것으로 변경해야 함)
    private final String API_URL = "https://fcm.googleapis.com/v1/projects/gaji-57b03/messages:send";

    // JSON 변환을 위한 ObjectMapper
    private final ObjectMapper objectMapper;

    /**
     * 특정 디바이스로 FCM 푸시 메시지를 전송합니다.
     *  이 메소드는 매개변수로 전달받은 targetToken에 해당하는 device로 FCM 푸시알림을 전송 요청합니다

     * @param targetToken 푸시 메시지를 받을 디바이스의 FCM 토큰
     * @param title 알림 제목
     * @param body 알림 내용
     */
    public void sendMessageTo(String targetToken, String title, String body) throws IOException {
        // FCM 메시지 생성
        String message = makeMessage(targetToken, title, body);

        // OkHttp 클라이언트 생성
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(
                message,
                MediaType.get("application/json; charset=utf-8")
        );

        // FCM API 요청 객체 생성
        Request request = new Request.Builder()
                .url(API_URL)
                .post(requestBody)
                .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
                .build();

        // API 호출 및 응답 출력
        Response response = client.newCall(request).execute();
        System.out.println(response.body().string());
    }

    /**
     * FCM 메시지 포맷에 맞는 JSON 문자열을 생성합니다.
     * 마지막입니다. FcmMessage를 만들고, 이를 ObjectMapper을 이용해 String으로 변환하여 반환합니다.

     * @param targetToken 푸시 메시지를 받을 디바이스의 FCM 토큰
     * @param title 알림 제목
     * @param body 알림 내용
     * @return FCM 메시지 JSON 문자열
     */
    private String makeMessage(String targetToken, String title, String body) throws JsonProcessingException {
        FcmMessage fcmMessage = FcmMessage.builder()
                .message(FcmMessage.Message.builder()
                        .token(targetToken)
                        .notification(FcmMessage.Notification.builder()
                                .title(title)
                                .body(body)
                                .image(null)
                                .build()
                        )
                        .build()
                )
                .validate_only(false)
                .build();

        return objectMapper.writeValueAsString(fcmMessage);
    }

    /**
     * Firebase Admin SDK로부터 Access Token을 가져옵니다.
     */
    private String getAccessToken() throws IOException {
        // Firebase 서비스 계정 키 파일 경로
        String firebaseConfigPath = "firebase/firebase_service_key.json";

        // Google Credentials 생성
        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())
                .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));

        // 토큰 갱신 및 반환
        googleCredentials.refreshIfExpired();
        return googleCredentials.getAccessToken().getTokenValue();
    }
}