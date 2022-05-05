/*
********************************************************************************
* システム：ai-land i-mode live BBS for Queen 	                               *
*==============================================================================*
* クラス　：Static                  ｜モジュール：Static.java                  *
* 概要　　：ログ出力など                                                       *
* 作成　　：2004/04/16 K.katafuchi(affinity)                                   *
*------------------------------------------------------------------------------*
* 更新　　：2004/07/20 K.Katafuchi(affinity)								   *
*			ai-land i-mode より移植											   *
*		  : 2004/07/17 K.Katafuchi(affinity)								   *
*			Tommy追加														   *
********************************************************************************
*/

/*-- パッケージ --------------------------------------------------------------*/
package manami.system;

/*-- インポート --------------------------------------------------------------*/

/*-- クラス定義 --------------------------------------------------------------*/
public class Static {

	static        String  _MyName   = "Static";

	static public Property Property = new Property();
	static public String   HomeDir  = Property.getHomeDir();

	static public Log      Log      = new Log();
	static public ErrorLog Error    = new ErrorLog();
	static public Trace    Trace    = new Trace();

	static public Debug    Debug    = new Debug();

   	static public Trace    XXX     = new Trace( "XXX" );

	static public Tommy    Tommy    = new Tommy();


	  ////////////////////
	 // コンストラクタ //
	////////////////////
	public Static() {
	}

}
