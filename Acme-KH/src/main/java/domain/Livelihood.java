
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.validation.Valid;

@Entity
@Access(AccessType.PROPERTY)
public class Livelihood extends Building {

	private Materials	materials;
	private Double		extraMaterialsPerLvl;
	private Integer		timeToRecollect;
	private Double		LessTimePerLvl;


	/**
	 * 
	 * Materiales que recolecta este edificio una vez acabe el tiempo
	 */
	@Valid
	public Materials getMaterials() {
		return this.materials;
	}

	public void setMaterialsPerMinute(final Materials materials) {
		this.materials = materials;
	}
	/**
	 * 
	 * Devuelve el extra de materiales que dará por cada nivel, <b>SIEMPRE</b> se aplica al de nivel 1.
	 */
	public Double getExtraMaterialsPerLvl() {
		return this.extraMaterialsPerLvl;
	}

	public void setExtraMaterialsPerLvl(final Double extraMaterialsPerLvl) {
		this.extraMaterialsPerLvl = extraMaterialsPerLvl;
	}
	/**
	 * 
	 * Devuelve el tiempo <b>EN MINUTOS</b> que hay que esperar para recolectar todos los materiales que consiga el edificio
	 */
	public Integer getTimeToRecollect() {
		return this.timeToRecollect;
	}

	public void setTimeToRecollect(final Integer timeToRecollect) {
		this.timeToRecollect = timeToRecollect;
	}
	/**
	 * 
	 * Devuelve cuanto se reducirá el tiempo de recolección cada vez que se sube el nivel
	 */
	public Double getLessTimePerLvl() {
		return this.LessTimePerLvl;
	}

	public void setLessTimePerLvl(final Double lessTimePerLvl) {
		this.LessTimePerLvl = lessTimePerLvl;
	}
	/**
	 * 
	 * <b> Recibe: </b>El nivel del edificio al que se le quiere calcular los materiales <br>
	 * 
	 * @return Los materiales totales este edificio de nivel "lvl" recolecta
	 */
	@Transient
	public Materials getTotalMaterials(final Integer lvl) {
		final Materials res = new Materials();

		final Integer munny = this.getMaterials().getMunny();
		final Integer mythril = this.getMaterials().getMytrhil();
		final Integer coal = this.getMaterials().getGummiCoal();

		res.setMunny((int) (munny + this.extraMaterialsPerLvl * munny * (lvl - 1)));
		res.setMytrhil((int) (mythril + this.extraMaterialsPerLvl * mythril * (lvl - 1)));
		res.setGummiCoal((int) (coal + this.extraMaterialsPerLvl * coal * (lvl - 1)));

		return res;

	}
	/**
	 * 
	 * <b> Recibe: </b>El nivel del edificio al que se le quiere calcular el tiempo de recolección <br>
	 * 
	 * @return El tiempo total de este edificio de nivel "lvl" tarda en recolectar
	 */
	@Transient
	public Integer getTotalTime(final Integer lvl) {

		Integer res = (int) (this.getTimeToConstruct() - (this.getTimeToConstruct() * (lvl - 1) * this.getLessTimePerLvl()));
		if (res < 0)
			res = 0;

		return res;
	}
}
