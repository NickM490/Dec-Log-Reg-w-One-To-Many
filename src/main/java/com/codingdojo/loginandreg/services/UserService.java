package com.codingdojo.loginandreg.services;

import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.codingdojo.loginandreg.models.LoginUser;
import com.codingdojo.loginandreg.models.User;
import com.codingdojo.loginandreg.repositories.UserRepository;

@Service
public class UserService {

	
	
	@Autowired
    private UserRepository userRepo;
	
	
	public User oneUser(Long id) {
		Optional<User> optionalUser = userRepo.findById(id);
		if(optionalUser.isPresent()) {
			return optionalUser.get();
		} else {
			return null;
		}
	}
	
	
		
    // This is our Register Method
    public User register(User newUser, BindingResult result) {
    	// If check to see if the email from out New User is already present in the database
        if(userRepo.findByEmail(newUser.getEmail()).isPresent()) {
        	// If that email is already in the data base
            result.rejectValue("email", "Unique", "This email is already in use!");
        }
        
        // This checks to make sure the password and confirm password match eachother
        if(!newUser.getPassword().equals(newUser.getConfirm())) {
            result.rejectValue("confirm", "Matches", "The Confirm Password must match Password!");
        }
        // if we have errors in BindingResult return null - you get nothing
        if(result.hasErrors()) {
            return null;
        } else {
        	// If everything is good - we save the password encrypt via Bcrypt 
            String hashed = BCrypt.hashpw(newUser.getPassword(), BCrypt.gensalt());
            // Save the hashed PW in the DB
            newUser.setPassword(hashed);
            // Save the entire User object in the DB
            return userRepo.save(newUser);
        }
    }
    
    
    // This is for logging in
    public User login(LoginUser newLogin, BindingResult result) {
    	// IF there are any errors - you get nothing
        if(result.hasErrors()) {
            return null;
        }
        // This will get the email that the logging in user has submitted
        Optional<User> potentialUser = userRepo.findByEmail(newLogin.getEmail());
        // If that email does not exist in the DB - they get nothing
        if(!potentialUser.isPresent()) {
            result.rejectValue("email", "Unique", "Unknown email!");
            return null;
        }
        // If the email exists in the DB - we get that user based on the email
        User user = potentialUser.get();
        // If the password does not match the password in the DB - custom message sent to front end
        if(!BCrypt.checkpw(newLogin.getPassword(), user.getPassword())) {
            result.rejectValue("password", "Matches", "Invalid Password!");
        }
        // If there are any errors at this point in Binding Result - they get nothing
        if(result.hasErrors()) {
            return null;
        } else {
        	// But if all this is good then we return the User
            return user;
        }
    }
	
	
}
