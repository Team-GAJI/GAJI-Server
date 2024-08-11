package gaji.service.domain.user.service;

import gaji.service.domain.user.entity.User;

public interface UserCommandService {
    User findById(Long userId);
}
