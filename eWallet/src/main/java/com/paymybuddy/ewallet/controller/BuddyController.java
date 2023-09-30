package com.paymybuddy.ewallet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.annotation.JsonView;
import com.paymybuddy.ewallet.dto.BuddyAddDto;
import com.paymybuddy.ewallet.model.User;
import com.paymybuddy.ewallet.service.IBuddyService;
import com.paymybuddy.ewallet.views.UserView;

/**
 * A class that receives requests made from some of the usual CRUD endpoints and
 * specific URL for the Buddies. The <code>@CrossOrigin</code> annotation limits
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
public class BuddyController {

	@Autowired
	private IBuddyService iBuddyService;

	/**
	 * A <code>GetMapping</code> method on the <code>/mybuddies</code> URI with an
	 * user id as <code>PathVariable</code>. It calls the eponymous
	 * <code>IBuddyService</code> method and returns a list of <code>User</code>
	 * model entities.
	 * 
	 * @frontCall <code>contact.service.ts</code> : <code>getMyBuddies(userId: number)</code>
	 * 
	 * @view Display id, firstname, lastname, email and active attributes of the
	 *       involved <code>User</code> model entities.
	 * 
	 * @return A <code>User</code> list.
	 */
	@JsonView(UserView.BuddyView.class)
	@GetMapping("/mybuddies/{id}")
	public List<User> getBuddiesByUser(@PathVariable("id") int userId) {
		return iBuddyService.getBuddiesByUser(userId);
	}

	/**
	 * A <code>GetMapping</code> method on the <code>/mybuddies/active</code> URI
	 * with an user id as <code>PathVariable</code>. It calls the eponymous
	 * <code>IBuddyService</code> method and returns a list of <code>User</code>
	 * model entities.
	 * 
	 * @frontCall <code>transfer.service.ts</code> : <code>getMyActiveBuddies(userId: number)</code>
	 * 
	 * @view Display id, firstname, lastname, email and active attributes of the
	 *       involved <code>User</code> model entities.
	 * 
	 * @return A <code>User</code> list.
	 */
	@JsonView(UserView.BuddyView.class)
	@GetMapping("/mybuddies/active/{id}")
	public List<User> getActiveBuddiesByUser(@PathVariable("id") int userId) {
		return iBuddyService.getActiveBuddiesByUser(userId);
	}

	/**
	 * A <code>PostMapping</code> method on the <code>/buddy</code> URI with a DTO,
	 * consisting of the active user id and the future buddy user email, as
	 * <code>RequestBody</code>. It calls the eponymous <code>IBuddyService</code>
	 * method and returns the <code>User</code> added with status code 201. If the
	 * result is null, it returns status code 204.
	 * 
	 * @frontCall <code>contact.service.ts</code> : <code>addBuddy(contactValue: ContactValue)</code>
	 * 
	 * @view Display id, firstname, lastname, email and active attributes of the
	 *       involved <code>User</code> model entity.
	 * 
	 * @throws <code>BAD_REQUEST</code> if one of the two users concerned doesn't
	 * exist or if it's the same user.
	 * 
	 * @return A <code>User</code> and a status code.
	 */
	@JsonView(UserView.BuddyView.class)
	@PostMapping("/buddy")
	public ResponseEntity<User> addBuddy(@RequestBody BuddyAddDto buddyAddDto) {
		User myNewBuddy = iBuddyService.addBuddy(buddyAddDto);

		if (myNewBuddy == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<User>(myNewBuddy, HttpStatus.CREATED);
		}
	}

	/**
	 * A <code>DeleteMapping</code> method on the <code>/buddy</code> URI with the
	 * two buddies user id as <code>PathVariables</code>. It calls the eponymous
	 * <code>IBuddyService</code> method and returns nothing.
	 * 
	 * @frontCall <code>contact.service.ts</code> : <code>unBuddy(userId: number, buddyId: number)</code>
	 */
	@DeleteMapping("/buddy/{firstUserId}-{secondUserId}")
	public void deleteBuddy(@PathVariable("firstUserId") int firstUserId, @PathVariable("secondUserId") int secondUserId) {
		iBuddyService.deleteBuddy(firstUserId, secondUserId);
	}
}