
package form;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import domain.Built;
import domain.GummiShip;
import domain.Troop;

public class RecruitedForm {

	private Built		built;
	private Troop		troop;
	private GummiShip	gummiship;


	@Valid
	@NotNull
	public Built getBuilt() {
		return this.built;
	}

	public void setBuilt(final Built built) {
		this.built = built;
	}
	@Valid
	public Troop getTroop() {
		return this.troop;
	}

	public void setTroop(final Troop troop) {
		this.troop = troop;
	}
	@Valid
	public GummiShip getGummiship() {
		return this.gummiship;
	}

	public void setGummiship(final GummiShip gummiship) {
		this.gummiship = gummiship;
	}

}
