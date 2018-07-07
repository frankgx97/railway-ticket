package cn.guoduhao.TicketSystem.Controllers;

import cn.guoduhao.TicketSystem.Models.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class OrderController {

    @RequestMapping(value = "/buy")
    public String placeOrder(HttpServletRequest request, Map<String, String> map){
        map.put("train_id",request.getParameter("train_id"));
        return "buy";
    }

    @RequestMapping("/check_order_state")
    public void checkOrderState(){

    }
}
