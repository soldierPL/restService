package hello;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloConroller {

    @RequestMapping("/")
    public String index(){
        return "Próba SpringBoota";
    }
}
