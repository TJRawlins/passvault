package com.passvault.spring.pwgen;

import java.util.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.passvault.spring.dtos.PasswordGeneratorDto;

@CrossOrigin
@RestController
@RequestMapping("/api/pwgen")
public class PasswordGeneratorController {
	
	@GetMapping()
	public ResponseEntity<String> GeneratorPassword(@RequestBody PasswordGeneratorDto pwgenDto) {
        if(pwgenDto == null) return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        
        if(pwgenDto.length < 5) return new ResponseEntity<String>("Length must be greater than 4",HttpStatus.BAD_REQUEST);
        

        // result
        String pw = "";
        
        // OPTION STRINGS - GETS PASSED TO RANDOMCHAR()
        String lower = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toLowerCase();
        String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String digits = "123456789";
        String special = "/|\\*+-{}[]()!@#$%^&*~?><";
        
        // LOOP TO ADD CHARACTERS TO PASSWORD
        while(true)
        {
        	pw += pwgenDto.digits ? randomChar(digits) : "";
        	if (pw.length() == pwgenDto.length) break;
        	pw += pwgenDto.lowerCase ? randomChar(lower) : "";
        	if (pw.length() == pwgenDto.length) break;
        	pw += pwgenDto.special ? randomChar(special) : "";
        	if (pw.length() == pwgenDto.length) break;
        	pw += pwgenDto.upperCase ? randomChar(upper) : "";
        	if (pw.length() == pwgenDto.length) break;
        }
        // SCRAMBLES PASSWORD STRING TO MAXIMIZE RANDOMNESS
        pw = scrambleStr(pw);
        
        return new ResponseEntity<String>(pw, HttpStatus.OK);
    }
	
	// RETURN RANDOM CHARACTER OF STRING
	static String randomChar(String str) {
		Random rand = new Random();
		char character = str.charAt(rand.nextInt(0,str.length()));
		return String.valueOf(character);
	}
	
	// SCRAMBLE STRING
	static String scrambleStr(String str) {
	    List<Character> list = new ArrayList<Character>();
	    for(char c : str.toCharArray()) {
	        list.add(c);
	    }
	    Collections.shuffle(list);
	    StringBuilder builder = new StringBuilder();
	    for(char c : list) {
	        builder.append(c);
	    }
	    String shuffled = builder.toString();
	    return shuffled;
	}
	
	
	
	
	
	
	
	
}
