package cn.guoduhao.TicketSystem.Controllers;

import cn.guoduhao.TicketSystem.Models.CurrentUser;
import cn.guoduhao.TicketSystem.Models.User;
import org.aspectj.weaver.ast.Or;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import cn.guoduhao.TicketSystem.service.OrderService;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class OrderController {

    @Autowired
    OrderService orderService;

    @RequestMapping(value = "/buy")
    public String placeOrder(HttpServletRequest request, Map<String, String> map){
        CurrentUser currentUser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        orderService.placeOrderService(currentUser.getId(), Integer.parseInt(request.getParameter("id")));
        map.put("train_id",request.getParameter("id"));
        return "buy";
    }

    @RequestMapping("/check_order_state")
    public void checkOrderState(){

    }
}
