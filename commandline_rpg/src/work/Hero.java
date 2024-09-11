package work;

public class Hero extends Character {
	
	public static boolean escapeSuccess = false;
	
	//コンストラクタ
	public Hero(String name, int hp, int mp, int power, int defence, int special_power) {
		super(name, hp, mp, power, defence, special_power);
		this.name = PropUtil.readProperty(PropUtil.NAME);
		this.hp = Integer.parseInt(PropUtil.readProperty(PropUtil.HP));
		this.mp = Integer.parseInt(PropUtil.readProperty(PropUtil.MP));
		this.power = Integer.parseInt(PropUtil.readProperty(PropUtil.POWER));
		this.defense = Integer.parseInt(PropUtil.readProperty(PropUtil.DEFENSE));
		this.special_power = Integer.parseInt(PropUtil.readProperty(PropUtil.SPECIAL_POWER));
	}

	//逃げるメソッド
	public boolean escape() {
		double d = Math.random();
		escapeSuccess = d < 0.2;
		return escapeSuccess;
	}
}
