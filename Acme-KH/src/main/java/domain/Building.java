
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Building extends DomainEntity {

	private String			name;
	private String			description;
	private String			photo;
	private Integer			maxLvl;
	private Boolean			isFinal;
	private Materials		cost;
	private Double			extraCostPerLvl;
	private Integer			timeToConstruct;

	//relations

	private ContentManager	contentManager;


	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}
	@NotBlank
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}
	@URL
	public String getPhoto() {
		return this.photo;
	}

	public void setPhoto(final String photo) {
		this.photo = photo;
	}
	/**
	 * 
	 * Nivel máximo que puede subirse el edificio
	 */
	@NotNull
	@Range(min = 1)
	public Integer getMaxLvl() {
		return this.maxLvl;
	}

	public void setMaxLvl(final Integer maxLvl) {
		this.maxLvl = maxLvl;
	}
	/**
	 * 
	 * Ya esta guardado para usarse (true), se puede editar (false)
	 */
	@NotNull
	public Boolean getIsFinal() {
		return this.isFinal;
	}

	public void setIsFinal(final Boolean isFinal) {
		this.isFinal = isFinal;
	}
	/**
	 * 
	 * Devuelve los materiales que son necesarios para adquirir el edificio
	 */
	@Valid
	public Materials getCost() {
		return this.cost;
	}

	public void setCost(final Materials cost) {
		this.cost = cost;
	}
	/**
	 * 
	 * Cuanto más cuesta mejorar un edificio<b> SIEMPRE </b>se aplica al de nivel 1
	 */
	@Range(min = 0, max = 1)
	public Double getExtraCostPerLvl() {
		return this.extraCostPerLvl;
	}

	public void setExtraCostPerLvl(final Double extraCostPerLvl) {
		this.extraCostPerLvl = extraCostPerLvl;
	}
	/**
	 * 
	 * EN MINUTOS
	 */
	@Range(min = 0)
	public Integer getTimeToConstruct() {
		return this.timeToConstruct;
	}

	public void setTimeToConstruct(final Integer timeToConstruct) {
		this.timeToConstruct = timeToConstruct;
	}
	@Valid
	@ManyToOne(optional = false)
	public ContentManager getContentManager() {
		return this.contentManager;
	}

	public void setContentManager(final ContentManager contentManager) {
		this.contentManager = contentManager;
	}
	@Transient
	public Materials getTotalMaterials(final Integer currentLvL) {
		final Materials res = new Materials();

		final Integer munny = this.getCost().getMunny();
		final Integer mythril = this.getCost().getMytrhil();
		final Integer coal = this.getCost().getGummiCoal();

		res.setMunny((int) (munny + this.extraCostPerLvl * munny * (currentLvL - 1)));
		res.setMytrhil((int) (mythril + this.extraCostPerLvl * mythril * (currentLvL - 1)));
		res.setGummiCoal((int) (coal + this.extraCostPerLvl * coal * (currentLvL - 1)));

		return res;

	}

}
