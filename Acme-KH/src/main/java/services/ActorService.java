
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
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

	public Actor create(String AuthorityString) {
		Actor actor;
		UserAccount userAccount;
		Collection<Authority> authorities;
		Authority auth;

		actor = new Actor();
		userAccount = new UserAccount();
		authorities = new ArrayList<>();
		auth = new Authority();

		auth.setAuthority(AuthorityString);
		authorities.add(auth);
		userAccount.setAuthorities(authorities);
		actor.setUserAccount(userAccount);
		actor.setHasConfirmedTerms(false);

		return actor;
	}

	public Actor findOne(int actorId) {
		Assert.notNull(actorId);

		Actor actor;

		actor = this.actorRepository.findOne(actorId);

		Assert.notNull(actor);

		return actor;
	}

	public Actor save(Actor actor) {
		Assert.notNull(actor, "error.message.null");
		if (actor.getId() != 0)
			Assert.isTrue(actor.getUserAccount().equals(LoginService.getPrincipal()), "error.message.owner");
		Actor res;

		res = this.actorRepository.save(actor);
		return res;
	}

	public Actor saveFromCreate(Actor user) {
		Actor result;
		System.out.println("Username: " + user.getUserAccount().getUsername());
		System.out.println("Nickname: " + user.getNickname());
		Assert.isTrue(this.findByUserAccountUsername(user.getUserAccount().getUsername()) == null, "error.message.duplicatedUsername");
		Assert.isTrue(this.findByNickname(user.getNickname()) == null, "error.message.duplicatedNickname");

		Md5PasswordEncoder encoder;
		String hash;

		encoder = new Md5PasswordEncoder();
		hash = encoder.encodePassword(user.getUserAccount().getPassword(), null);

		user.getUserAccount().setPassword(hash);

		user.setConfirmMoment(new Date(System.currentTimeMillis() - 1000));

		result = this.save(user);
		return result;
	}
	public Actor reconstruct(final Actor user, final BindingResult binding) {
		UserAccount userAccount;
		Collection<Authority> authorities;
		Authority auth;

		userAccount = user.getUserAccount();
		authorities = new ArrayList<>();
		auth = new Authority();

		auth.setAuthority("PLAYER");
		authorities.add(auth);
		userAccount.setAuthorities(authorities);
		user.setUserAccount(userAccount);

		user.setId(0);
		user.setVersion(0);

		this.validator.validate(user, binding);

		return user;
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
		Assert.notNull(username, "error.message.null");
		Assert.isTrue(username.length() >= 3 && username.length() <= 32);
		Actor actor = this.actorRepository.findByUserAccountUsername(username);
		Assert.notNull(actor, "error.message.notexist");

		return actor;
	}

	public Actor findByNickname(String nickname) {
		Assert.notNull(nickname);
		Actor actor = this.actorRepository.findByNickname(nickname);

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
