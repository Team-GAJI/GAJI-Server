package gaji.service.domain.user.service;

import gaji.service.domain.User;

import java.util.Optional;

public interface UserQueryService {

    boolean existUserById(Long userId);
    User findUserById(Long userId);

}
