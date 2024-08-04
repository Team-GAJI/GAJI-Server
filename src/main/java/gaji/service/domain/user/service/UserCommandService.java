package gaji.service.domain.user.service;

import gaji.service.domain.user.entity.User;
import gaji.service.domain.user.web.dto.UserRequestDTO;

public interface UserCommandService {
    public User cancleUser(Long userId);
    public User updateUserNickname(Long userId, UserRequestDTO.UpdateNicknameDTO request);
    public void hardDeleteInactiveUsers();
}
