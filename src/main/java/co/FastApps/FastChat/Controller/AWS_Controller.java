package co.FastApps.FastChat.Controller;

import co.FastApps.FastChat.Entity.EndResult;
import co.FastApps.FastChat.Service.AWS_Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping
public class AWS_Controller {

    @Autowired
    private AWS_Service aws_service;

    @RequestMapping(value = "/{text}", method = RequestMethod.GET)
    public EndResult listEntity(@PathVariable("text") String text) {
        return this.aws_service.comprehend(text);
    }

}