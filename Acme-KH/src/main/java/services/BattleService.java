
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
import domain.KeybladeWielder;
import domain.Materials;
import domain.Prize;
import domain.Recruited;
import domain.Troop;
import form.BattleForm;

@Service
@Transactional
public class BattleService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private BattleRepository		BattleRepository;
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


	// CRUD methods

	public Battle create() {
		Battle Battle;

		Battle = new Battle();

		return Battle;
	}

	public Battle save(final Battle Battle) {
		Assert.notNull(Battle);

		Battle saved;

		saved = this.BattleRepository.save(Battle);

		return saved;
	}

	public Battle findOne(final int BattleId) {
		Assert.notNull(BattleId);

		Battle Battle;

		Battle = this.BattleRepository.findOne(BattleId);

		return Battle;
	}

	public Collection<Battle> findAll() {
		Collection<Battle> Battles;

		Battles = this.BattleRepository.findAll();

		return Battles;
	}

	public void delete(final Battle Battle) {
		Assert.notNull(Battle);

		this.BattleRepository.delete(Battle);
	}

	public void fight(final BattleForm bat) {
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
		//int defenseId = this.actorService.findByUserAccountUsername(bat.getEnemy()).getId();
		attackRc = this.recruitedService.getAllRecruited(atacante.getId());
		final int saltosGalaxia = (atacante.getWorldCoordinates().getZ() - defensor.getWorldCoordinates().getZ()) / 5;
		//Aqui falta cuanto combustible va a costar cada salto de 5 galacias
		final int combustible = saltosGalaxia * 10;

		//Pnemos en una arrayList, las tropas que el atacante envia
		for (final Integer ra : ta)
			tropasAtacante.add(ra);
		System.out.println("Tropas atacantes1: " + tropasAtacante);

		//--------------------------------Assert tropas de mas en ataque---------------------------------------------------
		this.enviaMasTropas(bat);
		//-----------------------------Assert tropas envidadas superan sloots----------------------------------------------
		this.MasTropasQueCapacidad(bat);
		//-----------------------------Combustible necesario supera el actual----------------------------------------------
		Assert.isTrue(atacante.getMaterials().getGummiCoal() > combustible);
		//----------------------------------------Fin de Asserts-----------------------------------------------------------
		//Antes de nada, actualizamos el combustible del atacante
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
		//Añadimos los pluss, tanto por faccion como por suerte 
		//Extra del ataque y defensa del atacante
		//--Extra por faccion
		final int extraAttackFaccionAtacante = (int) (atacante.getFaction().getExtraAttack() * ataqueAtacante);
		final int extraDefenseFaccionAtacante = (int) (atacante.getFaction().getExtraDefense() * defensaAtacante);

		ataqueAtacante = ataqueAtacante + extraAttackFaccionAtacante;
		defensaAtacante = defensaAtacante + extraDefenseFaccionAtacante;
		System.out.println("Ataques y defensas de atacante");
		//--Extra por suerte
		final Double suerteAtacante = (double) (((int) Math.random() * 100) / 100);
		final int extraAttackSuerteAtacante = (int) (suerteAtacante * ataqueAtacante);
		final int extraDefenseSuerteAtacante = (int) (suerteAtacante * defensaAtacante);
		ataqueAtacante = ataqueAtacante + extraAttackSuerteAtacante;
		defensaAtacante = defensaAtacante + extraDefenseSuerteAtacante;

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
		ataqueDefensor = ataqueDefensor + extraAttackFaccionDefensor;
		defensaDefensor = defensaDefensor + extraDefenseFaccionDefensor;
		//--Extra por suerte
		final Double suerteDefensor = (double) (((int) Math.random() * 100) / 100);
		final int extraAttackSuerteDefensor = (int) (suerteDefensor * ataqueDefensor);
		final int extraDefenseSuerteDefensor = (int) (suerteDefensor * defensaDefensor);
		ataqueDefensor = ataqueDefensor + extraAttackSuerteDefensor;
		defensaDefensor = defensaDefensor + extraDefenseSuerteDefensor;

		final int ataque = ataqueAtacante - defensaDefensor;
		final int defensa = ataqueDefensor - defensaAtacante;
		boolean winner = false;
		boolean looser = true;

		//Gana el que sea mayor
		if (ataque > defensa) {
			System.out.println("ataque > defensa");
			//Añadimos victoria a atacante, derrota a defensor
			atacante.setWins(atacante.getWins() + 1);
			defensor.setLoses(defensor.getLoses() + 1);
			//Guardamos los atacantes y defensores al final, para hacer solo un save
			for (final Recruited des : defenseRc)
				if (des.getGummiShip() != null)
					this.gummiShipService.delete(des.getGummiShip());
				else
					this.troopService.delete(des.getTroop());
			//Eliminamos las tropas del atacante con un 33% de probabilidad de muerte en caso de que gane el atacante
			System.out.println("Tropas atacantes: " + tropasAtacante);
			for (final Recruited arc : attackRc) {
				System.out.println("Recruited: " + arc);

				int cont = 0;
				if (cont < troops.size()) {
					if (arc.getTroop() != null)
						for (final Troop tr : troops) {
							if (tropasAtacante.get(cont) > 0)
								if (arc.getTroop().getName().equals(tr.getName()))
									if ((int) Math.random() * 3 == 0) {

										this.recruitedService.delete(arc);
										final int a = tropasAtacante.get(cont) - 1;
										tropasAtacante.remove(cont);
										tropasAtacante.add(cont, a);
										System.out.println("eliminamos");
									}
							System.out.println("trops: " + tropasAtacante);

							cont = cont++;
						}
				} else if (arc.getGummiShip() != null)
					for (final GummiShip gr : gummiShips) {
						if (tropasAtacante.get(cont) > 0)
							if (arc.getTroop().getName().equals(gr.getName()))
								if ((int) Math.random() * 3 == 0) {

									this.recruitedService.delete(arc);
									tropasAtacante.set(cont, tropasAtacante.get(cont) - 1);
								}
						cont = cont++;
					}
				cont = cont++;

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
			//Añadimos una victoria a defensor, y una derrota a atacante
			defensor.setWins(defensor.getWins() + 1);
			atacante.setLoses(atacante.getLoses() + 1);
			//Eliminamos todas las tropas del atacante
			//A medida que vamos eliminando tropas, vamos añadiendo los materiales destruidos de las naves, para la recompensa del defensor
			for (final Recruited arc : attackRc) {
				System.out.println("Recruited: " + arc);

				int cont = 0;
				if (cont < troops.size()) {
					if (arc.getTroop() != null)
						for (final Troop tr : troops) {
							if (tropasAtacante.get(cont) > 0)
								if (arc.getTroop().getName().equals(tr.getName())) {
									this.recruitedService.delete(arc);
									final int a = tropasAtacante.get(cont) - 1;
									tropasAtacante.remove(cont);
									tropasAtacante.add(cont, a);
									System.out.println("eliminamos");
									materialesNaves.setGummiCoal(materialesNaves.getGummiCoal() + tr.getCost().getGummiCoal());
									materialesNaves.setMunny(materialesNaves.getMunny() + tr.getCost().getMunny());
									materialesNaves.setMytrhil(materialesNaves.getMytrhil() + tr.getCost().getMytrhil());

								}
							System.out.println("trops: " + tropasAtacante);

							cont = cont++;
						}
				} else {
					if (arc.getGummiShip() != null)
						for (final GummiShip gr : gummiShips) {
							if (tropasAtacante.get(cont) > 0)
								if (arc.getGummiShip().getName().equals(gr.getName())) {
									this.recruitedService.delete(arc);
									tropasAtacante.set(cont, tropasAtacante.get(cont) - 1);
									materialesNaves.setGummiCoal(materialesNaves.getGummiCoal() + gr.getCost().getGummiCoal());
									materialesNaves.setMunny(materialesNaves.getMunny() + gr.getCost().getMunny());
									materialesNaves.setMytrhil(materialesNaves.getMytrhil() + gr.getCost().getMytrhil());

								}
							cont = cont++;
						}
					cont = cont++;
				}
			}

			System.out.println("tropas: " + this.recruitedService.getAllRecruited(atacante.getId()));

			System.out.println("Terminamos de eliminar tropas");

			//Eliminamos las tropas del defensor con un 33% de probabilidad de muerte en caso de que gane el defensor
			for (final Recruited des : defenseRc)
				if ((int) Math.random() * 3 == 0)
					this.recruitedService.delete(des);
		}
		//Creamos los combates a los que les vamos a añadir los datos
		final Battle battle = this.create();
		final Battle battleDefen = this.create();
		final Materials materiales = new Materials();
		final Prize recompensa = new Prize();
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
			recompensa.setDate(new Date(System.currentTimeMillis() - 1000));
			recompensa.setDescription("Premio recibido al ganar el combate");
			recompensa.setKeybladeWielder(atacante);
			recompensa.setMaterials(materiales);

		} else {
			materiales.setGummiCoal((int) (materialesNaves.getGummiCoal() * this.configurationService.getConfiguration().getPercentageWinDefender()));
			materiales.setMunny((int) (materialesNaves.getMunny() * this.configurationService.getConfiguration().getPercentageWinDefender()));
			materiales.setMytrhil((int) (materialesNaves.getMytrhil() * this.configurationService.getConfiguration().getPercentageWinDefender()));

			recompensa.setDate(new Date(System.currentTimeMillis() - 1000));
			recompensa.setDescription("Premio recibido al ganar el combate");
			recompensa.setKeybladeWielder(defensor);
			recompensa.setMaterials(materiales);

		}
		//Guardamos la recompensa
		this.prizeService.save(recompensa);
		//Guardamos al atacante y defensor, por los distintos cambios ocurridos en ellos
		this.keybladeWielderService.save(atacante);
		this.keybladeWielderService.save(defensor);
		//-----------------------------------------------------------------
		//Si hay guardados mas de 10 combates, borramos el ultimo
		if (this.myBattles(atacante.getId()).size() > 10) {
			int idMenor = Integer.MAX_VALUE;
			for (final Battle b : this.myBattles(atacante.getId()))
				if (b.getId() < idMenor)
					idMenor = b.getId();
			this.delete(this.findOne(idMenor));
		}

		final String balance = this.tropasEnviadas(bat);

		battle.setAttacker(atacante);
		battle.setDeffender(defensor);
		battle.setIsWon(winner);
		battle.setAttackerOwner(true);
		battle.setLuckAttacker(suerteAtacante);
		battle.setLuckDeffender(suerteDefensor);
		battle.setWonOrLostMaterials(materiales);
		battle.setBalance(balance);

		this.save(battle);
		//Si hay guardados mas de 10 combates, borramos el ultimo

		if (this.myBattles(defensor.getId()).size() > 10) {
			int idMenorD = Integer.MAX_VALUE;
			for (final Battle b : this.myBattles(defensor.getId()))
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
		//Ponemos ambos arrays a 0, con el tamaño pertinente
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

		int nu = 0;
		int in = 0;
		boolean error = false;
		for (final Integer a : ta) {
			System.out.println("Size: " + troops.size());

			//tropasAtacante.add(a);
			if (nu < troops.size()) {
				if (a > tropasTotalesAtacante.get(nu))
					error = true;
				nu++;
			} else {
				if (a > navesTotalesAtacante.get(in))
					error = true;
				in++;
			}

		}
		System.out.println("Tropas atacantes3: " + tropasAtacante);

		Assert.isTrue(!error, "message.error.moreTrops");
	}

	private void MasTropasQueCapacidad(final BattleForm bat) {
		final Collection<Integer> ta = bat.getTroops();
		final ArrayList<Integer> tropasAtacante = new ArrayList<>();
		final Collection<Troop> troops = this.troopService.findAll();
		final Collection<GummiShip> gummiShips = this.gummiShipService.findAll();
		int numTropas = 0;
		int capacidad = 0;
		final boolean error = false;

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

	public Collection<Battle> myBattles(final int actorId) {
		return this.BattleRepository.myBattles(actorId);
	}
}
