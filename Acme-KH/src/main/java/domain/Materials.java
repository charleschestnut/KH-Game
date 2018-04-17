
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;

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

}
