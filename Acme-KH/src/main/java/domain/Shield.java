
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Shield extends DomainEntity {

	private String	name;
	private Date	date;
	private Integer	duration;


	/**
	 * 
	 * En caso de activarse por ser atacado o por empezar el juego el escudo ser� uno predeterminado
	 */
	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}
	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getDate() {
		return this.date;
	}

	public void setDate(final Date date) {
		this.date = date;
	}
	/**
	 * 
	 * EN MINUTOS
	 */
	public Integer getDuration() {
		return this.duration;
	}

	public void setDuration(final Integer duration) {
		this.duration = duration;
	}

}
