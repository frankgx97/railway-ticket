package cn.guoduhao.TicketSystem.service.user;

import cn.guoduhao.TicketSystem.Models.User;
import cn.guoduhao.TicketSystem.Models.UserCreateForm;

import java.util.Collection;
import java.util.Optional;

public interface UserService {

    Optional<User> getUserById(String id);

    Optional<User> getUserByEmail(String email);

    Collection<User> getAllUsers();

    User create(UserCreateForm form);
}
