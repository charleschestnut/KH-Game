
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.Range;

@Entity
@Access(AccessType.PROPERTY)
public class Configuration extends DomainEntity {

	private Materials	dailyMaterials;
	private Materials	baseMaterials;
	private Integer		orgMessages;
	private Double		percentageWinAttacker;
	private Double		percentageWinDefender;
	private Integer		lostLvlsDeffender;
	private Integer		worldSlots;


	/**
	 * 
	 * @return los materiales que seran otorgado a los jugadores por iniciar sesi�n cada dia
	 */
	@AttributeOverrides({
		@AttributeOverride(name = "munny", column = @Column(name = "dailyMunny")), @AttributeOverride(name = "mytrhil", column = @Column(name = "dailyMytrhil")), @AttributeOverride(name = "gummiCoal", column = @Column(name = "dailyGummiCoal"))

	})
	public Materials getDailyMaterials() {
		return this.dailyMaterials;
	}

	public void setDailyMaterials(final Materials dailyMaterials) {
		this.dailyMaterials = dailyMaterials;
	}
	/**
	 * 
	 * @return los materiales que puede conservar un jugador por defecto, a este valor se le ha de sumar el valor de cada jugador seg�n sus edifcios.
	 */
	@AttributeOverrides({
		@AttributeOverride(name = "munny", column = @Column(name = "baseMunny")), @AttributeOverride(name = "mytrhil", column = @Column(name = "baseMytrhil")), @AttributeOverride(name = "gummiCoal", column = @Column(name = "baseGummiCoal"))

	})
	public Materials getBaseMaterials() {
		return this.baseMaterials;
	}

	public void setBaseMaterials(final Materials baseMaterials) {
		this.baseMaterials = baseMaterials;
	}
	/**
	 * 
	 * @return n�mero de mensajes que ser�n almacenados en una organizaci�n
	 */
	@Range(min = 5)
	public Integer getOrgMessages() {
		return this.orgMessages;
	}

	public void setOrgMessages(final Integer orgMessages) {
		this.orgMessages = orgMessages;
	}
	/**
	 * 
	 * @return porcentaje de los recursos que se llevara el atacante del defensor si gana la batalla
	 */
	@Range(min = 0, max = 1)
	public Double getPercentageWinAttacker() {
		return this.percentageWinAttacker;
	}

	public void setPercentageWinAttacker(final Double percentageWinAttacker) {
		this.percentageWinAttacker = percentageWinAttacker;
	}
	/**
	 * 
	 * @return porcentaje de los recursos que se llevara el defensor de los recursos que envie el atacante si gana la batalla
	 */
	@Range(min = 0, max = 1)
	public Double getPercentageWinDefender() {
		return this.percentageWinDefender;
	}

	public void setPercentageWinDefender(final Double percentageWinDefender) {
		this.percentageWinDefender = percentageWinDefender;
	}
	/**
	 * 
	 * @return niveles que bajaran los edificios del defensor si pierde una batalla
	 */
	@Range(min = 0)
	public Integer getLostLvlsDeffender() {
		return this.lostLvlsDeffender;
	}

	public void setLostLvlsDeffender(final Integer lostLvlsDeffender) {
		this.lostLvlsDeffender = lostLvlsDeffender;
	}
	/**
	 * 
	 * @return los huecos que tiene un mundo para edificios
	 * @see esta propiedad solo puede ir aumentando
	 */
	@Range(min = 0)
	public Integer getWorldSlots() {
		return this.worldSlots;
	}

	public void setWorldSlots(final Integer worldSlots) {
		this.worldSlots = worldSlots;
	}

}
