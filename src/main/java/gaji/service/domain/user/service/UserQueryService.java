package gaji.service.domain.user.service;

import gaji.service.domain.User;

public interface UserQueryService {

    boolean existUserById(Long userId);
    User findUserById(Long userId);
}
