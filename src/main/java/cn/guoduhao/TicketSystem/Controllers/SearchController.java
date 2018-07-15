package cn.guoduhao.TicketSystem.Controllers;

import cn.guoduhao.TicketSystem.Models.Train;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import cn.guoduhao.TicketSystem.repository.TrainRepository;
import cn.guoduhao.TicketSystem.service.ticket.TicketServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class SearchController {

    @Autowired
    TrainRepository trainRepository;

    @Autowired
    TicketServiceImpl ticketServiceImpl;

    @RequestMapping("/search")
    public String index(HttpServletRequest request, Map<String, List<Train>> map, Map<String, String> stationMap){
        /*
        List<Train> result = trainRepository.findByDepartStationAndDestinationStation(
                request.getParameter("depart-station"),
                //request.getParameter("depart-time"),
                request.getParameter("destination-station")
        );
        */
        String depart = request.getParameter("depart-station");
        String destination = request.getParameter("destination-station");

        //TODO 完成后更改BJSH
        List<String> trainList = ticketServiceImpl.mapToTrainNo_BJ_SH(
                depart,
                destination
        );

        List<Train> result = new ArrayList<>();

        for(int i=0;i<trainList.size();i++){
            List<Train> trainListTemp = trainRepository.findByTrainNo(trainList.get(i));
            for(int j=0;j<trainListTemp.size();j++){
                result.add(trainListTemp.get(j));
            }
        }

        map.put("trains", result);
        stationMap.put("depart", depart);
        stationMap.put("destination", destination);
        return "search";
    }
}
