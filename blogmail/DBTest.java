import java.sql.*;
import manami.system.*;
import manami.util.*;

public class DBTest {
  public static void main(String[] args) {
    try {

		
		DB db = new DB();


		try{

			db.connect();
		}catch(Exception e){
			Static.Error.out(e.toString());
		}

			db.create();
			String sql = "SELECT * FROM nucleus_item";

			db.select(sql);

			while(db.next()){
			
				System.out.println(" " + db.getString("inumber") + " ");
			}

		try{
			db.close();
			db.disconnect();

		}catch(Exception e){
			Static.Error.out(e.toString());
		}

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
