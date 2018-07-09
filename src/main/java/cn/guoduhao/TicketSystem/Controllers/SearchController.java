package cn.guoduhao.TicketSystem.Controllers;

import cn.guoduhao.TicketSystem.Models.Train;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import cn.guoduhao.TicketSystem.repository.TrainRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
public class SearchController {

    @Autowired
    TrainRepository trainRepository;

    @RequestMapping("/search")
    public String index(HttpServletRequest request, Map<String, List<Train>> map){
        List<Train> result = trainRepository.findByDepartStationAndDestinationStation(
                request.getParameter("depart-station"),
                //request.getParameter("depart-time"),
                request.getParameter("destination-station")
        );
        map.put("trains", result);
        return "search";
    }
}
