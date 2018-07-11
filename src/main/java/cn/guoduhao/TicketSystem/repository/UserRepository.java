package cn.guoduhao.TicketSystem.repository;

import cn.guoduhao.TicketSystem.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findOneByEmail(String email);
    Optional<User> findOneById(String id);
}
