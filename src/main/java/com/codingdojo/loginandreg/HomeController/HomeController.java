package com.codingdojo.loginandreg.HomeController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.codingdojo.loginandreg.models.Idea;
import com.codingdojo.loginandreg.models.LoginUser;
import com.codingdojo.loginandreg.models.User;
import com.codingdojo.loginandreg.services.IdeaService;
import com.codingdojo.loginandreg.services.UserService;

@Controller
public class HomeController {

	
	 	@Autowired
	    private UserService userServ;
	 	@Autowired
	    private IdeaService ideaServ;
	    
//	    public HomeController(IdeaService ideaServ) {
//	    	this.ideaServ = ideaServ;
//	    }
	 
	 	// ************** LOGIN AND REG START ***********************
	 	// **********************************************************
	 	
	 	// This route renders the forms for login and reg
	    @GetMapping("/")
	    public String index(Model model) {
	        model.addAttribute("newUser", new User());
	        model.addAttribute("newLogin", new LoginUser());
	        return "index.jsp";
	    }
	    
	    // This route is the action for submitting the registration form
	    @PostMapping("/register")
	    public String register(@Valid @ModelAttribute("newUser") User newUser, 
	            BindingResult result, Model model, HttpSession session) {
	    	// This calls on the register method in service - sends in the newUser info and the reulsts from the Binding Result
	        userServ.register(newUser, result);
	        // If we have any errors we stay on the that page and display errors
	        if(result.hasErrors()) {
	            model.addAttribute("newLogin", new LoginUser());
	            return "index.jsp";
	        }
	        // If everything is good, set the UserId in session
	        session.setAttribute("user_id", newUser.getId());
	        return "redirect:/dashboard";
	    }
	    
	    
	    // This route is the action for submitting the login form
	    @PostMapping("/login")
	    public String login(@Valid @ModelAttribute("newLogin") LoginUser 							newLogin, 
	            			BindingResult result, Model model, HttpSession 							session) {
	    	
	        User user = userServ.login(newLogin, result);
	        // If we have any errors we stay on the page and display errors
	        if(result.hasErrors()) {
	            model.addAttribute("newUser", new User());
	            return "index.jsp";
	        }
	        // If no errors, set the UserID in session
	        session.setAttribute("user_id", user.getId());
	        return "redirect:/dashboard";
	    }
	    
	    // Method for Logging Out
	    @GetMapping("/logout")
	    public String logout(HttpSession session) {
	    	session.removeAttribute("user_id");
	    	return "redirect:/";
	    }
	
		// ************** LOGIN AND REG END ***********************
		// **********************************************************
	    
	    
	    
	    
	    
		@RequestMapping("/dashboard")
		public String dashboard(Model model, HttpSession session) {
			
			if(session.getAttribute("user_id") != null) {
			Long user_id = (Long) session.getAttribute("user_id");
			model.addAttribute("user", userServ.oneUser(user_id));
			model.addAttribute("ideas", ideaServ.allIdeas());
			
			return "dashboard.jsp";
		}
			else {
				return "redirect:/";
			}
		}
		
		
		// Renders the form to make an Idea
		@RequestMapping("/newIdea")
		public String newidea(@ModelAttribute("idea") Idea idea, Model model, 							HttpSession session) {
			Long user_id = (Long) session.getAttribute("user_id");
			model.addAttribute("user", userServ.oneUser(user_id));
			System.out.println("I am rendering the view for making an idea");
			return "newIdea.jsp";
		}
		
		// Make a method to actually create an Idea
		@RequestMapping("/processIdea")
		public String processIdea(@Valid @ModelAttribute("idea") Idea idea, 									BindingResult result, Model model, 									HttpSession session) {
			System.out.println("AM I even starting the method");
			if(result.hasErrors()) {
				Long user_id = (Long) session.getAttribute("user_id");
				model.addAttribute("user", userServ.oneUser(user_id));
				System.out.println("In the if, must be an error");
				return "newIdea.jsp";
			}
			System.out.println("Outside the if, looks to be ok.");
			ideaServ.create(idea);
			return "redirect:/dashboard";
			
		}
		
		
		// To show one Idea
		@RequestMapping("/oneIdea/{id}")
		public String viewIdea(@PathVariable("id") Long id, 							Model model) {
			model.addAttribute("idea", ideaServ.oneIdea(id));
			return "oneIdea.jsp";
		}
		
		
		
		// Render the form to edit an idea
		
		// This is triggered from the url request
		@RequestMapping("/editIdea/{id}")
		// This is the start to the method
		public String editIdea(@PathVariable("id") Long id, Model model) {
			// THis creates a variable to represent an instance that we are 				finding by
			// calling on a method that is in our Service file.
			Idea idea = ideaServ.oneIdea(id);
			model.addAttribute("idea", idea);
			return "editIdea.jsp";
		}
		
		// Method to actually Edit the idea
		@RequestMapping(value="/editIdea/{id}", method= 					RequestMethod.PUT)
		public String editingIdea(@Valid @ModelAttribute("idea") Idea idea, 									BindingResult result) {
			if(result.hasErrors()) {
				return "editIdea.jsp";
			}
			else {
				ideaServ.updateIdea(idea);
				return "redirect:/dashboard";
			}
		}
		
		@RequestMapping("/delete/{id}")
		public String deleteIdea(@PathVariable("id") Long id) {
			ideaServ.deleteIdea(id);
			return "redirect:/dashboard";
		}
		
		@RequestMapping("/creator/{id}")
		public String creator(@PathVariable("id") Long id, Model model, 								HttpSession session) {
			Long user_id = (Long) id;
			model.addAttribute("user", userServ.oneUser(id));
			
			return "creatorPage.jsp";
		}
	
}
