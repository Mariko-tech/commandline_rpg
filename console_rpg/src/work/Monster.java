package work;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Monster extends Character {

	private final static String URL = "jdbc:postgresql://localhost:5432/rpg";
	private final static String USER = "postgres";
	private final static String PASSWORD = "test";

	//コンストラクタ
	public Monster() {
		super("", 0, 0, 0, 0, 0);
	}

	public Monster(String name, int hp, int mp, int power, int defense, int special_power) {
		super(name, hp, mp, power, defense, special_power);
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public Monster(int id, String name, int hp, int mp, int power, int defense, int special_power) {
		super(name, hp, mp, power, defense, special_power);
	}

	//モンスター召喚
	public static Monster fetchMonster(int currentFloor) {
		String sql = "SELECT * FROM monster WHERE monster_no = ?;";
		try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement st = con.prepareStatement(sql)) {
			st.setInt(1, currentFloor);
			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				return new Monster(
						rs.getString("name"),
						rs.getInt("hp"),
						rs.getInt("mp"),
						rs.getInt("power"),
						rs.getInt("defense"),
						rs.getInt("special_power"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void deleteMonster() {
		String sql = "TRUNCATE TABLE monster;";
		try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement st = con.prepareStatement(sql);) {

			st.executeUpdate();
			System.out.println("モンスターデータを削除しました");
			
		} catch (Exception e) {
			System.out.println("DBアクセス時にエラーが発生しました。");
			e.printStackTrace();
		}
	}

	public void createMonster(int id,
			String name,
			int hp,
			int mp,
			int power,
			int defense,
			int special_power) {
		/* 1) SQL文の準備 */
		String sql = "INSERT INTO monster (monster_no, name, hp, mp, power, defense, special_power) VALUES(?, ?, ?, ?, ?, ?, ?);";

		/* 2) PostgreSQLへの接続 */
		try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement st = con.prepareStatement(sql);) {

			/* 3) SQL文の?部分を置き換え */
			st.setInt(1, id);
			st.setString(2, name);
			st.setInt(3, hp);
			st.setInt(4, mp);
			st.setInt(5, power);
			st.setInt(6, defense);
			st.setInt(7, special_power);

			/* 4) SQL文の実行 */
			st.executeUpdate();

		} catch (Exception e) {
			System.out.println("DBアクセス時にエラーが発生しました。");
			e.printStackTrace();
		}
	}

	//様子を見るメソッド
	public void waiting() {
		System.out.println(this.getName() + "は様子を見ている");
	}
}
