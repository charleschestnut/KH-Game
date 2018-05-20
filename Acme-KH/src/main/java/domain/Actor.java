
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

import security.UserAccount;

@Entity
@Access(AccessType.PROPERTY)
@Table(uniqueConstraints = {
	@UniqueConstraint(columnNames = "nickname")
})
public class Actor extends DomainEntity {

	private String	name;
	private String	surname;
	private String	nickname;
	private String	email;
	private String	phone;
	private String	avatar;
	private Boolean	hasConfirmedTerms;
	private Date	confirmMoment;


	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}
	@NotBlank
	public String getSurname() {
		return this.surname;
	}

	public void setSurname(final String surname) {
		this.surname = surname;
	}

	@NotBlank
	@Column(unique = true)
	public String getNickname() {
		return this.nickname;
	}

	public void setNickname(final String nickname) {
		this.nickname = nickname;
	}
	@NotBlank
	@Email
	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	@Pattern(regexp = "(^\\+?\\d+)?")
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(final String phone) {
		this.phone = phone;
	}

	@NotNull
	@AssertTrue
	public Boolean getHasConfirmedTerms() {
		return this.hasConfirmedTerms;
	}

	public void setHasConfirmedTerms(Boolean hasConfirmedTerms) {
		this.hasConfirmedTerms = hasConfirmedTerms;
	}

	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getConfirmMoment() {
		return this.confirmMoment;
	}

	public void setConfirmMoment(final Date confirmMoment) {
		this.confirmMoment = confirmMoment;
	}

	@URL
	@Pattern(regexp = ".+.(jpg|jpeg|gif|png)", message = "(jpg, jpeg, gif, png)")
	public String getAvatar() {
		return this.avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}


	// RELATIONSHIPS ----------------------------------------------------------

	private UserAccount	userAccount;


	@NotNull
	@Valid
	@OneToOne(cascade = CascadeType.ALL, optional = false)
	public UserAccount getUserAccount() {
		return this.userAccount;
	}

	public void setUserAccount(UserAccount userAccount) {
		this.userAccount = userAccount;
	}

	// TRANSIENTS ----------------------------------------------------------
	@Transient
	public String getActorTypeName() {
		if (this instanceof KeybladeWielder)
			return "KEYBLADEWIELDER";
		else if (this instanceof Administrator)
			return "ADMINISTRATOR";
		else if (this instanceof GameMaster)
			return "GAME MASTER";
		else if (this instanceof ContentManager)
			return "CONTENT MANAGER";
		else
			return "UNKNOW";
	}

}
