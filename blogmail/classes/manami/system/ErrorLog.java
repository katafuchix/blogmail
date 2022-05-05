/*
********************************************************************************
* システム：ai-land i-mode live BBS for Queen 				                   *
*==============================================================================*
* クラス　：ErrorLog                ｜モジュール：ErrorLog.java                *
* 概要　　：エラーログファイル出力関連                                         *
* 作成　　：2004/04/16 K.katafuchi(affinity)                                   *
*------------------------------------------------------------------------------*
* 更新　　：2004/07/20 K.Katafuchi(affinity)								   *
*			ai-land i-mode より移植											   *
********************************************************************************
*/

/*-- パッケージ --------------------------------------------------------------*/
package manami.system;

/*-- インポート --------------------------------------------------------------*/
import manami.system.*;
import manami.util.*;

/*-- クラス定義 --------------------------------------------------------------*/
public class ErrorLog extends Log {

	static    String     Classname = "ErrorLog";

	static    boolean    _IsGetProperty = false;
	static    String     _Directory;
	static    String     _Encode;

	  ////////////////////
	 // コンストラクタ //
	////////////////////
	public ErrorLog() {
		_initialize();
	}

	  ////////////
	 // 初期化 //
	////////////
	private void _initialize() {

		 // プロパティ取得
		if( ! _IsGetProperty ) {
			Property P = new Property();
			P.open( Classname );
			_Directory = FileIO.addPath( Static.HomeDir, P.getString( "LogDirectory" ) );
			_Encode    = P.getString( "Encode" );
			_IsGetProperty = true;
		}

		// 各種設定
		setDirectory( _Directory );
		setExtension( ".err" );
		setEncode( _Encode );

	}

}
