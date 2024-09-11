package work;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileManipulate {
	static String gameStart = "";
	static String gameEnd = "";
	static String gameEndLogName = "";
	static boolean gameClear = false;

	/*CSVファイル取込処理*/
	public static void importMonsterData(String filePath) {
		Monster.deleteMonster();

		try (FileReader fr = new FileReader(filePath);
				BufferedReader br = new BufferedReader(fr);) {

			br.readLine(); // 最初はタイトル行のためスキップ
			String lineData = br.readLine(); // 2行目からがデータ行（取込対象）

			// 取込処理
			Monster monster = new Monster();
			while (lineData != null) {
				System.out.println("【取込データ】" + lineData);
				String[] monsterData = lineData.split(",");
				monster.createMonster(
						Integer.parseInt(monsterData[0]),
						monsterData[1],
						Integer.parseInt(monsterData[2]),
						Integer.parseInt(monsterData[3]),
						Integer.parseInt(monsterData[4]),
						Integer.parseInt(monsterData[5]),
						Integer.parseInt(monsterData[6]));
				lineData = br.readLine();
			}
			System.out.println("取込処理が完了しました。");
		} catch (Exception e) {
			System.out.println("取込処理中にエラーが発生しました。");
			e.printStackTrace();
		}
	}

	/*Start時間の取得*/
	public static void getStartTime() {
		SimpleDateFormat f = new SimpleDateFormat("yyyy年MM月dd日 (E) HH時mm分ss秒");
		Date now = new Date();
		gameStart = f.format(now);
	}

	/*End時間の取得*/
	public static void getEndTime() {
		SimpleDateFormat f = new SimpleDateFormat("yyyy年MM月dd日 (E) HH時mm分ss秒");
		Date now = new Date();
		gameEnd = f.format(now);
	}
	
	/*End時間の取得*/
	public static String getEndTimeLogName() {
		SimpleDateFormat f = new SimpleDateFormat("yyyyMMddHHmmss");
		Date now = new Date();
		return  f.format(now);
	}

	/*GameClear flag*/
	public static void setFlag(boolean gameClear) {
		FileManipulate.gameClear = gameClear;
	}

	/*txtファイル出力処理*/
	public static void exportLog(Hero hero, int currentFloor, int heroFirstHp, int heroLastHp, int heroFirstMp, int heroLastMp) {
		// 書き込みファイルを開く（無い場合は新規作成）
		FileWriter fw = null;
		BufferedWriter bw = null;

        gameEndLogName = getEndTimeLogName();

		try {
			fw = new FileWriter("c:\\result\\" + gameEndLogName + "_ゲーム結果.txt", true);
			// バッファリングフィルタを接続
			bw = new BufferedWriter(fw);

			// 書込み処理
			bw.write("[ゲーム結果]");
			bw.newLine();
			bw.write("ゲーム開始：" + gameStart);
			bw.newLine();
			bw.write("ゲーム終了：" + gameEnd);
			bw.newLine();
			bw.newLine();
			bw.write("結果： " +(gameClear? "GAME CLEAR!!": "GAME OVER..."));
			bw.newLine();
			bw.newLine();

			bw.write("プレイヤー情報：");
			bw.newLine();
			bw.write("（名前）" + hero.getName());
			bw.newLine();
			bw.write("（HP）" + heroFirstHp + "→" + heroLastHp);
			bw.newLine();
			bw.write("（MP）" + heroFirstMp + "→" + heroLastMp);
			bw.newLine();
			bw.write("（攻撃力）" + hero.getPower());
			bw.newLine();
			bw.write("（防御力）" + hero.getDefense());
			bw.newLine();
			bw.write("（必殺技威力）" + hero.getSpecialPower());
			bw.newLine();
			bw.newLine();
			bw.write("進んだ階数：" + currentFloor + "階");
			bw.newLine();

			// 書込み確定処理
			bw.flush();
			System.out.println("");
			System.out.println("ログの出力が完了しました");

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// クローズ処理
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
				}
			}

			// 基本的にはBufferedReaderをクローズすれば
			// FileReaderも連動してクローズされるため、以下の処理は省略することもできる。
			// ただし、BufferedReaderをクローズできない状況でも
			// FileReaderはクローズできる状況も0ではないため、分けた方が
			// より安全な処理になる。
			if (fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
				}
			}
		}
	}
}
