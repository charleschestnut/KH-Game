package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import security.Authority;

import domain.Actor;
import domain.Building;
import domain.Built;
import domain.KeybladeWielder;
import domain.Materials;
import domain.Prize;

@Service
@Transactional
public class PromptService {
	
	@Autowired
	private ActorService			actorService;
	@Autowired
	private PrizeService			prizeService;
	@Autowired
	private BuildingService			buildingService;
	@Autowired
	private BuiltService			builtService;

	public String interpret(String command) {
		String res = "";
		
//		set player2 -mn 999 -mt 23 -gc 23
		
		if (command.equals("help")) {
			res = "Command prompt made to send prizes, buildings or troops to any player.  <br/>" +
					"To send a prize, use set [username] followed by one of the next options:  <br/><br/>" +
					"-mn [munnyQuantity]  <br/>" +
					"-mt [mythrilQuantity]  <br/>" +
					"-mn [gummyCoalQuantity]  <br/>"+
					"-dt [dd/MM/yyyy]  > By default, date is set as current date plus one day<br/><br/>" +
					"To send a building, use set [username] followed by next option:  <br/><br/>" +
					"-b [buildingName]  <br/> > You can list the available building by executing 'list buildings' command" ;
		} else if (command.trim().startsWith("set") && (command.indexOf("-mn")>0 || command.indexOf("-mt")>0 || command.indexOf("-gc")>0)) {
			Integer munny = 0;
			Integer mythril = 0;
			Integer gummiCoal = 0;
			String username;
			Boolean valid;
			Actor player;
			
			username = command.split("\\s+")[1];
			player = actorService.findByUserAccountUsername(username);
			valid = true;
			
			if (player != null && new ArrayList<>(player.getUserAccount().getAuthorities()).get(0).getAuthority().equals(Authority.PLAYER)) {
				if (command.indexOf("-mn") < 0) {
					munny = 0;
				} else if(command.split("-mn")[1].split("\\s+")[1].trim().matches("^[1-9]\\d*$")){
					munny = Integer.valueOf(command.split("-mn")[1].split("\\s+")[1].trim());
				}else{
					valid = false;
				}

				if (command.indexOf("-mt") < 0) {
					mythril = 0;
				} else if(command.split("-mt")[1].split("\\s+")[1].trim().matches("^[1-9]\\d*$")){
					mythril = Integer.valueOf(command.split("-mt")[1].split("\\s+")[1].trim());
				}else{
					valid = false;
				}

				if (command.indexOf("-gc") < 0) {
					gummiCoal = 0;
				} else if(command.split("-gc")[1].trim().matches("^[1-9]\\d*$")){
					gummiCoal = Integer.valueOf(command.split("-gc")[1].split("\\s+")[1].trim());
				}else{
					valid = false;
				}
				
				if(valid){
					Prize prize, saved;
					Materials materials;
					KeybladeWielder keybladeWielder;
					
					prize = prizeService.create();
					materials = new Materials();
					keybladeWielder = (KeybladeWielder) player;
					
					materials.setGummiCoal(gummiCoal);
					materials.setMunny(munny);
					materials.setMytrhil(mythril);
					prize.setDescription("Prize from gamemaster");
					prize.setMaterials(materials);
					prize.setKeybladeWielder(keybladeWielder);
					
					if(command.indexOf("-dt")>=0){
						prize.setDate((LocalDate.parse(command.split("-dt")[1].trim())).toDate());
					}else{
						prize.setDate(new Date(System.currentTimeMillis()+86400000));
					}
					
					saved = prizeService.save(prize);
//					prizeService.sendPrize(saved);
					res = "Prize sent";
				}else{
					res = "Command not understood";
				}
			}else{
				res = "Player doesn't exist";
			}
		}else if(command.trim().equals("list buildings")){ 
				res = buildingService.getAvailableBuildingsName().toString();
		}else if(command.trim().startsWith("set") && command.indexOf("-b")>0){ 
			String username;
			Actor player;
			Collection<String> buildingsNames;
			
			username = command.split("\\s+")[1];
			player = actorService.findByUserAccountUsername(username);
			buildingsNames = buildingService.getAvailableBuildingsName();
			
			if (player != null && new ArrayList<>(player.getUserAccount().getAuthorities()).get(0).getAuthority().equals(Authority.PLAYER)) {
				String buildingName;
				
				buildingName = command.split("-b")[1].trim();
				if(buildingsNames.contains(buildingName)){
					Building building;
					Built built;
					
					building = buildingService.getBuildingByName(buildingName);
					built = new Built();
					built.setLvl(0);
					built.setBuilding(building);
					built.setKeybladeWielder((KeybladeWielder) player);
					built.setCreationDate(new Date(System.currentTimeMillis() - 1000));
					
					builtService.saveFromGM(built);
					
					res = "Building sent";
					
				}else{
					res = "Building doesn't exist";
				}
			}else{
				res = "Player doesn't exist";
			}
			
		}else {
			res = "Command not understood";
		}
		
		return res;
	}
}
