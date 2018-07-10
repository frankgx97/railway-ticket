package cn.guoduhao.TicketSystem.Controllers;

import cn.guoduhao.TicketSystem.Models.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import cn.guoduhao.TicketSystem.service.OrderService;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class OrderController {

    @Autowired
    OrderService orderService;

    @RequestMapping(value = "/purchase")
    public String placeOrder(HttpServletRequest request, Map<String, String> map){
        CurrentUser currentUser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        orderService.placeOrder(currentUser.getId(), Integer.parseInt(request.getParameter("id")));
        map.put("train_id",request.getParameter("id"));
        return "purchase";
    }

    @RequestMapping("/check_order_state")
    public void checkOrderState(){

    }
}
