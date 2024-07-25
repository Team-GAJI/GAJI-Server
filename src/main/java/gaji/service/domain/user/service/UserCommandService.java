package gaji.service.domain.user.service;

import gaji.service.domain.user.User;

public interface UserCommandService {
    public User logoutUser(Long userId);
    //public User logoutUser(추후 유저 객체 사용)
}
