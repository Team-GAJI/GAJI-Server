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
}
