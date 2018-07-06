package cn.guoduhao.TicketSystem.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SearchController {

    @RequestMapping("/search")
    public String index(){
        return "search";
    }
}
