
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ShieldRepository;
import domain.Item;
import domain.Shield;

@Service
@Transactional
public class ShieldService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ShieldRepository	ShieldRepository;
	@Autowired
	private ItemService			itemService;
	@Autowired
	private ActorService		actorService;


	// CRUD methods

	public Shield create() {
		Shield Shield;

		Shield = new Shield();

		return Shield;
	}

	public Shield save(final Item item) {
		Assert.notNull(item);
		//Assert.isTrue(this.itemService.myItems(this.actorService.findByPrincipal().getId()).contains(item));
		final Shield res = this.create();
		res.setName(item.getName());
		res.setDate(new Date(System.currentTimeMillis() - 1000));
		final String duration = item.getName().replace("Escudo ", "");
		final int dur = Integer.parseInt(duration);
		res.setDuration(dur * 60);
		Shield saved;

		saved = this.ShieldRepository.save(res);

		return saved;
	}

	public Shield findOne(final int ShieldId) {
		Assert.notNull(ShieldId);

		Shield Shield;

		Shield = this.ShieldRepository.findOne(ShieldId);

		return Shield;
	}

	public Collection<Shield> findAll() {
		Collection<Shield> Shields;

		Shields = this.ShieldRepository.findAll();

		return Shields;
	}

	public void delete(final Shield Shield) {
		Assert.notNull(Shield);

		this.ShieldRepository.delete(Shield);
	}

}
