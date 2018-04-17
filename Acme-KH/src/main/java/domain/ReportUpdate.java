
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class ReportUpdate extends DomainEntity {

	private String			content;
	private Date			date;

	//relations

	private Administrator	administrator;
	private GameMaster		gameMaster;


	@NotBlank
	public String getContent() {
		return this.content;
	}

	public void setContent(final String content) {
		this.content = content;
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
	 * es null si el creador es un gm
	 */
	@Valid
	@ManyToOne(optional = true)
	public Administrator getAdministrator() {
		return this.administrator;
	}

	public void setAdministrator(final Administrator administrator) {
		this.administrator = administrator;
	}
	/**
	 * 
	 * es null si el creador es un admin
	 */
	@Valid
	@ManyToOne(optional = true)
	public GameMaster getGameMaster() {
		return this.gameMaster;
	}

	public void setGameMaster(final GameMaster gameMaster) {
		this.gameMaster = gameMaster;
	}

	@Transient
	public Actor getCreator() {
		Actor a = this.getGameMaster();
		if (a == null)
			a = this.getAdministrator();
		return a;
	}
}
