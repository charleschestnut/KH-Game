
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 
 * 
 * 
 * <p>
 * En esta clase se almacenar� cuantas veces ha sido baneado un actor Un jugador solo puede tener un ban activo, es decir, en caso de querer cambiar su ban habr� que cancelar el activo y activar uno nuevo
 * </p>
 * */
@Entity
@Access(AccessType.PROPERTY)
public class Banned extends DomainEntity {

	private Date	banDate;
	private Integer	duration;
	private String	reason;
	private Boolean	isValid;

	//Relations

	private Actor	actor;


	/**
	 * 
	 * @return Fecha en la que comenz� el ban
	 * @see "dd/MM/yyyy HH:mm"
	 */
	@Past
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getBanDate() {
		return this.banDate;
	}

	public void setBanDate(Date banDate) {
		this.banDate = banDate;
	}
	/**
	 * 
	 * @return duraci�n (en horas) de cuanto tiempo estar� baneado (null si es indefinido)
	 */
	@NotNull
	@Range(min = 0)
	public Integer getDuration() {
		return this.duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public String getReason() {
		return this.reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	/**
	 * <p>
	 * Como los baneos no se borran se ha de almacenar si sigue estando activo, principalmente para los indefinidos, aunque se pueda dar el caso de cancelar un baneo temporal
	 * </p>
	 * 
	 * @return Si el baneo sigue activo
	 */
	@NotNull
	public Boolean getIsValid() {
		return this.isValid;
	}

	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}

	//Relations

	@Valid
	@ManyToOne(optional = false)
	public Actor getActor() {
		return this.actor;
	}

	public void setActor(Actor actor) {
		this.actor = actor;
	}

}
