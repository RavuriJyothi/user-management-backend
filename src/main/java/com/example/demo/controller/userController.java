package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.User;
import com.example.demo.repository.userRepository;

@RestController
@CrossOrigin("http://localhost:5173")
public class userController {
	
	@Autowired
	private userRepository userRepository;
	
	@PostMapping("/user")
	public User createUser(@RequestBody User user) {
	    return userRepository.save(user);
	}
	@GetMapping("/users")
	List<User> getAllUser(){
		return userRepository.findAll();
	}
	
	@GetMapping("/user/{id}")
	User getUserById(@PathVariable Long id) {
		return userRepository.findById(id)
				.orElseThrow(()->new UserNotFoundException(id));
	}
	@PutMapping("/user/{id}")
	User UpdateUser(@RequestBody User newuser,@PathVariable Long id) {
		return userRepository.findById(id)
				.map(user ->{
					user.setUsername(newuser.getUsername());
					user.setName(newuser.getName());
					user.setEmail(newuser.getEmail());
					return userRepository.save(user);
				}).orElseThrow(()-> new UserNotFoundException(id));
				
	}
	@DeleteMapping("/user/{id}")
	String deleteUser(@PathVariable Long id) {
		if(!userRepository.existsById(id)) {
			throw new UserNotFoundException(id);
		}
		userRepository.deleteById(id);
		return "user deleted successfully in id:" +id;
	}
}
