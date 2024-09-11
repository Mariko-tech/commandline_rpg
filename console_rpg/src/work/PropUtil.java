package work;

import java.io.FileReader;
import java.util.Properties;

public class PropUtil {
	// プロパティファイルパス
	private static final String GANE_PROPERTIES = "c:\\config\\game.properties";

	// キー定数
	public static final String KEY_MODE = "mode"; // 処理モード
	public static final String KEY_CSV_OUTPUT_PATH = "csvOutputPath"; // CSV出力先パス
	public static final String KEY_DB_URL = "dbUrl"; // DB接続情報
	public static final String KEY_DB_USER = "dbUser"; // DB接続情報
	public static final String KEY_DB_PASS = "dbPass"; // DB接続情報

	public static final String NAME = "hero_name";
	public static final String HP = "hero_hp";
	public static final String MP = "hero_mp";
	public static final String POWER = "hero_power";
	public static final String DEFENSE = "hero_defense";
	public static final String SPECIAL_POWER = "hero_special_power";

	public static final String ACTION_ATTACK = "action_attack";
	public static final String ACTION_SPECIAL_ATTACK = "action_special_attack";
	public static final String ACTION_WAIT = "action_wait";

	/*
	 * 受け取ったキーに対応するプロパティ値を返します。
	 * キーに対応するプロパティ値がない場合は空文字を返します。
	 */

	public static String readProperty(String key) {
		String value = "";
		try (FileReader fr = new FileReader(PropUtil.GANE_PROPERTIES);) {
			Properties p = new Properties();
			p.load(fr);
			value = p.getProperty(key);
		} catch (Exception e) {
			System.out.println("プロパティ情報の取得に失敗しました");
			e.printStackTrace();
		}

		return value;
	}
}
