
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.Range;

@Entity
@Access(AccessType.PROPERTY)
public class Defense extends Building {

	private Integer	defense;
	private Double	extraDefensePerLvl;


	@Range(min = 0)
	public Integer getDefense() {
		return this.defense;
	}

	public void setDefense(final Integer defense) {
		this.defense = defense;
	}
	@Range(min = 0, max = 1)
	public Double getExtraDefensePerLvl() {
		return this.extraDefensePerLvl;
	}

	public void setExtraDefensePerLvl(final Double extraDefensePerLvl) {
		this.extraDefensePerLvl = extraDefensePerLvl;
	}
	/**
	 * 
	 * <b> Recibe: </b>El nivel del edificio al que se le quiere calcular la defensa <br>
	 * 
	 * @return La defensa total de este edificio de nivel "lvl"
	 */
	@Transient
	public Integer getTotalDefense(final Integer lvl) {

		final Integer res = (int) (this.defense + this.defense * (lvl - 1) * this.extraDefensePerLvl);

		return res;

	}

}