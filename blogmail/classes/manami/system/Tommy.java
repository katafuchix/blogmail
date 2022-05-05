/*
********************************************************************************
* システム：ai-land i-mode live BBS for Queen 	                               *
*==============================================================================*
* クラス　：Tommy                   ｜モジュール：Tommy.java                   *
* 概要　　：トレースファイル出力関連                                           *
* 作成　　：2004/07/16 K.Katafuchi(affinity)                                   *
*------------------------------------------------------------------------------*
* 移植　　：2004/07/20 K.Katafuchi(affinity)                                   *
********************************************************************************
*/

/*-- パッケージ --------------------------------------------------------------*/
package manami.system;

/*-- インポート --------------------------------------------------------------*/
import  javax.servlet.*;
import  manami.system.*;

/*-- クラス定義 --------------------------------------------------------------*/
public class Tommy extends Log {

	static    String     Classname = "Tommy";

	static    boolean    _IsGetProperty = false;
	static    String     _Directory;
	static    String     _Encode;
	static    boolean    _DebugMode;

	  ////////////////////
	 // コンストラクタ //
	////////////////////
	public Tommy() {
		_initialize();
	}
	public Tommy( String SubDir ) {
		_initialize();
		setDirectory( FileIO.addPath( _Directory, SubDir ) + "/" );
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
			_Encode    = P.getString(  "Encode" );
			_DebugMode = P.getBoolean( "DebugMode" );
			_IsGetProperty = true;
		}

		// 各種設定
		setDirectory( _Directory );
		setExtension( ".tommy" );
		setEncode( _Encode );
		setIsOut( _DebugMode );
		setIsAddDate( false );

	}

}
