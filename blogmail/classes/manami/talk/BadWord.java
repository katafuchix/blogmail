/*
********************************************************************************
* �V�X�e���@�F ai-land i-mode live BBS for Queen 		                       *
*==============================================================================*
* ���W���[���FBadWord.java                                    			       *
* �N���X���@�FBadWord		                                                   *
* �T�v�@�@�@�F�֎~�p��`�F�b�N		                                           *
* �쐬�@�@�@�F2004/06/26 K.Katafuchi(affinity)                                 *
*------------------------------------------------------------------------------*�@�@�@�F2004/07/20 K.Katafuchi(affinity) X��*							   *
********************************************************************************
*/

/*-- �p�b�P�[�W --------------------------------------------------------------*/
package manami.talk;

/*-- �C���|�[�g --------------------------------------------------------------*/
import javax.servlet.http.*;
import manami.system.*;
import manami.util.*;


/*-- �N���X��` --------------------------------------------------------------*/
public class BadWord
{

	private String _str;
	private boolean flg;

	public  void   setStr(String s){ _str = s; }

	DB db = new DB();
	DateTime dt = new DateTime();
	String time = dt.getString();

	//--------------------------------------------------------
	// �R���X�g���N�^
	//--------------------------------------------------------
	public BadWord()
	{
		this.clear();
	}

	public BadWord(String s)
	{
		this.clear();
		_str = s;
	}


	//--------------------------------------------------------
	// �N���A
	//--------------------------------------------------------
	private void clear() {
		_str 	 = "";
	}



	//--------------------------------------------------------
	// �֎~�p�꒲��
	//--------------------------------------------------------

	public String isBad(){
		String rtnStr = _str;
		flg = false;

		try{
			db.connect();
		}catch( Exception e ){
			Static.Error.out( e.toString() );
		}

		String _sql = " select �֎~�p�� from �`���b�g�֎~�p��e�[�u�� ";


		try{
			db.create();
			db.select( _sql );

			while( db.next() ){
				if( _str.indexOf(db.getString(1)) >-1){
/*
					rtnStr = _str.substring(0,_str.indexOf(db.getString(1)));
					int i;
					for(i = 0; i < db.getString(1).length(); i++ ){
						rtnStr += "X";
					}
					rtnStr += _str.substring(_str.indexOf(db.getString(1)) + i);
*/
					String chgStr = "";
					for(int i = 0; i < db.getString(1).length(); i++ ){
						chgStr += "*";
						//chgStr += "X";   //chg 2004.07.13
					}
					rtnStr = _str.replaceAll(db.getString(1),chgStr);
					flg = true;
				}
			}

			db.close();

		}catch( Exception e){
				Static.Error.out( e.toString() );
		}


		try{
			db.disconnect();
		}catch( Exception e ){
			Static.Error.out( e.toString() );
		}

		return rtnStr;

	}




}
