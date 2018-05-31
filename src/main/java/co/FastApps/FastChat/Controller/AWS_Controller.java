package co.FastApps.FastChat.Controller;

import co.FastApps.FastChat.Entity.ResultType;
import co.FastApps.FastChat.Service.AWS_Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/Select")
public class AWS_Controller {

    @Autowired
    private AWS_Service aws_service;

//    @RequestMapping(method = RequestMethod.GET)
//    public List<ResultType> getEverything(){
//        return aws_service.getEverything();
//    }
    @RequestMapping(value = "/{text}", method = RequestMethod.GET)
    public List<List<ResultType>> listEntity(@PathVariable("text")String text){
        return this.aws_service.comprehend(text);
    }
}
