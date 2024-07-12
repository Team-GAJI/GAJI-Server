package gaji.service.domain.test;

import gaji.service.apiPayload.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

        //Get 요청으로 api 테스트
        @GetMapping
        public String test() {
            return "test ok";
        }

        // 통일된 응답 테스트 , 성공한 경우
        @GetMapping("/api/success")
        public ApiResponse<TempResponse.TempTestDTO> successAPI(){
            return ApiResponse.onSuccess(TempConverter.toSuccessTestDTO());
        }


    }
