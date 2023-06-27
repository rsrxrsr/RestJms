package rest.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import rest.repository.UserRepository;
import rest.entity.User;

@RestController
@RequestMapping("restapi/user")
public class UserController {

	@Autowired
	private UserRepository entityService;
	
	//@CrossOrigin(origins = "*")
	@RequestMapping(value = "/filter{entity}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public List<User> list(@PathVariable String entity) {
		System.out.print("********** READ ******************");
		List<User> listEntity = (List<User>) entityService.findAll();
		//return new ResponseEntity(listEntity, HttpStatus.OK);
		return listEntity;
	}

	@PostMapping("/login")	
	User login(@RequestBody User user) {
		return entityService.findByUserAndPassword(user.getUser(), user.getPassword()). orElse(new User());		
	}
}
