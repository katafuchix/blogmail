import java.sql.*;

public class MysqlTest {
  public static void main(String[] args) {
    try {
      // ドライバクラスをロード
      Class.forName("org.gjt.mm.mysql.Driver");
      // データベースへ接続
      String url = "jdbc:mysql:///nucleus?useUnicode=true&characterEncoding=SJIS";
	  //String url = "jdbc:MySQL://localhost:3306/nucleus?useUnicode=true&characterEncoding=SJIS&user=root&password=mysql";

      //Connection con = DriverManager.getConnection(url);

		Connection con = DriverManager.getConnection(
		    url, "root", "mysql");

      // ステートメントオブジェクトを生成
      Statement stmt = con.createStatement();

      // 全ての行を検索するSQL文を作成
      //sql = "SELECT * FROM HELLO_WORLD_TABLE";
	String sql = "SELECT * FROM nucleus_item ";
      // クエリーを実行して結果セットを取得
      ResultSet rs = stmt.executeQuery(sql);
      // 検索された行数分ループ
      while(rs.next()){
 
        String lang = rs.getString("inumber");

        // 表示
        System.out.println(" " + lang + " ");
      }
      // データベースから切断
      stmt.close();
      con.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
