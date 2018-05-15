
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.Range;

@Embeddable
@Access(AccessType.PROPERTY)
public class Materials {

	private Integer	munny;
	private Integer	mytrhil;
	private Integer	gummiCoal;


	@Range(min = 0)
	public Integer getMunny() {
		return this.munny;
	}

	public void setMunny(final Integer munny) {
		this.munny = munny;
	}

	@Range(min = 0)
	public Integer getMytrhil() {
		return this.mytrhil;
	}

	public void setMytrhil(final Integer mytrhil) {
		this.mytrhil = mytrhil;
	}
	@Range(min = 0)
	public Integer getGummiCoal() {
		return this.gummiCoal;
	}

	public void setGummiCoal(final Integer gummiCoal) {
		this.gummiCoal = gummiCoal;
	}

	@Transient
	public Boolean isHigherThan(final Materials materials) {
		Boolean res = false;
		if (this.getGummiCoal() > materials.getGummiCoal() && this.getMunny() > materials.getMunny() && this.getMytrhil() > materials.getMytrhil())
			res = true;
		return res;

	}

	@Transient
	public Materials substract(final Materials materials) {
		final Integer munny = this.getMunny() - materials.getMunny();
		final Integer gummi = this.getGummiCoal() - materials.getGummiCoal();
		final Integer mythril = this.getMytrhil() - materials.getMytrhil();

		final Materials res = new Materials();
		res.setMunny(munny);
		res.setGummiCoal(gummi);
		res.setMytrhil(mythril);

		return res;

	}

	@Override
	public String toString() {
		return "Munny=" + this.munny + ", Mytrhil=" + this.mytrhil + ", Gummi Coal=" + this.gummiCoal;
	}

}
