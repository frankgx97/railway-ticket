package cn.guoduhao.TicketSystem.service.currentuser;

import cn.guoduhao.TicketSystem.Models.CurrentUser;
import cn.guoduhao.TicketSystem.Models.Role;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserServiceImpl implements CurrentUserService {

    @Override
    public boolean canAccessUser(CurrentUser currentUser, String userId) {
        return currentUser != null
                && (currentUser.getRole() == Role.ADMIN || currentUser.getId().equals(userId));
    }
}