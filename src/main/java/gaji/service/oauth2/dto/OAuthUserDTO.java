package gaji.service.oauth2.dto;

import gaji.service.domain.enums.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OAuthUserDTO {

    private Role role;
    private String name;
    // 서버에서 발급받는 아이디
    private String usernameId;


}