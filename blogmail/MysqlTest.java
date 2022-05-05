import java.sql.*;

public class MysqlTest {
  public static void main(String[] args) {
    try {
      // �h���C�o�N���X�����[�h
      Class.forName("org.gjt.mm.mysql.Driver");
      // �f�[�^�x�[�X�֐ڑ�
      String url = "jdbc:mysql:///nucleus?useUnicode=true&characterEncoding=SJIS";
	  //String url = "jdbc:MySQL://localhost:3306/nucleus?useUnicode=true&characterEncoding=SJIS&user=root&password=mysql";

      //Connection con = DriverManager.getConnection(url);

		Connection con = DriverManager.getConnection(
		    url, "root", "mysql");

      // �X�e�[�g�����g�I�u�W�F�N�g�𐶐�
      Statement stmt = con.createStatement();

      // �S�Ă̍s����������SQL�����쐬
      //sql = "SELECT * FROM HELLO_WORLD_TABLE";
	String sql = "SELECT * FROM nucleus_item ";
      // �N�G���[�����s���Č��ʃZ�b�g���擾
      ResultSet rs = stmt.executeQuery(sql);
      // �������ꂽ�s�������[�v
      while(rs.next()){
 
        String lang = rs.getString("inumber");

        // �\��
        System.out.println(" " + lang + " ");
      }
      // �f�[�^�x�[�X����ؒf
      stmt.close();
      con.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
