package com.paymybuddy.ewallet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.annotation.JsonView;
import com.paymybuddy.ewallet.dto.UserLoginDto;
import com.paymybuddy.ewallet.dto.UserProfileDto;
import com.paymybuddy.ewallet.model.User;
import com.paymybuddy.ewallet.service.IUserService;
import com.paymybuddy.ewallet.views.UserView;

/**
 * A class that receives requests made from some of the usual CRUD endpoints and
 * specific URL for the Users. The <code>@CrossOrigin</code> annotation limits
 * the acceptance of requests to those sent from the front-end.
 * 
 * @singularity Some responses are filtered using JsonView to match the response
 *              pattern desired by the front-end.
 * 
 * @author Sébastien Cappon
 * @version 1.0
 */
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

	@Autowired
	private IUserService iUserService;

	/**
	 * A <code>GetMapping</code> method on the <code>/users/login</code> URI with an
	 * user id as <code>PathVariable</code>. It calls the <code>IUserService</code>
	 * methods <code>getUserById(int userId)</code> and returns the
	 * <code>User</code> model entity whose id is the one passed in parameter.
	 * 
	 * @frontCall <code>auth.service.ts</code> : <code>getUserById(userId: number)</code>
	 * 
	 * @view Display id, firstname, lastname, amount for the involved
	 *       <code>User</code> model entity.
	 * 
	 * @return A <code>User</code>.
	 */
	@JsonView(UserView.LoginView.class)
	@GetMapping("/users/login/{id}")
	@ResponseBody
	public User getUserById_forLoginPage(@PathVariable("id") int userId) {
		return iUserService.getUserById(userId);
	}

	/**
	 * A <code>GetMapping</code> method on the <code>/users/login</code> URI with an
	 * user id as <code>PathVariable</code>. It calls the <code>IUserService</code>
	 * methods <code>getUserById(int userId)</code> and returns the
	 * <code>User</code> model entity whose id is the one passed in parameter.
	 * 
	 * @frontCall <code>profile.service.ts</code> : <code>getUserById(userId: number)</code>
	 * 
	 * @view Display firstname, lastname, email for the involved
	 *       <code>User</code> model entity.
	 * 
	 * @return A <code>User</code>.
	 */
	@JsonView(UserView.ProfileView.class)
	@GetMapping("/users/profile/{id}")
	@ResponseBody
	public User getUserById_forProfilePage(@PathVariable("id") int userId) {
		return iUserService.getUserById(userId);
	}

	/**
	 * A <code>GetMapping</code> method on the <code>/users/amount</code> URI with
	 * an user id as <code>PathVariable</code>. It calls the
	 * <code>IUserService</code> methods <code>getUserById(int userId)</code> and
	 * returns the <code>User</code> model entity whose id is the one passed in
	 * parameter.
	 * 
	 * @frontCall <code>users.service.ts</code> : <code>getUserWalletById(userId: number)</code>
	 * 
	 * @view Display id, amount for the involved <code>User</code> model entity.
	 * 
	 * @return A <code>User</code>.
	 */
	@JsonView(UserView.UserAmountView.class)
	@GetMapping("/users/amount/{id}")
	@ResponseBody
	public User getUserById_forHomePage(@PathVariable("id") int userId) {
		return iUserService.getUserById(userId);
	}

	/**
	 * A <code>PostMapping</code> method on the <code>/login</code> URI with a DTO,
	 * consisting of the active user email and non-encrypted password, as
	 * <code>RequestBody</code>. It calls the eponymous <code>IUserService</code>
	 * method and returns the <code>User</code> added with status code 201. If the
	 * result is null, it returns status code 204.
	 * 
	 * @frontCall <code>auth.service.ts</code> : <code>login(authValue: AuthValue)</code>
	 * 
	 * @view Display id, firstname, lastname, amount for the involved
	 *       <code>User</code> model entities.
	 * 
	 * @throws <code>BAD_REQUEST</code> if the user concerned doesn't exist or the
	 * password doesn't fit to its encrypted version.
	 * 
	 * @return A <code>User</code> and a status code.
	 */
	@JsonView(UserView.LoginView.class)
	@PostMapping("/login")
	@ResponseBody
	public ResponseEntity<User> postUserByEmailAndPassword(@RequestBody UserLoginDto userLoginDto) throws Exception {
		User loggedUser = iUserService.postUserByEmailAndPassword(userLoginDto);

		if (loggedUser == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<User>(loggedUser, HttpStatus.OK);
		}
	}

	/**
	 * A <code>PostMapping</code> method on the <code>/user</code> URI with a User
	 * model entity as <code>RequestBody</code>. It calls the eponymous
	 * <code>IUserService</code> method and returns the <code>User</code> added with
	 * status code 201. If the result is null, it returns status code 204.
	 * 
	 * @frontCall <code>new-account.service.ts</code> : <code>createNewAccount(newUserValue: NewUserValue)</code>
	 * 
	 * @view Display id, amount for the involved <code>User</code> model entities.
	 * 
	 * @throws <code>BAD_REQUEST</code> if the user concerned email already exist in
	 * the user table.
	 * 
	 * @return A <code>User</code> and a status code.
	 */
	@JsonView(UserView.UserAmountView.class)
	@PostMapping("/user")
	@ResponseBody
	public ResponseEntity<User> addUser(@RequestBody User user) throws Exception {
		User createdUser = iUserService.addUser(user);

		if (createdUser == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<User>(createdUser, HttpStatus.CREATED);
		}
	}

	/**
	 * A <code>PutMapping</code> method on the <code>/users/{id}/profile</code> URI
	 * with an user id as <code>PathVariables</code> and a DTO, consisting of the
	 * active user firstname, lastname, email, non-encrypted password, as
	 * <code>RequestBody</code>. It calls the eponymous <code>IUserService</code>
	 * method and returns an <code>Integer</code> used by the front end to determine
	 * the success of the request, with status code 200. If the result is null, it
	 * returns status code 204.
	 * 
	 * @frontCall <code>profile.service.ts</code> : <code>updateProfile(userId: number, formValue: ProfileValue)</code>
	 * 
	 * @throws <code>BAD_REQUEST</code> if the user concerned doesn't exist.
	 * 
	 * @return An <code>Integer</code> and a status code.
	 */
	@PutMapping("/users/{id}/profile")
	@ResponseBody
	public ResponseEntity<Integer> updateProfile(@PathVariable("id") int userId, @RequestBody UserProfileDto userProfileDto) throws Exception {
		Integer updatedUser = iUserService.updateProfile(userId, userProfileDto);

		if (updatedUser == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<Integer>(HttpStatus.OK);
		}
	}

	/**
	 * A <code>PutMapping</code> method on the <code>/users/{id}/active</code> URI
	 * with an user id as <code>PathVariables</code> and a boolean as
	 * <code>RequestBody</code>. It calls the eponymous <code>IUserService</code>
	 * method and returns an <code>Integer</code> used by the front end to determine
	 * the success of the request, with status code 200. If the result is null, it
	 * returns status code 204.
	 * 
	 * @frontCall <code>profile.service.ts</code> : <code>disableUserById(userId: number)</code>
	 * 
	 * @throws <code>BAD_REQUEST</code> if the user concerned doesn't exist.
	 * 
	 * @return An <code>Integer</code> and a status code.
	 */
	@PutMapping("/users/{id}/active")
	@ResponseBody
	public ResponseEntity<Integer> updateActive(@PathVariable("id") int userId, @RequestBody boolean isActive) throws Exception {
		Integer updatedUser = iUserService.updateActive(userId, isActive);

		if (updatedUser == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<Integer>(HttpStatus.OK);
		}
	}
}