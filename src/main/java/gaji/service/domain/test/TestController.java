package gaji.service.domain.test;

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

    }
