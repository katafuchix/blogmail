/*
********************************************************************************
* システム　： ai-land i-mode live BBS for Queen 		                       *
*==============================================================================*
* モジュール：BadWord.java                                    			       *
* クラス名　：BadWord		                                                   *
* 概要　　　：禁止用語チェック		                                           *
* 作成　　　：2004/06/26 K.Katafuchi(affinity)                                 *
*------------------------------------------------------------------------------*　　　：2004/07/20 K.Katafuchi(affinity) X→*							   *
********************************************************************************
*/

/*-- パッケージ --------------------------------------------------------------*/
package manami.talk;

/*-- インポート --------------------------------------------------------------*/
import javax.servlet.http.*;
import manami.system.*;
import manami.util.*;


/*-- クラス定義 --------------------------------------------------------------*/
public class BadWord
{

	private String _str;
	private boolean flg;

	public  void   setStr(String s){ _str = s; }

	DB db = new DB();
	DateTime dt = new DateTime();
	String time = dt.getString();

	//--------------------------------------------------------
	// コンストラクタ
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
	// クリア
	//--------------------------------------------------------
	private void clear() {
		_str 	 = "";
	}



	//--------------------------------------------------------
	// 禁止用語調査
	//--------------------------------------------------------

	public String isBad(){
		String rtnStr = _str;
		flg = false;

		try{
			db.connect();
		}catch( Exception e ){
			Static.Error.out( e.toString() );
		}

		String _sql = " select 禁止用語 from チャット禁止用語テーブル ";


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
