package cn.guoduhao.TicketSystem.Controllers;

import cn.guoduhao.TicketSystem.Models.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import cn.guoduhao.TicketSystem.service.OrderService;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class OrderController {

    @Autowired
    OrderService orderService;

    @RequestMapping(value = "/purchase")
    public String placeOrder(HttpServletRequest request, Map<String, String> map){
        CurrentUser currentUser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String depart = request.getParameter("depart");
        String destination = request.getParameter("destination");
        String ticketId = this.orderService.placeOrder(
                currentUser.getId(),
                Integer.parseInt(request.getParameter("id")),
                depart,
                destination
        );
        map.put("train_id",request.getParameter("id"));
        map.put("ticket_id",ticketId);
        return "purchase";
    }

    @RequestMapping(value="/check_order_state")
    @ResponseBody
    public String checkOrderState(HttpServletRequest request){
        String ticketId = request.getParameter("ticket_id");
        CurrentUser currentUser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return this.orderService.checkOrderStatus(ticketId,currentUser.getId());
    }
}
