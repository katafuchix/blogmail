/*
********************************************************************************
* �V�X�e���F �T���P�C�X�|�[�c�X�J�E�g�L�����o���@�����L���O�W�v�N���X 		   *
*==============================================================================*
* �N���X�@�FWeeklyRanking            �b���W���[���FWeeklyRanking.java          *
* �T�v�@�@�F���C�ɓ���ɓo�^���ꂽ�^�����g���J�E���g���ăe�[�u���ɏ�������     *
*			�ؗj�̌ߑO�S���ɓ�����											   *
* �쐬�@�@�F2006/06/28 K.Katafuchi(affinity)                                   *
*------------------------------------------------------------------------------*
* �X�V�@�@�F																   *
********************************************************************************
*/

/*-- �C���|�[�g --------------------------------------------------------------*/
import javax.mail.*;
import javax.mail.internet.*;
import java.io.*;
import java.util.*;
import manami.system.*;
import manami.util.*;

/*-- �N���X��` --------------------------------------------------------------*/
public class WeeklyRanking {


  public static void main(String args[]) {


		DB db  		= new DB();
		DB db2		= new DB();
	
		DateTime dt = new DateTime();
		String time = dt.getString();

		String time_yyyy = time.substring(0,4);
		String time_mm   = time.substring(4,6);
		String time_dd   = time.substring(6,8);
	
		EasyDate ed = new EasyDate();
		String start_date = Integer.toString(ed.getDays(-7)); //��T�ԑO�̓��t���擾����

		String start_yyyy = start_date.substring(0,4);
		String start_mm   = start_date.substring(4,6);
		String start_dd   = start_date.substring(6,8);

		//�ߋ���T�ԕ��̃f�[�^���擾����
		String sql = " SELECT f.idol_id, m.idol_name, count( * ) AS cnt "
					+ " FROM tbl_idol_master m, tbl_favorite f "
					+ " WHERE m.idol_id = f.idol_id "
					+ " AND f.timestamp >= '" + start_yyyy + "-" + start_mm + "-" + start_dd + "'"
					+ " AND f.timestamp < '" + time_yyyy + "-" + time_mm + "-" + time_dd + "'"
					+ " GROUP BY f.idol_id, m.idol_name "
					+ " ORDER BY cnt DESC ";

		try{

			db.connect();
			db.create();

			db2.connect();
				
			db.select( sql );
			while( db.next() ){

					String _idol_id = db.getString(1);
					String _cnt = db.getString(3);					

					String isql = " insert into tbl_week_rank  "
                     				+ "( date, idol_id, count )"
                     				+ "values('" + time + "','" + _idol_id +  "','" + _cnt + "')";
			

					try{
						db2.create();
						db2.prepare( isql );
						db2.insert();
						db2.close();
						db2.commit();

					}catch( Exception e2){
						Static.Error.out( e2.toString() );
						db2.rollback();
					}

			}

		}catch( Exception e){
			Static.Error.out( e.toString() );
		}



		try{

			db.close();
			db.disconnect();

			db2.close();
			db2.disconnect();

		}catch( Exception e){
			Static.Error.out( e.toString() );
		}
	

  }


}



