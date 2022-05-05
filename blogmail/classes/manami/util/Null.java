/*
********************************************************************************
* システム： ai-land i-mode live BBS for Queen 	                               *
*==============================================================================*
* クラス　：Null                    ｜モジュール：Null.java                    *
* 概要　　：null関連                                                           *
* 作成　　：2004/04/20 K.katafuchi(affinity)                                   *
*------------------------------------------------------------------------------*
* 更新　　：2004/07/20 K.Katafuchi(affinity)								   *
*			ai-land i-mode より移植											   *
********************************************************************************
*/

/*-- パッケージ --------------------------------------------------------------*/
package manami.util;

/*-- インポート --------------------------------------------------------------*/
import  manami.util.*;

/*-- クラス定義 --------------------------------------------------------------*/
public class Null {

	static    String     Classname = "Null";

	  ////////////////////
	 // コンストラクタ //
	////////////////////
	public Null() {
	}

	  //////////////////////////////////
	 // nullをデフォルト値に置きかえ //
	//////////////////////////////////
//	static public String replace( String Value, String Default ) {
//		return( replace( Value, Default ) );
//	}
	static public Object replace( Object Value, Object Default ) {
		Object Result = null;
		if( Value == null ) Result = Default;
		else                Result = Value;
		return( Result );
	}

};
