
package form;

import java.util.Collection;

public class BattleForm {

	private Collection<Integer>	troops;
	private String				enemy;


	public Collection<Integer> getTroops() {
		return this.troops;
	}
	public void setTroops(Collection<Integer> troops) {
		this.troops = troops;
	}

	public String getEnemy() {
		return this.enemy;
	}
	public void setEnemy(String enemy) {
		this.enemy = enemy;
	}
}
