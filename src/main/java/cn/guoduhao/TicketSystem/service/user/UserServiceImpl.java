package cn.guoduhao.TicketSystem.service.user;

import cn.guoduhao.TicketSystem.Models.User;
import cn.guoduhao.TicketSystem.Models.UserCreateForm;
import cn.guoduhao.TicketSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository IUserRepository) {
        this.userRepository = IUserRepository;
    }

    @Override
    public Optional<User> getUserById(String id) {
        return userRepository.findOneById(id);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findOneByEmail(email);
    }

    @Override
    public Collection<User> getAllUsers() {
        return userRepository.findAll(new Sort("email"));
    }

    @Override
    public User create(UserCreateForm form) {
        User user = new User();
        Date date = new Date();
        Long timestamp = Long.valueOf(date.getTime()/1000);
        user.setEmail(form.getEmail());
        user.setName(form.getName());
        user.setPassword(new BCryptPasswordEncoder().encode(form.getPassword()));
        user.setRole(form.getRole());
        user.setCreatedAt(timestamp);
        user.setUpdatedAt(timestamp);

        return userRepository.save(user);
    }
}
