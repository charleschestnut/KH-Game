
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

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
	 * Cuando un player se crea la cuenta poner la fecha de ayer para que asi reciba los premios de hoy cuando inice sesi�n
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
	 * El m�ximo que podr� almacenar vendra dado por lo que haya en configuraci�n + lo que le permitan sus edificios, si alguna vez se excede el maximo los materiales sobrantes se perder�n
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

	/*
	 * Este metodo te devuelve un Integer en modulo 34 (para elegir unas de las 34 imagenes de mundos) segun tu md5(nickname + worldName + worldCoordinate)
	 * de este hash, cogemos todos los numeros y los ponemos en modulo 34, obteniendo asi un Integer aleatorio segun valores estaticos de la cuenta del Keyblade
	 */
	@Transient
	public Long getWorldImage() {
		Md5PasswordEncoder encoder;
		String hash;

		encoder = new Md5PasswordEncoder();
		hash = encoder.encodePassword(this.getNickname() + this.getWorldName() + this.getWorldCoordinates().getX() + this.getWorldCoordinates().getY() + this.getWorldCoordinates().getZ(), null);
		hash = hash.replaceAll("\\D+", "");
		Long worldImageId = Long.parseLong(hash);
		worldImageId = worldImageId % 34 + 1;

		return worldImageId;
	}
}
