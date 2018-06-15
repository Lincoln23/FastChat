package co.FastApps.FastChat.Controller;

import co.FastApps.FastChat.Entity.EndResult;
import co.FastApps.FastChat.Service.AWS_Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping
class AWS_Controller {

	@Autowired
	private AWS_Service aws_service;

	//send a get request to ....8080/{your message}
	@RequestMapping(value = "/{text}", method = RequestMethod.GET)
	EndResult listEntity(@PathVariable("text") String text, @RequestParam(defaultValue = "10", required = false) int limit) {
		System.out.println("this is limit >>> " + limit);
		return this.aws_service.comprehend(text, limit);
	}

}