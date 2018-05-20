
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PrizeRepository;
import domain.KeybladeWielder;
import domain.Materials;
import domain.Prize;

@Service
@Transactional
public class PrizeService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private PrizeRepository			PrizeRepository;
	@Autowired
	private ActorService			actorService;
	@Autowired
	private KeybladeWielderService	keybladeWielderService;
	@Autowired
	private BuiltService			builtService;
	@Autowired
	private ConfigurationService	configurationService;


	// CRUD methods

	public Prize create() {
		Prize prize;

		prize = new Prize();
		prize.setDate(new Date(System.currentTimeMillis() + 30 * 24 * 60 * 60 * 1000));

		return prize;
	}

	public Prize save(final Prize Prize) {
		Assert.notNull(Prize);

		Prize saved;

		saved = this.PrizeRepository.save(Prize);

		return saved;
	}

	public Prize findOne(final int PrizeId) {
		Assert.notNull(PrizeId);

		Prize Prize;

		Prize = this.PrizeRepository.findOne(PrizeId);

		return Prize;
	}

	public Collection<Prize> findAll() {
		Collection<Prize> Prizes;

		Prizes = this.PrizeRepository.findAll();

		return Prizes;
	}

	public void delete(final Prize Prize) {
		Assert.notNull(Prize);

		this.PrizeRepository.delete(Prize);
	}

	public void open(final Prize prize) {
		final KeybladeWielder player = (KeybladeWielder) this.actorService.findByPrincipal();
		Assert.isTrue(prize.getKeybladeWielder().equals(player), "error.message.owner");

		if (prize.getDate().before(new Date(System.currentTimeMillis())))
			this.PrizeRepository.delete(prize);
		else {

			final Materials max = this.builtService.maxMaterials();
			final Materials old = player.getMaterials();
			final Materials news = old.add(prize.getMaterials());
			final Materials sinExceso = news.removeExcess(max);

			player.setMaterials(sinExceso);
			this.keybladeWielderService.save(player);

			this.PrizeRepository.delete(prize);
		}

	}

	public void createDailyPrizeForPrincipal() {
		final Prize p = this.create();

		p.setKeybladeWielder((KeybladeWielder) this.actorService.findByPrincipal());
		p.setMaterials(this.configurationService.getConfiguration().getDailyMaterials());
		p.setDescription("prize.daily.defaultDescription");

		this.PrizeRepository.save(p);

	}
	// Other methods

	public Collection<Prize> getMyPrizes() {
		final Integer playerId = this.actorService.findByPrincipal().getId();

		final Collection<Prize> trash = this.PrizeRepository.getTrashPrizeFromKeybladeWielder(playerId);

		for (final Prize p : trash)
			this.PrizeRepository.delete(p.getId());

		return this.PrizeRepository.getPrizeFromKeybladeWielder(playerId);

	}
}
