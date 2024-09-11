package work;

public class Character {
	protected String name;
	protected int hp;
	protected int mp;
	protected int power;
	protected int defense;
	protected int special_power;

	public Character(String name, int hp, int mp, int power, int defense, int special_power) {
		this.name = name;
		this.hp = hp;
		this.mp = mp;
		this.power = power;
		this.defense = defense;
		this.special_power = special_power;
	}

	public Character() {
	}

	public String getName() {
		return name;
	}

	public int getHp() {
		return hp;
	}
	
	public void setHp(int hp) {
		this.hp = Math.max(hp, 0);
	}
	
	public int getMp() {
		return mp;
	}

	public void setMp(int mp) {
		this.mp = Math.max(mp, 0);
	}

	public int getPower() {
		return power;
	}

	public int getDefense() {
		return defense;
	}

	public int getSpecialPower() {
		return special_power;
	}
	//攻撃メソッド
	public void attack(Character character_attack, Character character_defense) {
		System.out.println(character_attack.getName() + "の攻撃！");
		int damage = Math.max(character_attack.getPower() - character_defense.getDefense(), 0);
		System.out.println(character_defense.getName() + "に" + damage + "のダメージ！");
		character_defense.setHp(character_defense.getHp() - damage);
	}

	//必殺技メソッド
	public void special_attack(Character character_attack, Character character_defense) {
		System.out.println(character_attack.getName() + "は必殺技を放った！");
		if (character_attack.getMp() <= 0) {
			System.out.println("しかし技は失敗した…");
		} else {
			System.out.println(character_defense.getName() + "に" + (character_attack.getSpecialPower()) + "のダメージ！");
			character_defense.setHp(character_defense.getHp() - character_attack.getSpecialPower());
		}
		character_attack.setMp(character_attack.getMp() - 1);
	}
}
