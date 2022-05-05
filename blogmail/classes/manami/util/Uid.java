/*
********************************************************************************
* システム： ai-land i-mode live BBS for Queen 	                               *
*==============================================================================*
* モジュール：Uid.java                                    			           *
* クラス名　：Uid		                                                       *
* 概要　　　：NTTドコモ uid 取得クラス                                         *
* 作成　　　：2004/05/14 K.Katafuchi(affinity)                                 *
*------------------------------------------------------------------------------*
* 更新　　：2004/07/20 K.Katafuchi(affinity)								   *
*			ai-land i-mode より移植											   *
********************************************************************************
*/

/*-- パッケージ --------------------------------------------------------------*/
package manami.util;

/*-- インポート --------------------------------------------------------------*/
import java.io.*;
import javax.servlet.http.*;
import manami.system.*;


/*-- クラス定義 --------------------------------------------------------------*/
public class Uid
{

	private String _Uid;
	public  void   setUid( String s ) { _Uid  = s;   }
	public  String getUid()           { return _Uid; }

	private HttpServletRequest _Request;

	//--------------------------------------------------------
	// コンストラクタ
	//--------------------------------------------------------
	public Uid()
	{
		clear();
	}
	public Uid( HttpServletRequest request ) {
		setRequest( request );
		clear();
	}

	public void setRequest( HttpServletRequest request ) {
		_Request = request;
	}

	private void clear() {
		_Uid = "";
	}

	//--------------------------------------------------------
	// メソッド：uid 取得 12桁
	// 戻り値  ：uid（12桁）
	//--------------------------------------------------------
	public String createID()
	{
		String UID = null;
		if( _Request != null ) UID = _Request.getParameter( "uid" );
		if( UID		 == null || UID.equals("NULLGWDOCOMO")) UID = createID_dummy();
		return( UID );
	}



/* テスト用にダミーuid発行メソッド */

	public String createID_dummy()
	{
		String UID = "";
		try {
			InputStreamReader _In  = new InputStreamReader(
			  new FileInputStream( Static.HomeDir + "uid.txt" ), "SJIS" );
			BufferedReader    _Buf = new BufferedReader( _In );
			// EOFなら終了
			if( _Buf.ready() ) {
				// 1行読込み
				UID = _Buf.readLine();
			}
		} catch( Exception X ) {
			Static.Error.out( X.toString() );
		}

		return( UID );
	}
}
