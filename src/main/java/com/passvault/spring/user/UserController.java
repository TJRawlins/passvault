package com.passvault.spring.user;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.passvault.spring.dtos.LoginDto;
import com.passvault.spring.encrypt.*;


@CrossOrigin
@RestController
@RequestMapping("/api/user")
public class UserController extends EncryptAes{
	
	@Autowired
	private UserRepository userRepo;
	
	// GET LOGIN - AES-256 ENCRYPTION [USING DTO]
	@PostMapping("login")
	public ResponseEntity<User> login(@RequestBody LoginDto loginDto) {
		// Decrypt Secret Key then Encrypt Password using AES256 and check for match
		String decSecret = SecretKey.DecryptSecret();
        String encPw = EncryptAes.encrypt(loginDto.password, decSecret); 
        Optional<User> user = userRepo.findUserByUsernameAndPassword(loginDto.username, encPw);
        if(user.isEmpty()) {
        	return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<User>(user.get(), HttpStatus.OK);
    }
	
	// GET ALL
	@GetMapping
	public ResponseEntity<Iterable<User>> getUsers() {
		Iterable<User> user = userRepo.findAll();
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	
	// GET BY ID
	@GetMapping("{id}")
	public ResponseEntity<User> getUser(@PathVariable int id) {
		if(id <= 0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Optional<User> user = userRepo.findById(id);
		if(user.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<User>(user.get(), HttpStatus.OK);
	}
	
	// POST - USING AES ENCRPYTION
	@PostMapping
	public ResponseEntity<User> postUser(@RequestBody User user) {
		if(user.getId() != 0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		// Encrypt Password AES256
		String decSecret = SecretKey.DecryptSecret();
        String encPw = EncryptAes.encrypt(user.getPassword(), decSecret); 
        user.setPassword(encPw);
		
		userRepo.save(user);
		return new ResponseEntity<User>(user, HttpStatus.CREATED);
	}
	
	// PUT
	@SuppressWarnings("rawtypes")
	@PutMapping("{id}")
	public ResponseEntity putUser(@RequestBody User user) {
		if(user.getId() <= 0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		// Encrypt Password AES256
		String decSecret = SecretKey.DecryptSecret();
        String encPw = EncryptAes.encrypt(user.getPassword(), decSecret);
        user.setPassword(encPw);

		userRepo.save(user);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	// DELETE
		@SuppressWarnings("rawtypes")
		@DeleteMapping("{id}")
		public ResponseEntity DeleteUser(@PathVariable int id) {
			if(id <= 0) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			userRepo.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	

}
