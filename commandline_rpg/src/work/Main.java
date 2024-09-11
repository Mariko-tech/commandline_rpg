package work;

public class Main {

	public static void main(String[] args) throws Exception {
		//初期設定
		int currentFloor = 1;
		Hero hero = new Hero("", 0, 0, 0, 0, 0);
		boolean gameFlag = false;
		
		int heroFirstHp = hero.getHp();
		int heroLastHp;
		int heroFirstMp = hero.getMp();
		int heroLastMp;


		//ゲーム開始
		System.out.println("★☆スッキリタワー☆★\n"
				+ "1:ゲームスタート\n"
				+ "2:モンスターデータ取込\n");
		FileManipulate.getStartTime();
		int startChoice = new java.util.Scanner(System.in).nextInt();

		//ゲームスタート
		if (startChoice == 1) {
			while (hero.getHp() > 0) {
				Monster monster = Monster.fetchMonster(currentFloor);
				if (monster != null) {
					System.out.println(
							"[ " + hero.getName() + "のHP：" + hero.getHp() + " " + hero.getName() + "MP：" + hero.getMp()
									+ " ]");
					System.out.println();
					System.out.println("【" + currentFloor + "階に到着した】");
					System.out.println(
							monster.getName() + "[ HP：" + monster.getHp() + " MP：" + monster.getMp() + " ]が現れた！");
					boolean heroEscaped = false;
					while (hero.getHp() > 0 && monster.getHp() > 0) {

						//Hero攻撃ターン
						System.out.print("コマンドを入力（1:攻撃 2:必殺技(消費MP:1) 3:逃げる) >>");
						int UserMoveChoice = new java.util.Scanner(System.in).nextInt();
						switch (UserMoveChoice) {
						case 1:
							hero.attack(hero, monster);
							break;
						case 2:
							hero.special_attack(hero, monster);
							break;
						case 3:
							heroEscaped = hero.escape();
							if (heroEscaped) {
								System.out.println(hero.getName() + "はうまく逃げ切れた");
								currentFloor++;
								break;
							} else {
								System.out.println(hero.getName() + "は逃げ切れなかった");
							}
						}

						if (heroEscaped == true) {
							break;
						}

						//monster攻撃ターン
						if (monster.getHp() > 0) {
							double attack = Double.parseDouble(PropUtil.readProperty(PropUtil.ACTION_ATTACK)) / 100;
							double special = Double.parseDouble(PropUtil.readProperty(PropUtil.ACTION_SPECIAL_ATTACK))
									/ 100;
							double wait = Double.parseDouble(PropUtil.readProperty(PropUtil.ACTION_WAIT)) / 100;
							int monsterMove = (Math.random() < wait) ? 1 : (Math.random() < special) ? 2 : 3;

							//Monsterの攻撃
							switch (monsterMove) {
							case 1:
								monster.waiting();
								break;
							case 2:
								monster.special_attack(monster, hero);
								break;
							case 3:
								monster.attack(monster, hero);
								break;
							}

							System.out.println(
									"[ " + hero.getName() + "のHP：" + hero.getHp() + " MP：" + hero.getMp() + " ]");
							System.out.println(
									"[ " + monster.getName() + "のHP：" + monster.getHp() + " MP：" + monster.getMp()
											+ " ]");
							System.out.println();

							if (hero.getHp() < 1) {
								System.out.println(hero.getName() + "は死んでしまった…");
								System.out.println("--Game Over--");
								heroLastHp = hero.getHp();
								heroLastMp = hero.getMp();
								FileManipulate.getEndTime();
								FileManipulate.setFlag(gameFlag);
								FileManipulate.exportLog(hero, currentFloor, heroFirstHp, heroLastHp, heroFirstMp, heroLastMp);
								return;
							}
						}

						if (monster.getHp() < 1) {
							System.out.println(monster.getName() + "をやっつけた！");
							currentFloor++;
							break;
						}
					}
				} else {
					System.out.println("おめでとう！　クリアです！！");
					System.out.println("--END--");
					gameFlag = true;
					FileManipulate.setFlag(true);
					heroLastHp = hero.getHp();
					heroLastMp = hero.getMp();
					FileManipulate.getEndTime();
					FileManipulate.exportLog(hero, currentFloor, heroFirstHp, heroLastHp, heroFirstMp, heroLastMp);
					break;

				}
			}
		}

		//モンスターデータ取り込み
		else {
			System.out.println("取込むCSVファイルのパスを入力してください。");
			String filePath = new java.util.Scanner(System.in).nextLine();
			FileManipulate.importMonsterData(filePath);
			return;

		}
	}
}
