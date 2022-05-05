/*
********************************************************************************
* システム：ai-land i-mode live BBS for Queen 			                       *
*==============================================================================*
* クラス　：Debug                   ｜モジュール：Debug.java                   *
* 概要　　：デバッグ用                                                         *
* 作成　　：2004/04/16 K.Katafuchi(affinity)                                   *
*			JDK1.2 オリジナル作成のためコンパイルが通らない。順次修正          *
*------------------------------------------------------------------------------*
* 更新　　：2004/07/20 K.Katafuchi(affinity)								   *
*			ai-land i-mode より移植											   *
********************************************************************************
*/

/*-- パッケージ --------------------------------------------------------------*/
package manami.system;

/*-- インポート --------------------------------------------------------------*/
import  java.io.*;
//import  javax.servlet.jsp.*;   //original
import  manami.system.*;

/*-- クラス定義 --------------------------------------------------------------*/
public class Debug {

	static    String     Classname = "Debug";

	static    boolean    _IsGetProperty = false;
	static    boolean    _DebugMode;

	  ////////////////////
	 // コンストラクタ //
	////////////////////
	public Debug() {
		if( ! _IsGetProperty ) {
			Property P = new Property();
			P.open( Classname );
			_DebugMode = P.getBoolean( "DebugMode" );
			_IsGetProperty = true;
		}
	}

	  /////////////
	 // JSP出力 //
	/////////////
//	static public void out( JspWriter out, String OutString ) {   //original
	static public void out( String out, String OutString ) {

		if( out == null ) {
			out( OutString );
			return;
		}

		if( _DebugMode ) {
			try {
				//out.println( OutString );  //org
				System.out.println( OutString );
			//} catch( IOException X ) {  //org
			} catch( Exception X ) {
				System.out.println( X.toString() );
			}
		}
	}

	  //////////////
	 // 標準出力 //
	//////////////
	static public void out( String OutString ) {
		if( _DebugMode ) {
			System.out.println( OutString );
		}
	}

}
