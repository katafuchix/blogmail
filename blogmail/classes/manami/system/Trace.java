/*
********************************************************************************
* システム： ai-land i-mode live BBS for Queen                                 *	                               *
*==============================================================================*
* クラス　：Trace                   ｜モジュール：Trace.java                   *
* 概要　　：トレースファイル出力関連                                           *
* 作成　　：2004/04/16 K.Katafuchi(affinity)                                   *
*------------------------------------------------------------------------------*
* 更新　　：2004/07/20 K.Katafuchi(affinity)								   *
*			ai-land i-mode より移植											   *
********************************************************************************
*/

/*-- パッケージ --------------------------------------------------------------*/
package manami.system;

/*-- インポート --------------------------------------------------------------*/
import  javax.servlet.*;
import  manami.system.*;

/*-- クラス定義 --------------------------------------------------------------*/
public class Trace extends Log {

	static    String     Classname = "Trace";

	static    boolean    _IsGetProperty = false;
	static    String     _Directory;
	static    String     _Encode;
	static    boolean    _DebugMode;

	  ////////////////////
	 // コンストラクタ //
	////////////////////
	public Trace() {
		_initialize();
	}
	public Trace( String SubDir ) {
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
		setExtension( ".trc" );
		setEncode( _Encode );
		setIsOut( _DebugMode );
		setIsAddDate( false );

	}

}
