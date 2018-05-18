
package services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ActorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import form.ActorForm;

@Service
@Transactional
public class ActorService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ActorRepository	actorRepository;

	@Autowired
	private Validator		validator;


	// CRUD methods

	public Actor findOne(int actorId) {
		Assert.notNull(actorId);

		Actor actor;

		actor = this.actorRepository.findOne(actorId);

		Assert.notNull(actor);

		return actor;
	}

	public Actor save(Actor actor) {
		Assert.notNull(actor, "error.message.null");
		Assert.isTrue(actor.getUserAccount().equals(LoginService.getPrincipal()), "error.message.owner");
		Actor res;

		res = this.actorRepository.save(actor);
		return res;
	}

	// Other business methods -------------------------------------------------

	public Actor reconstruct(ActorForm actorForm, BindingResult binding) {
		Actor res;

		Actor original = this.findByUserAccountUsername(actorForm.getUsername());
		Assert.notNull(original, "error.message.null");
		Assert.isTrue(original.getUserAccount().getUsername().equals(LoginService.getPrincipal().getUsername()), "error.message.owner");

		res = original;
		res.setName(actorForm.getName());
		res.setSurname(actorForm.getSurname());
		res.setAvatar(actorForm.getAvatar());
		res.setEmail(actorForm.getEmail());
		res.setPhone(actorForm.getPhone());

		this.validator.validate(res, binding);

		return res;

	}

	public Actor findByPrincipal() {
		UserAccount userAccount;
		Actor actor;

		userAccount = LoginService.getPrincipal();
		actor = this.actorRepository.findByUserAccountId(userAccount.getId());

		return actor;
	}

	public Actor findByUserAccountUsername(String username) {
		Assert.notNull(username);
		Assert.isTrue(username.length() >= 3 && username.length() <= 32);
		Actor actor = this.actorRepository.findByUserAccountUsername(username);
		Assert.notNull(actor);
		return actor;
	}
	public String getPrincipalAuthority() {
		Actor actor;
		String auth;

		try {
			actor = this.findByPrincipal();
			List<Authority> authorities = new ArrayList<>(actor.getUserAccount().getAuthorities());
			auth = authorities.get(0).getAuthority();
		} catch (Throwable o) { //Exception catched when user is not authenticated
			auth = "NONE";
		}

		return auth;
	}

}
