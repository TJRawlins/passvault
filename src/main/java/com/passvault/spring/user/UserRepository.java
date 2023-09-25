package com.passvault.spring.user;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends CrudRepository<User, Integer> {
	Optional<User> findUserByUsernameAndPassword(String username, String password);
	
}
