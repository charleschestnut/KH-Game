
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

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Purchase extends DomainEntity {

	private Date	purchaseDate;
	private Date	activationDate;

	//relations

	private Item	item;


	/**
	 * 
	 * usada para controlar la caducidad
	 */
	@NotNull
	@Past
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getPurchaseDate() {
		return this.purchaseDate;
	}

	public void setPurchaseDate(final Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	/**
	 * 
	 * usada para controlar el tiempo de activación, null si no se ha utilizado aún
	 */
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getActivationDate() {
		return this.activationDate;
	}

	public void setActivationDate(final Date activationDate) {
		this.activationDate = activationDate;
	}
	@Valid
	@ManyToOne(optional = false)
	public Item getItem() {
		return this.item;
	}

	public void setItem(final Item item) {
		this.item = item;
	}

}
