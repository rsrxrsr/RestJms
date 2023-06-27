package rest.brodcast;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import rest.jms.JmsDto;
 
@RestController
//@CrossOrigin("*")
@RequestMapping("webapi")
public class BrodcastController {

	@Autowired
	private BrodcastService brodcastService;

//	@GetMapping(produces = "application/json; charset=UTF-8")
	@GetMapping(path="/subscribe", produces = "text/event-stream")
	SseEmitter subscribe() {
		System.out.println("/subscribe");
		return brodcastService.subscribe();		
	}
	
	@GetMapping(path="/brodcast")
	String brodcast() {
		System.out.print("brodcast");
		brodcastService.brodcast(new JmsDto("hdr","request","recive"));
		return "Brodcasting";
	}
			
}
