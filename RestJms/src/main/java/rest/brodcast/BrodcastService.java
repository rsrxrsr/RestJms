package rest.brodcast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import rest.jms.JmsDto;

@Service
public class BrodcastService {
	
	static List<SseEmitter> emitters = new ArrayList<>();
	
	public SseEmitter subscribe() {
		SseEmitter emitter = new SseEmitter(24 * 60 * 60 * 1000l);
		emitter.onCompletion(()->emitters.remove(emitter));
		emitter.onTimeout(()->emitters.remove(emitter));
		emitters.add(emitter);
		System.out.println("Subscription " + emitters.size());
		return emitter;
	}
 
	public void brodcast(JmsDto jmsDto) {
		System.out.println("in Brodcast " + emitters.size());
		List<SseEmitter> deadEmitters = new ArrayList<>();
		emitters.forEach(emitter -> {
			try {
				System.out.println("Emitter");
				emitter.send(SseEmitter
					   .event()
					   .name("brodcast")
					   .data(jmsDto)
				);
			} catch (IOException e) {
				System.out.println("Error");
				System.out.println(e);
				deadEmitters.add(emitter);
			}
		});
		emitters.removeAll(deadEmitters);
	}
		
}
