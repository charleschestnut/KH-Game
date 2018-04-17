
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

@Entity
@Access(AccessType.PROPERTY)
@Table(uniqueConstraints = {
	@UniqueConstraint(columnNames = "name")
})
public class GummiShip extends DomainEntity {

	private Integer		slots;
	private Materials	cost;
	private Integer		timeToRecruit;
	private Integer		recruiterRequiredLvl;
	private String		name;

	//relations
	private Recruiter	recruiter;


	@Range(min = 0)
	public Integer getSlots() {
		return this.slots;
	}

	public void setSlots(final Integer slots) {
		this.slots = slots;
	}

	@Valid
	public Materials getCost() {
		return this.cost;
	}

	public void setCost(final Materials cost) {
		this.cost = cost;
	}
	@Range(min = 0)
	public Integer getTimeToRecruit() {
		return this.timeToRecruit;
	}

	public void setTimeToRecruit(final Integer timeToRecruit) {
		this.timeToRecruit = timeToRecruit;
	}
	/**
	 * 
	 * @return el nivel que necesita el recruiter para poder crear esta nave
	 */
	@Range(min = 0)
	public Integer getRecruiterRequiredLvl() {
		return this.recruiterRequiredLvl;
	}

	public void setRecruiterRequiredLvl(final Integer recruiterRequiredLvl) {
		this.recruiterRequiredLvl = recruiterRequiredLvl;
	}
	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@ManyToOne(optional = false)
	public Recruiter getRecruiter() {
		return this.recruiter;
	}

	public void setRecruiter(final Recruiter recruiter) {
		this.recruiter = recruiter;
	}

}
