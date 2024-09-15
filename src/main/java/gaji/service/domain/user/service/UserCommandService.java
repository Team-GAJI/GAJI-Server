package gaji.service.domain.user.service;

import gaji.service.domain.user.entity.User;
import gaji.service.domain.user.web.dto.UserRequestDTO;
import gaji.service.domain.user.web.dto.UserResponseDTO;

public interface UserCommandService {
    public UserResponseDTO.CancleResultDTO cancleUser(Long userId);
    public UserResponseDTO.UpdateNicknameResultDTO updateUserNickname(Long userIdFromToken, Long userIdFromPathVariable, UserRequestDTO.UpdateNicknameDTO request);
    public void hardDeleteInactiveUsers();
    void save(User user);

}
