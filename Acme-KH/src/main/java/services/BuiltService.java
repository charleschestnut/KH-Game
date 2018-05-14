
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.BuiltRepository;
import domain.Built;

@Service
@Transactional
public class BuiltService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private BuiltRepository	BuiltRepository;


	// CRUD methods

	public Built create() {
		Built built;

		built = new Built();

		return built;
	}

	public Built save(final Built Built) {
		Assert.notNull(Built);

		Built saved;

		saved = this.BuiltRepository.save(Built);

		return saved;
	}

	public Built findOne(final int BuiltId) {
		Assert.notNull(BuiltId);

		Built Built;

		Built = this.BuiltRepository.findOne(BuiltId);

		return Built;
	}

	public Collection<Built> findAll() {
		Collection<Built> Builts;

		Builts = this.BuiltRepository.findAll();

		return Builts;
	}

	public void delete(final Built Built) {
		Assert.notNull(Built);

		this.BuiltRepository.delete(Built);
	}

}
