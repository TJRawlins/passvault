package com.passvault.spring.category;

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

import com.passvault.spring.user.User;
import com.passvault.spring.user.UserRepository;


@CrossOrigin
@RestController
@RequestMapping("/api/category")
public class CategoryController {
	
	@Autowired
	private CategoryRepository catRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	
	// GET ALL
	@GetMapping
	public ResponseEntity<Iterable<Category>> getCategorys() {
		Iterable<Category> category = catRepo.findAll();
		return new ResponseEntity<>(category, HttpStatus.OK);
	}
	
	// GET BY ID
	@GetMapping("{id}")
	public ResponseEntity<Category> getCategory(@PathVariable int id) {
		if(id <= 0) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		Optional<Category> category = catRepo.findById(id);
		
		if(category.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);;
		
		return new ResponseEntity<Category>(category.get(), HttpStatus.OK);
	}
	
	// POST
	@PostMapping
	public ResponseEntity<Category> postCategory(@RequestBody Category category) {
		if(category.getId() != 0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		// CHECK FOR AMDIN PRIVILEGE
		Optional<User> user = userRepo.findById(category.getUser().getId());
		if(!user.get().getAdmin()) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		
		catRepo.save(category);
		return new ResponseEntity<Category>(category, HttpStatus.CREATED);
	}
	
	// PUT
	@SuppressWarnings("rawtypes")
	@PutMapping("{id}")
	public ResponseEntity putCategory(@RequestBody Category category) {
		if(category.getId() <= 0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		// CHECK FOR AMDIN PRIVILEGE
		Optional<User> user = userRepo.findById(category.getUser().getId());
		if(!user.get().getAdmin() || user.get().getId() != category.getUser().getId()) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		
		catRepo.save(category);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	// DELETE
		@SuppressWarnings("rawtypes")
		@DeleteMapping("{id}")
		public ResponseEntity DeleteCategory(@PathVariable int id) {
			if(id <= 0) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
			// CHECK FOR AMDIN PRIVILEGE
			
			catRepo.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
}
