
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.RecruiterRepository;
import security.LoginService;
import domain.ContentManager;
import domain.GummiShip;
import domain.Recruiter;
import domain.Troop;

@Service
@Transactional
public class RecruiterService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private RecruiterRepository	RecruiterRepository;
	@Autowired
	private BuildingService		buildingService;
	@Autowired
	private Validator			validator;
	@Autowired
	private ActorService		actorService;
	@Autowired
	private TroopService		troopService;
	@Autowired
	private GummiShipService	gummiShipService;


	// CRUD methods

	public Recruiter create() {
		Recruiter recruiter;

		recruiter = (domain.Recruiter) this.buildingService.create();
		recruiter.setGummiShips(new ArrayList<GummiShip>());
		recruiter.setTroops(new ArrayList<Troop>());

		return recruiter;
	}

	public Recruiter save(final Recruiter Recruiter) {
		Assert.notNull(Recruiter);
		Assert.isTrue(Recruiter.getContentManager().getUserAccount().equals(LoginService.getPrincipal()), "error.message.building.creator");

		Recruiter saved;

		saved = this.RecruiterRepository.save(Recruiter);

		return saved;
	}

	public Recruiter findOne(final int RecruiterId) {
		Assert.notNull(RecruiterId);

		Recruiter Recruiter;

		Recruiter = this.RecruiterRepository.findOne(RecruiterId);

		return Recruiter;
	}

	public Collection<Recruiter> findAll() {
		Collection<Recruiter> Recruiters;

		Recruiters = this.RecruiterRepository.findAll();

		return Recruiters;
	}

	public void addTroop(final Recruiter r, Troop t) {
		Assert.isTrue(!r.getIsFinal(), "error.message.building.final");
		Assert.isTrue(r.getContentManager().getUserAccount().equals(LoginService.getPrincipal()), "error.message.building.creator");
		Assert.isTrue(r.equals(t.getRecruiter()), "error.message.recruiter.addTroop");

		t = this.troopService.save(t);

		final Collection<Troop> troops = r.getTroops();
		troops.add(t);

		this.RecruiterRepository.save(r);

	}

	public void addGummiShip(final Recruiter r, GummiShip gs) {
		Assert.isTrue(!r.getIsFinal(), "error.message.building.final");
		Assert.isTrue(r.getContentManager().getUserAccount().equals(LoginService.getPrincipal()), "error.message.building.creator");
		Assert.isTrue(r.equals(gs.getRecruiter()), "error.message.recruiter.addGummiShip");

		gs = this.gummiShipService.save(gs);

		final Collection<GummiShip> gummies = r.getGummiShips();
		gummies.add(gs);

		this.RecruiterRepository.save(r);

	}

	public Recruiter reconstruct(final Recruiter recruiter, final BindingResult binding) {
		final Recruiter original = this.findOne(recruiter.getId());

		if (recruiter.getId() == 0) {
			recruiter.setGummiShips(new ArrayList<GummiShip>());
			recruiter.setTroops(new ArrayList<Troop>());
			recruiter.setContentManager((ContentManager) this.actorService.findByPrincipal());
			recruiter.setIsFinal(false);

		} else {
			recruiter.setGummiShips(original.getGummiShips());
			recruiter.setTroops(original.getTroops());
			recruiter.setContentManager(original.getContentManager());
			recruiter.setIsFinal(original.getIsFinal());
		}

		this.validator.validate(recruiter, binding);

		return recruiter;
	}
}
