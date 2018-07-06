package cn.guoduhao.TicketSystem.service.currentuser;

import cn.guoduhao.TicketSystem.Models.CurrentUser;

public interface CurrentUserService {

    boolean canAccessUser(CurrentUser currentUser, String userId);
}
