package cn.guoduhao.TicketSystem.Controllers;

import cn.guoduhao.TicketSystem.Models.CurrentUser;
import cn.guoduhao.TicketSystem.Models.Train;
import cn.guoduhao.TicketSystem.repository.TrainRepository;
import cn.guoduhao.TicketSystem.service.ticket.TicketService;
import cn.guoduhao.TicketSystem.service.ticket.TicketServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import cn.guoduhao.TicketSystem.service.OrderService;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.swing.text.html.Option;
import java.util.Map;
import java.util.Optional;

@Controller
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    TrainRepository trainRepository;

    @Autowired
    TicketService ticketService;

    @RequestMapping(value = "/purchase")
    public String placeOrder(HttpServletRequest request, Map<String, String> map, Map<String, Train> trainMap){
        CurrentUser currentUser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String depart = request.getParameter("depart");
        String destination = request.getParameter("destination");
        String ticketId = this.orderService.placeOrder(
                currentUser.getId(),
                Integer.parseInt(request.getParameter("id")),
                depart,
                destination
        );
        Optional<Train> train = trainRepository.findOneById(Integer.parseInt(request.getParameter("id")));
        train.get().expense = Math.round(this.ticketService.countFee(depart,destination,train.get().trainNo));

        trainMap.put("train", train.get());

        map.put("train_id",request.getParameter("id"));
        map.put("ticket_id",ticketId);
        map.put("depart", depart);
        map.put("destination", destination);
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
