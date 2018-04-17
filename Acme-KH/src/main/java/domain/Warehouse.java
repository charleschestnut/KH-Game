
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.validation.Valid;

import org.hibernate.validator.constraints.Range;

@Entity
@Access(AccessType.PROPERTY)
public class Warehouse extends Building {

	private Materials	materialsSlots;
	private Integer		troopSlots;
	private Integer		gummiSlots;
	private Double		extraSlotsPerLvl;


	/**
	 * 
	 * Devuelve cuanto aumenta los materiales que se pueden tener
	 */
	@Valid
	public Materials getMaterialsSlots() {
		return this.materialsSlots;
	}

	public void setMaterialsSlots(final Materials materialsSlots) {
		this.materialsSlots = materialsSlots;
	}
	/**
	 * 
	 * Cuantas tropas puede almacenar
	 */
	@Range(min = 0)
	public Integer getTroopSlots() {
		return this.troopSlots;
	}

	public void setTroopSlots(final Integer troopSlots) {
		this.troopSlots = troopSlots;
	}
	/**
	 * 
	 * Cuantas naves puede almacenar
	 */
	@Range(min = 0)
	public Integer getGummiSlots() {
		return this.gummiSlots;
	}

	public void setGummiSlots(final Integer gummiSlots) {
		this.gummiSlots = gummiSlots;
	}
	@Range(min = 0, max = 1)
	public Double getExtraSlotsPerLvl() {
		return this.extraSlotsPerLvl;
	}

	public void setExtraSlotsPerLvl(final Double extraSlotsPerLvl) {
		this.extraSlotsPerLvl = extraSlotsPerLvl;
	}
	/**
	 * 
	 * 
	 * 
	 * @return Cuantos materiales puede almacenar en total el edificio de nivel "lvl"
	 */
	@Transient
	public Materials getTotalMaterials(final Integer lvl) {
		final Materials res = new Materials();

		final Integer munny = this.getMaterialsSlots().getMunny();
		final Integer mythril = this.getMaterialsSlots().getMytrhil();
		final Integer coal = this.getMaterialsSlots().getGummiCoal();

		res.setMunny((int) (munny + this.extraSlotsPerLvl * munny * (lvl - 1)));
		res.setMytrhil((int) (mythril + this.extraSlotsPerLvl * mythril * (lvl - 1)));
		res.setGummiCoal((int) (coal + this.extraSlotsPerLvl * coal * (lvl - 1)));

		return res;

	}
	/**
	 * 
	 * @return Cuantas tropas puede almacenar en total el edificio de nivel "lvl"
	 */
	@Transient
	public Integer getTotalTroopSlots(final Integer lvl) {

		final Integer res = (int) (this.troopSlots + this.troopSlots * (lvl - 1) * this.extraSlotsPerLvl);

		return res;

	}
	/**
	 * 
	 * @return Cuantas tropas puede almacenar en total el edificio de nivel "lvl"
	 */
	@Transient
	public Integer getTotalGummiSlots(final Integer lvl) {

		final Integer res = (int) (this.gummiSlots + this.gummiSlots * (lvl - 1) * this.extraSlotsPerLvl);

		return res;

	}

}
