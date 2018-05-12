
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
public class Organization extends DomainEntity {

	private String	name;
	private String	description;
	private Date	creationDate;


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
	
	
	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(final Date creationDate) {
		this.creationDate = creationDate;
	}

}
