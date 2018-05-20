
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.KeybladeWielderRepository;
import domain.KeybladeWielder;
import domain.Organization;

@Service
@Transactional
public class KeybladeWielderService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private KeybladeWielderRepository	KeybladeWielderRepository;

	@Autowired
	private OrganizationService			organizationService;

	@Autowired
	private ActorService				actorService;


	// CRUD methods

	public KeybladeWielder create() {
		KeybladeWielder KeybladeWielder;

		KeybladeWielder = new KeybladeWielder();

		return KeybladeWielder;
	}

	public KeybladeWielder save(final KeybladeWielder KeybladeWielder) {
		Assert.notNull(KeybladeWielder);

		KeybladeWielder saved;

		saved = this.KeybladeWielderRepository.save(KeybladeWielder);

		return saved;
	}

	public KeybladeWielder findOne(final int KeybladeWielderId) {
		Assert.notNull(KeybladeWielderId);

		KeybladeWielder KeybladeWielder;

		KeybladeWielder = this.KeybladeWielderRepository.findOne(KeybladeWielderId);

		return KeybladeWielder;
	}

	public Collection<KeybladeWielder> findAll() {
		Collection<KeybladeWielder> KeybladeWielders;

		KeybladeWielders = this.KeybladeWielderRepository.findAll();

		return KeybladeWielders;
	}

	public void delete(final KeybladeWielder KeybladeWielder) {
		Assert.notNull(KeybladeWielder);

		this.KeybladeWielderRepository.delete(KeybladeWielder);
	}

	public Collection<KeybladeWielder> findMembersOfOrganization(final int organizationId) {
		final Collection<KeybladeWielder> members = this.KeybladeWielderRepository.findMembersOfOrganization(organizationId);
		return members;
	}

	public Boolean getTieneOrganizacion() {
		Boolean res = true;
		final KeybladeWielder actual = (KeybladeWielder) this.actorService.findByPrincipal();
		final Organization org = this.organizationService.findOrganizationByPlayer(actual.getId());
		if (org == null)
			res = false;
		return res;
	}

	//Dashboard

	public Collection<Double> ratioOfUserPerFaction() {
		return this.KeybladeWielderRepository.ratioOfUserPerFaction();
	}
	public Collection<KeybladeWielder> getTopWinsPlayers() {
		return this.KeybladeWielderRepository.getTopWinsPlayers();
	}

	public Collection<KeybladeWielder> getTopWinRatioPlayers() {
		return this.KeybladeWielderRepository.getTopWinRatioPlayers();
	}

	public Collection<KeybladeWielder> getTopMunnyPlayers() {
		return this.KeybladeWielderRepository.getTopMunnyPlayers();
	}

	public Collection<KeybladeWielder> getTopMytrhilPlayers() {
		return this.KeybladeWielderRepository.getTopMythrilPlayers();
	}

	public Double avgOfWinRatio() {
		return this.KeybladeWielderRepository.avgOfWinRatio();
	}
}
