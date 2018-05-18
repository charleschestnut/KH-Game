
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 
 * Player
 * 
 */
@Entity
@Access(AccessType.PROPERTY)
public class KeybladeWielder extends Actor {

	private Date		lastConnection;
	private Materials	materials;
	private Integer		wins;
	private Integer		loses;
	private Coordinates	worldCoordinates;
	private String		worldName;

	//relations
	private Faction		faction;
	private Shield		shield;


	/**
	 * Hay que almacenarla para saber si ha recibido los premios hoy o no y tiene que ser actualizada cada vez que logea
	 * <p>
	 * <b>Importante: </b><br>
	 * Cuando un player se crea la cuenta poner la fecha de ayer para que asi reciba los premios de hoy cuando inice sesión
	 * </p>
	 * 
	 * @see "dd/MM/yyyy"
	 * 
	 */
	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getLastConnection() {
		return this.lastConnection;
	}

	public void setLastConnection(final Date lastConnection) {
		this.lastConnection = lastConnection;
	}
	/**
	 * 
	 * Son los materiales que posee el jugador.
	 * <b>Importante: </b><br>
	 * El máximo que podrá almacenar vendra dado por lo que haya en configuración + lo que le permitan sus edificios, si alguna vez se excede el maximo los materiales sobrantes se perderán
	 * </p>
	 */
	@Valid
	public Materials getMaterials() {
		return this.materials;
	}

	public void setMaterials(final Materials materials) {
		this.materials = materials;
	}
	/**
	 * 
	 * Puesto que solo guardaremos unas pocas batallas en base de datos tenemos que ir actualizando los valores de victorias y derrotas en los jugadores
	 */
	@NotNull
	@Range(min = 0)
	public Integer getWins() {
		return this.wins;
	}

	public void setWins(final Integer wins) {
		this.wins = wins;
	}
	/**
	 * 
	 * Puesto que solo guardaremos unas pocas batallas en base de datos tenemos que ir actualizando los valores de victorias y derrotas en los jugadores
	 */
	@NotNull
	@Range(min = 0)
	public Integer getLoses() {
		return this.loses;
	}

	public void setLoses(final Integer loses) {
		this.loses = loses;
	}
	@Valid
	public Coordinates getWorldCoordinates() {
		return this.worldCoordinates;
	}

	public void setWorldCoordinates(final Coordinates worldCoordinates) {
		this.worldCoordinates = worldCoordinates;
	}
	@NotBlank
	public String getWorldName() {
		return this.worldName;
	}

	public void setWorldName(final String worldName) {
		this.worldName = worldName;
	}

	@Valid
	@ManyToOne(optional = false)
	public Faction getFaction() {
		return this.faction;
	}

	public void setFaction(final Faction faction) {
		this.faction = faction;
	}

	@Valid
	@OneToOne(optional = true)
	public Shield getShield() {
		return this.shield;
	}

	public void setShield(final Shield shield) {
		this.shield = shield;
	}

}
