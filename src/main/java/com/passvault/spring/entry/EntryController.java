package com.passvault.spring.entry;

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

import com.passvault.spring.encrypt.EncryptAes;
import com.passvault.spring.encrypt.SecretKey;
import com.passvault.spring.user.User;
import com.passvault.spring.user.UserRepository;


@CrossOrigin
@RestController
@RequestMapping("/api/entry")
public class EntryController {
	
	@Autowired
	private EntryRepository entryRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	// GET ALL
	@GetMapping
	public ResponseEntity<Iterable<Entry>> getEntrys() {
		Iterable<Entry> entry = entryRepo.findAll();
		return new ResponseEntity<>(entry, HttpStatus.OK);
	}
	
	// GET BY ID
	@GetMapping("{id}")
	public ResponseEntity<Entry> getEntry(@PathVariable int id) {
		if(id <= 0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Optional<Entry> entry = entryRepo.findById(id);
		if(entry.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Entry>(entry.get(), HttpStatus.OK);
	}	
	
	// GET CLAERTEXT PASSWORD
	@GetMapping("getpass/{id}")
	public ResponseEntity<String> getCtPass(@PathVariable int id) {
		if(id <= 0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Optional<Entry> entry = entryRepo.findById(id);
		if(entry.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		String decSecret = SecretKey.DecryptSecret();
		String encPw = entry.get().getPassword();
		String decPw = EncryptAes.decrypt(encPw, decSecret);
	
		return new ResponseEntity<String>(decPw, HttpStatus.OK);
	}
	
	// POST
	@PostMapping
	public ResponseEntity<Entry> postEntry(@RequestBody Entry entry) {
		if(entry.getId() != 0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		// CHECK FOR AMDIN PRIVILEGE
		Optional<User> user = userRepo.findById(entry.getCategory().getUser().getId());
		if(!user.get().getAdmin()) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		
		// Encrypt Password AES256
		String decSecret = SecretKey.DecryptSecret();
        String encPw = EncryptAes.encrypt(entry.getPassword(), decSecret); 
        entry.setPassword(encPw);
		entryRepo.save(entry);
		return new ResponseEntity<Entry>(entry, HttpStatus.CREATED);
	}
	
	// PUT
	@SuppressWarnings("rawtypes")
	@PutMapping("{id}")
	public ResponseEntity putEntry(@RequestBody Entry entry) {
		if(entry.getId() <= 0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		// CHECK FOR AMDIN PRIVILEGE
		Optional<User> user = userRepo.findById(entry.getCategory().getUser().getId());
		if(!user.get().getAdmin()) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		
		entryRepo.save(entry);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	// DELETE
		@SuppressWarnings("rawtypes")
		@DeleteMapping("{id}")
		public ResponseEntity DeleteEntry(@PathVariable int id) {
			if(id <= 0) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			entryRepo.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

}
