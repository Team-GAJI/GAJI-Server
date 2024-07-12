package gaji.service.domain.test;

import gaji.service.global.base.BaseResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

        @GetMapping
        public String test() {
            return "test ok";
        }

    //@Operation(summary = "성공적인 응답 반환 API", description = "테스트 문자열을 반환하는 API입니다.")
    //@ApiResponse(responseCode = "200", description = "테스트 문자열을 성공적으로 반환")
    @GetMapping("/success")
    public BaseResponse<String> successResponseAPI() {
        return BaseResponse.onSuccess("This is Test!");
    }
}
