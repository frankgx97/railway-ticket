package cn.guoduhao.TicketSystem.Controllers;

import cn.guoduhao.TicketSystem.Models.CurrentUser;
import cn.guoduhao.TicketSystem.Models.Ticket;
import cn.guoduhao.TicketSystem.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Map;

@Controller
public class HomeController {

    @Autowired
    OrderService orderService;

    @RequestMapping("/home")
    public String home(Map<String, List<Ticket>> map){
        CurrentUser currentUser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Ticket> ticketList = this.orderService.readOrdersFromRedis(currentUser.getId());
        map.put("ticketList",ticketList);
        return "home";
    }
}
