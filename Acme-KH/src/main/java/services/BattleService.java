
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.BattleRepository;
import domain.Battle;
import domain.GummiShip;
import domain.ItemType;
import domain.KeybladeWielder;
import domain.Materials;
import domain.Prize;
import domain.Purchase;
import domain.Recruited;
import domain.Troop;
import form.BattleForm;

@Service
@Transactional
public class BattleService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private BattleRepository		battleRepository;
	@Autowired
	private ActorService			actorService;
	@Autowired
	private TroopService			troopService;
	@Autowired
	private GummiShipService		gummiShipService;
	@Autowired
	private RecruitedService		recruitedService;
	@Autowired
	private BuiltService			builtService;
	@Autowired
	private KeybladeWielderService	keybladeWielderService;
	@Autowired
	private ConfigurationService	configurationService;
	@Autowired
	private PrizeService			prizeService;
	@Autowired
	private PurchaseService			purchaseService;
	@Autowired
	private ShieldService			shieldService;


	// CRUD methods

	public Battle create() {
		Battle Battle;

		Battle = new Battle();

		return Battle;
	}

	public Battle save(final Battle Battle) {
		Assert.notNull(Battle);

		Battle saved;

		saved = this.battleRepository.save(Battle);

		return saved;
	}

	public Battle findOne(final int BattleId) {
		Assert.notNull(BattleId);

		Battle Battle;

		Battle = this.battleRepository.findOne(BattleId);

		return Battle;
	}

	public Collection<Battle> findAll() {
		Collection<Battle> Battles;

		Battles = this.battleRepository.findAll();

		return Battles;
	}

	public void delete(final Battle Battle) {
		Assert.notNull(Battle);

		this.battleRepository.delete(Battle);
	}

	public Battle fight(final BattleForm bat) {
		System.out.println("Se llama a figth");
		System.out.println(bat.getEnemy());
		final KeybladeWielder atacante = (KeybladeWielder) this.actorService.findByPrincipal();
		final KeybladeWielder defensor = (KeybladeWielder) this.actorService.findByUserAccountUsername(bat.getEnemy());
		final Collection<Integer> ta = bat.getTroops();
		final ArrayList<Integer> tropasAtacante = new ArrayList<>();
		final Collection<Troop> troops = this.troopService.findAll();
		final Collection<GummiShip> gummiShips = this.gummiShipService.findAll();
		Collection<Recruited> attackRc = new ArrayList<>();
		final Materials materialesNaves = new Materials();
		materialesNaves.setGummiCoal(0);
		materialesNaves.setMunny(0);
		materialesNaves.setMytrhil(0);
		//int defenseId = this.actorService.findByUserAccountUsername(bat.getEnemy()).getId();
		attackRc = this.recruitedService.getAllRecruited(atacante.getId());
		final int saltosGalaxia = ((atacante.getWorldCoordinates().getZ() % 5) - (defensor.getWorldCoordinates().getZ()) % 5);
		//Aqui falta cuanto combustible va a costar cada salto de 5 galacias
		final int combustible = saltosGalaxia * 10;
		boolean todoCero = true;
		//Pnemos en una arrayList, las tropas que el atacante envia
		for (final Integer ra : ta)
			if (ra == null) {
				tropasAtacante.add(0);
			} else {
				if (ra != 0) {
					todoCero = false;
				}
				tropasAtacante.add(ra);
			}
		System.out.println("Tropas atacantes: " + bat.getTroops());
		System.out.println("Tropas atacantes1: " + tropasAtacante);

		//--------------------------------Assert tropas de mas en ataque---------------------------------------------------
		this.enviaMasTropas(bat);
		//-----------------------------Assert tropas envidadas superan sloots----------------------------------------------
		this.MasTropasQueCapacidad(bat);
		//-----------------------------Combustible necesario supera el actual----------------------------------------------
		Assert.isTrue(atacante.getMaterials().getGummiCoal() > combustible, "message.error.exceedsGummiCoal");
		//-----------------------------------Defensor tiene un escudo-------------------------------------------------------

		if (defensor.getShield() != null) {
			Long days = (long) defensor.getShield().getDuration();
			Date expiration = new Date(System.currentTimeMillis() - days * 1000);
			if (!defensor.getShield().getDate().after(expiration)) {
				this.shieldService.delete(defensor.getShield());
			} else {
				Assert.isTrue(false, "message.error.defenderShield");
			}
		}
		//-----------------------------------Atacante tiene un escudo-------------------------------------------------------
		if (atacante.getShield() != null) {
			this.shieldService.delete(atacante.getShield());
		}
		//-----------------------------------Enemigo de la misma faccion-----------------------------------------------------
		if (defensor.getFaction().equals(atacante.getFaction())) {
			Assert.isTrue(false, "message.error.sameFaction");
		}
		//-----------------------------------Todas las tropas van a 0--------------------------------------------------------
		Assert.isTrue(!todoCero, "message.error.notTroops");
		//----------------------------------------Fin de Asserts-------------------------------------------------------------
		//Antes de nada, actualizamos el combustible del atacante
		System.out.println("Pasamos los asserts");
		final Materials nuevoCombustible = atacante.getMaterials();
		nuevoCombustible.setGummiCoal(nuevoCombustible.getGummiCoal() - combustible);
		atacante.setMaterials(nuevoCombustible);
		//fin de nuevo combustible
		//Calculamos ataque y defensa de atacante
		Collection<Recruited> defenseRc = new ArrayList<>();
		//int defenseId = this.actorService.findByUserAccountUsername(bat.getEnemy()).getId();
		defenseRc = this.recruitedService.getAllRecruited(defensor.getId());
		System.out.println("Se llama a figth5");
		System.out.println("Defensas: " + defenseRc);
		System.out.println("Tropas atacantes2: " + tropasAtacante);

		int ataqueAtacante = 0;
		int defensaAtacante = 0;
		int numTropa;
		int i = 0;

		for (final Troop t : troops) {
			System.out.println("iteracion: " + i);
			numTropa = tropasAtacante.get(i);
			System.out.println("num tropas: " + numTropa);
			ataqueAtacante = numTropa * t.getAttack() + ataqueAtacante;
			defensaAtacante = numTropa * t.getDefense() + defensaAtacante;
			System.out.println("ataque: " + ataqueAtacante + " defensa: " + defensaAtacante);
			i++;
			if (i >= troops.size())
				break;
		}
		//A�adimos los pluss, tanto por faccion como por suerte 
		//Extra del ataque y defensa del atacante
		//--Extra por faccion
		final int extraAttackFaccionAtacante = (int) (atacante.getFaction().getExtraAttack() * ataqueAtacante);
		final int extraDefenseFaccionAtacante = (int) (atacante.getFaction().getExtraDefense() * defensaAtacante);

		//ataqueAtacante = ataqueAtacante + extraAttackFaccionAtacante;
		//defensaAtacante = defensaAtacante + extraDefenseFaccionAtacante;
		//--Extra por suerte
		final Double suerteAtacante = (double) (((int) Math.random() * 100) / 100);
		final int extraAttackSuerteAtacante = (int) (suerteAtacante * ataqueAtacante);
		final int extraDefenseSuerteAtacante = (int) (suerteAtacante * defensaAtacante);
		//ataqueAtacante = ataqueAtacante + extraAttackSuerteAtacante;
		//defensaAtacante = defensaAtacante + extraDefenseSuerteAtacante;

		//--Extra por items
		Collection<Purchase> purchaseAtacante = this.purchaseService.activePurchasesByPlayer(atacante.getId());
		int extraAttackBoostAtacante = 0;
		int extraDefenseBoostAtacante = 0;
		for (Purchase p : purchaseAtacante) {
			if (p.getItem().getType().equals(ItemType.ATTACKBOOST)) {
				extraAttackBoostAtacante = (int) (extraAttackBoostAtacante + ataqueAtacante * p.getItem().getExtra());
			} else if (p.getItem().getType().equals(ItemType.DEFENSEBOOST)) {
				extraDefenseBoostAtacante = (int) (extraDefenseBoostAtacante + defensaAtacante * p.getItem().getExtra());
			}
		}
		ataqueAtacante = ataqueAtacante + extraAttackFaccionAtacante + extraAttackSuerteAtacante + extraAttackBoostAtacante;
		defensaAtacante = defensaAtacante + extraDefenseFaccionAtacante + extraDefenseSuerteAtacante + extraDefenseBoostAtacante;
		System.out.println("Ataques y defensas de atacante");

		//Calculamos ataque y defensa de defensor
		int ataqueDefensor = 0;
		int defensaDefensor = 0;
		final int numTropaDef;
		int d = 0;

		System.out.println("Tropas del defensor: " + defenseRc);

		for (final Recruited r : defenseRc) {
			System.out.println("Vamos a calcular ataques del defensor");

			if (r.getTroop() != null) {
				System.out.println(r.getTroop());
				ataqueDefensor = ataqueDefensor + r.getTroop().getAttack();
				defensaDefensor = defensaDefensor + r.getTroop().getDefense();
				d++;
			}
		}
		System.out.println("defensaDefensor1 " + defensaDefensor);
		//Defensas por construcciones

		if (this.builtService.getDefenseByBuildings(defensor.getId()) != null) {
			final int defensaDefensorEdificios = this.builtService.getDefenseByBuildings(defensor.getId());
			System.out.println("defensaDefensor edificios " + defensaDefensorEdificios);

			defensaDefensor = defensaDefensor + defensaDefensorEdificios;
		}
		System.out.println("defensaDefensor2 " + defensaDefensor);
		//Extra del ataque y defensa del defensor
		//---Extra por faccion
		final int extraAttackFaccionDefensor = (int) (defensor.getFaction().getExtraAttack() * ataqueDefensor);
		final int extraDefenseFaccionDefensor = (int) (defensor.getFaction().getExtraDefense() * defensaDefensor);
		//ataqueDefensor = ataqueDefensor + extraAttackFaccionDefensor;
		//defensaDefensor = defensaDefensor + extraDefenseFaccionDefensor;
		//--Extra por suerte
		final Double suerteDefensor = (double) (((int) Math.random() * 100) / 100);
		final int extraAttackSuerteDefensor = (int) (suerteDefensor * ataqueDefensor);
		final int extraDefenseSuerteDefensor = (int) (suerteDefensor * defensaDefensor);
		//ataqueDefensor = ataqueDefensor + extraAttackSuerteDefensor;
		//defensaDefensor = defensaDefensor + extraDefenseSuerteDefensor;

		//--Extra por items
		Collection<Purchase> purchaseDefensor = this.purchaseService.activePurchasesByPlayer(defensor.getId());
		int extraAttackBoostDefensor = 0;
		int extraDefenseBoostDefensor = 0;
		for (Purchase p : purchaseAtacante) {
			if (p.getItem().getType().equals(ItemType.ATTACKBOOST)) {
				extraAttackBoostDefensor = (int) (extraAttackBoostDefensor + ataqueDefensor * p.getItem().getExtra());
			} else if (p.getItem().getType().equals(ItemType.DEFENSEBOOST)) {
				extraDefenseBoostDefensor = (int) (extraDefenseBoostDefensor + defensaDefensor * p.getItem().getExtra());
			}
		}
		ataqueDefensor = ataqueDefensor + extraAttackFaccionDefensor + extraAttackSuerteDefensor + extraAttackBoostDefensor;
		defensaDefensor = defensaDefensor + extraDefenseFaccionDefensor + extraDefenseSuerteDefensor + extraDefenseBoostDefensor;
		//Calculo de diferencias
		final int ataque = ataqueAtacante - defensaDefensor;
		final int defensa = ataqueDefensor - defensaAtacante;
		boolean winner = false;
		boolean looser = true;

		//Gana el que sea mayor
		if (ataque > defensa) {
			System.out.println("ataque > defensa");
			//A�adimos victoria a atacante, derrota a defensor
			atacante.setWins(atacante.getWins() + 1);
			defensor.setLoses(defensor.getLoses() + 1);
			//Guardamos los atacantes y defensores al final, para hacer solo un save
			//Eliminamos todos los recurited del defensor
			for (Recruited des : defenseRc) {
				this.recruitedService.delete(des);
			}
			//Eliminamos las tropas del atacante con un 33% de probabilidad de muerte en caso de que gane el atacante
			System.out.println("Tropas atacantes: " + tropasAtacante);
			for (Recruited arc : attackRc) {
				System.out.println("Entra al for");
				int cont = 0;
				//		if (cont < troops.size()) {
				//		System.out.println("Recruited: " + arc.getTroop().getName());
				if (arc.getTroop() != null) {
					System.out.println("entramos a eliminar tropa");
					for (final Troop tr : troops) {
						System.out.println(tropasAtacante.get(cont) + " Tropas quedan");
						if (tropasAtacante.get(cont) > 0) {
							System.out.println("Entra");
							if (arc.getTroop().getName().equals(tr.getName())) {
								int numero = (int) (Math.random() * 100);
								if (numero < 30) {
									this.recruitedService.delete(arc);
									System.out.println("eliminamos");

									System.out.println("Termina el for 1");
								}
							}
						}
						System.out.println("trops: " + tropasAtacante);

						cont = cont++;
					}
				} else {
					System.out.println("entramos a eliminar gummiship");
					cont = troops.size();
					if (arc.getGummiShip() != null)
						for (final GummiShip gr : gummiShips) {
							if (tropasAtacante.get(cont) > 0) {
								if (arc.getGummiShip().getName().equals(gr.getName())) {
									int numero = (int) (Math.random() * 100);
									if (numero < 30) {
										this.recruitedService.delete(arc);
									}
								}
							}
							cont = cont++;
						}
				}
			}

			winner = true;
			looser = false;
			//Gana el defensor
		} else {
			System.out.println("defensa > ataque");
			//Collection<Recruited> attackRc = new ArrayList<>();
			//int attackId = this.actorService.findByPrincipal().getId();
			//attackRc = this.recruitedService.getAllRecruited(attackId);
			System.out.println("Tropas atacantes: " + tropasAtacante);
			//A�adimos una victoria a defensor, y una derrota a atacante
			defensor.setWins(defensor.getWins() + 1);
			atacante.setLoses(atacante.getLoses() + 1);
			//Eliminamos todas las tropas del atacante
			//A medida que vamos eliminando tropas, vamos a�adiendo los materiales destruidos de las naves, para la recompensa del defensor
			for (final Recruited arc : attackRc) {
				System.out.println("Recruited: " + arc);

				int cont = 0;
				//if (cont < troops.size()) {
				if (arc.getTroop() != null) {
					System.out.println("entramos a eliminar tropa");
					for (final Troop tr : troops) {
						System.out.println(tropasAtacante.get(cont) + " Tropas quedan");
						if (tropasAtacante.get(cont) > 0) {
							System.out.println("Entra");
							if (arc.getTroop().getName().equals(tr.getName())) {
								this.recruitedService.delete(arc);
								final int a = tropasAtacante.get(cont) - 1;
								tropasAtacante.remove(cont);
								tropasAtacante.add(cont, a);
								System.out.println("eliminamos");
								materialesNaves.setGummiCoal(materialesNaves.getGummiCoal() + tr.getCost().getGummiCoal());
								materialesNaves.setMunny(materialesNaves.getMunny() + tr.getCost().getMunny());
								materialesNaves.setMytrhil(materialesNaves.getMytrhil() + tr.getCost().getMytrhil());
								System.out.println("Termina el for 1");

							}
						}
						System.out.println("trops: " + tropasAtacante);

						cont = cont++;
					}
				} else {
					System.out.println("entramos a eliminar gummiship");
					cont = troops.size();
					if (arc.getGummiShip() != null)
						for (final GummiShip gr : gummiShips) {
							if (tropasAtacante.get(cont) > 0) {
								if (arc.getGummiShip().getName().equals(gr.getName())) {
									this.recruitedService.delete(arc);
									final int a = tropasAtacante.get(cont) - 1;
									tropasAtacante.remove(cont);
									tropasAtacante.add(cont, a);
									//tropasAtacante.set(cont, tropasAtacante.get(cont) - 1);
									materialesNaves.setGummiCoal(materialesNaves.getGummiCoal() + gr.getCost().getGummiCoal());
									materialesNaves.setMunny(materialesNaves.getMunny() + gr.getCost().getMunny());
									materialesNaves.setMytrhil(materialesNaves.getMytrhil() + gr.getCost().getMytrhil());

								}
							}
							cont = cont++;
						}
					//cont = cont++;
				}
				System.out.println("Contador: " + cont);
			}

			System.out.println("tropas: " + this.recruitedService.getAllRecruited(atacante.getId()));

			System.out.println("Terminamos de eliminar tropas");

			//Eliminamos las tropas del defensor con un 33% de probabilidad de muerte en caso de que gane el defensor
			for (final Recruited des : defenseRc)
				if ((int) Math.random() * 3 == 0)
					this.recruitedService.delete(des);
		}
		//Creamos los combates a los que les vamos a a�adir los datos
		final Battle battle = this.create();
		final Battle battleDefen = this.create();
		final Materials materiales = new Materials();
		final Prize recompensa = this.prizeService.create();
		if (winner) {

			final Materials mLoss = defensor.getMaterials();
			//Vamos a hacer, con un numero random, que el atacante pueda robar, ente un 25 y un 65 de los materiales max 
			//Que el mismo pueda almacenar, para ello, tengo que ver el max a almacenar
			int gummiCoal = (int) (mLoss.getGummiCoal() * this.configurationService.getConfiguration().getPercentageWinAttacker());
			int munny = (int) (mLoss.getMunny() * this.configurationService.getConfiguration().getPercentageWinAttacker());
			int mytrhil = (int) (mLoss.getMytrhil() * this.configurationService.getConfiguration().getPercentageWinAttacker());
			if (mLoss.getGummiCoal() < gummiCoal)
				gummiCoal = mLoss.getGummiCoal();
			if (mLoss.getMunny() < munny)
				munny = mLoss.getMunny();
			if (mLoss.getMytrhil() < mytrhil)
				mytrhil = mLoss.getMytrhil();
			mLoss.setGummiCoal(mLoss.getGummiCoal() - gummiCoal);
			mLoss.setMunny(mLoss.getMunny() - munny);
			mLoss.setMytrhil(mLoss.getMytrhil() - mytrhil);
			defensor.setMaterials(mLoss);

			materiales.setGummiCoal(gummiCoal);
			materiales.setMunny(munny);
			materiales.setMytrhil(mytrhil);
			//Este metodo seguramente tenga que crear uno oara
			//this.keybladeWielderService.save(defensor); Ya guardamos al final del metodo

			//Creamos un prize si gana atacante
			recompensa.setDescription("Premio recibido al ganar el combate");
			recompensa.setKeybladeWielder(atacante);
			recompensa.setMaterials(materiales);

		} else {
			materiales.setGummiCoal((int) (materialesNaves.getGummiCoal() * this.configurationService.getConfiguration().getPercentageWinDefender()));
			materiales.setMunny((int) (materialesNaves.getMunny() * this.configurationService.getConfiguration().getPercentageWinDefender()));
			materiales.setMytrhil((int) (materialesNaves.getMytrhil() * this.configurationService.getConfiguration().getPercentageWinDefender()));

			recompensa.setDescription("Premio recibido al ganar el combate");
			recompensa.setKeybladeWielder(defensor);
			recompensa.setMaterials(materiales);

		}
		//Guardamos la recompensa
		Prize pz = this.prizeService.save(recompensa);
		System.out.println("El putito premio: " + pz.getKeybladeWielder().getUserAccount().getUsername() + " ja " + pz.getDescription());
		//Guardamos al atacante y defensor, por los distintos cambios ocurridos en ellos
		this.keybladeWielderService.save(atacante);
		this.keybladeWielderService.save(defensor);
		System.out.println("Guardo los keyblader");
		//-----------------------------------------------------------------
		//Si hay guardados mas de 10 combates, borramos el ultimo
		if (this.myBattlesAttack(atacante.getId()).size() > 10) {
			int idMenor = Integer.MAX_VALUE;
			for (final Battle b : this.myBattlesAttack(atacante.getId()))
				if (b.getId() < idMenor)
					idMenor = b.getId();
			this.delete(this.findOne(idMenor));
		}
		System.out.println("Pasamos estro");

		final String balance = this.tropasEnviadas(bat);

		battle.setAttacker(atacante);
		battle.setDeffender(defensor);
		battle.setIsWon(winner);
		battle.setAttackerOwner(true);
		battle.setLuckAttacker(suerteAtacante);
		battle.setLuckDeffender(suerteDefensor);
		battle.setWonOrLostMaterials(materiales);
		battle.setBalance(balance);

		Battle battleSaved = this.save(battle);
		//Si hay guardados mas de 10 combates, borramos el ultimo
		System.out.println("Guardo battle 1");

		if (this.myBattlesDefense(defensor.getId()).size() > 10) {
			int idMenorD = Integer.MAX_VALUE;
			for (final Battle b : this.myBattlesDefense(defensor.getId()))
				if (b.getId() < idMenorD)
					idMenorD = b.getId();
			this.delete(this.findOne(idMenorD));
		}

		battleDefen.setAttacker(atacante);
		battleDefen.setDeffender(defensor);
		battleDefen.setIsWon(looser);
		battleDefen.setAttackerOwner(false);
		battleDefen.setLuckAttacker(suerteAtacante);
		battleDefen.setLuckDeffender(suerteDefensor);
		battleDefen.setWonOrLostMaterials(materiales);
		battleDefen.setBalance(balance);

		//--------------------------------------------------
		this.save(battleDefen);
		System.out.println("Guardo los battle");
		//Activamos un escudo para el defensor
		this.shieldService.saveForAttack(defensor);
		return battleSaved;

	}
	private void enviaMasTropas(final BattleForm bat) {
		final KeybladeWielder atacante = (KeybladeWielder) this.actorService.findByPrincipal();
		final Collection<Integer> ta = bat.getTroops();
		final ArrayList<Integer> tropasAtacante = new ArrayList<>();
		final Collection<Troop> troops = this.troopService.findAll();
		final Collection<GummiShip> gummiShips = this.gummiShipService.findAll();
		Collection<Recruited> attackRc = new ArrayList<>();
		attackRc = this.recruitedService.getAllRecruited(atacante.getId());

		//A partir de aqui, vamos a calcular la cantidad de cada tipo de nave y de tropa para las excepciones
		final ArrayList<Integer> tropasTotalesAtacante = new ArrayList<>();
		final ArrayList<Integer> navesTotalesAtacante = new ArrayList<>();
		//Ponemos ambos arrays a 0, con el tama�o pertinente
		for (final Troop at : troops)
			tropasTotalesAtacante.add(0);
		for (final GummiShip ga : gummiShips)
			navesTotalesAtacante.add(0);

		int indiceT;
		int indiceG;
		for (final Recruited ra : attackRc) {
			indiceT = 0;
			indiceG = 0;
			if (ra.getTroop() != null)
				for (final Troop tp : troops) {
					if (tp.getName().equals(ra.getTroop().getName())) {
						System.out.println("Son iguales: RA: " + tp.getName());
						final int add = tropasTotalesAtacante.get(indiceT) + 1;
						System.out.println("add: " + add);
						tropasTotalesAtacante.remove(indiceT);
						tropasTotalesAtacante.add(indiceT, add);
					}
					indiceT++;

				}
			else {
				int add2 = 0;
				for (final GummiShip gp : gummiShips) {
					if (gp.getName().equals(ra.getGummiShip().getName())) {
						add2 = navesTotalesAtacante.get(indiceG) + 1;
						navesTotalesAtacante.remove(indiceG);
						navesTotalesAtacante.add(indiceG, add2);
					}
					indiceG++;
				}
			}
		}

		for (final Integer n : navesTotalesAtacante)
			tropasTotalesAtacante.add(n);

		System.out.println("Tropas atacante Totales, a comprobar " + tropasTotalesAtacante);
		System.out.println("Naves atacante Totales, a comprobar " + navesTotalesAtacante);
		System.out.println("Pasamos parte 1");
		int nu = 0;
		final int in = 0;
		boolean error = false;
		for (final Integer a : ta) {
			System.out.println("nu: " + nu);
			System.out.println("tama�o trops: " + troops.size());

			//tropasAtacante.add(a);
			/*
			 * if (nu < troops.size()) {
			 * System.out.println("Comprobamos tropa");
			 * if (a > tropasTotalesAtacante.get(nu))
			 * error = true;
			 * nu++;
			 * } else {
			 * System.out.println("Comprobamos naves");
			 * if (a > navesTotalesAtacante.get(in))
			 * error = true;
			 * in++;
			 * }
			 */
			if (a > tropasTotalesAtacante.get(nu))
				error = true;
			nu++;
		}
		System.out.println("Maaaaaaaaaaaaaas tropas?: " + error);

		Assert.isTrue(!error, "message.error.moreTrops");
	}

	private void MasTropasQueCapacidad(final BattleForm bat) {
		Collection<Integer> ta = bat.getTroops();
		ArrayList<Integer> tropasAtacante = new ArrayList<>();
		Collection<Troop> troops = this.troopService.findAll();
		Collection<GummiShip> gummiShips = this.gummiShipService.findAll();
		int numTropas = 0;
		int capacidad = 0;
		boolean error = false;

		for (final Integer ra : ta)
			tropasAtacante.add(ra);
		int index = 0;

		for (final Troop tr : troops) {
			numTropas = numTropas + tropasAtacante.get(index);
			index++;
		}
		for (final GummiShip gr : gummiShips) {
			capacidad = capacidad + gr.getSlots() * tropasAtacante.get(index);
			index++;
		}
		if (capacidad < numTropas) {
			error = true;
		}
		System.out.println("Tropas atacantes3: " + tropasAtacante);

		Assert.isTrue(!error, "message.error.exceedsCapacity");
	}

	private String tropasEnviadas(final BattleForm bat) {
		String tropas = "";
		final Collection<Integer> ta = bat.getTroops();
		final ArrayList<Integer> tropasAtacante = new ArrayList<>();
		final Collection<Troop> troops = this.troopService.findAll();
		final Collection<GummiShip> gummiShips = this.gummiShipService.findAll();

		for (final Integer ra : ta)
			tropasAtacante.add(ra);
		int index = 0;
		for (final Troop tr : troops) {
			if (index < troops.size() - 1)
				tropas = tropas + tr.getName() + ": " + tropasAtacante.get(index) + ",";
			else
				tropas = tropas + tr.getName() + ": " + tropasAtacante.get(index) + "\n";
			index++;
		}
		for (final GummiShip gr : gummiShips) {
			if (index < gummiShips.size() + troops.size() - 1)
				tropas = tropas + gr.getName() + ": " + tropasAtacante.get(index) + ",";
			else
				tropas = tropas + gr.getName() + ": " + tropasAtacante.get(index) + "\n";
			index++;
		}

		return tropas;
	}

	public Collection<Battle> myBattlesAttack(final int actorId) {
		return this.battleRepository.myBattlesAttack(actorId);
	}

	public Collection<Battle> myBattlesDefense(final int actorId) {
		return this.battleRepository.myBattlesDefense(actorId);
	}

	public void flush() {
		this.battleRepository.flush();
	}
}
