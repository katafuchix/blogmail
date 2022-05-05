/*
********************************************************************************
* システム： ai-land i-mode live BBS for Queen 	                               *
*==============================================================================*
* クラス　：Super                   ｜モジュール：Super.java                   *
* 概要　　：汎用スーパークラス                                                 *
* 作成　　：2004/04/20 K.Katafuchi(affinity)                                   *
*------------------------------------------------------------------------------*
* 更新　　：2004/07/20 K.Katafuchi(affinity)								   *
*			ai-land i-mode より移植											   *
********************************************************************************
*/

/*-- パッケージ --------------------------------------------------------------*/
package manami.util;

/*-- インポート --------------------------------------------------------------*/
import  javax.servlet.jsp.*;
import  javax.servlet.http.*;
import  manami.system.*;

/*-- クラス定義 --------------------------------------------------------------*/
public class Super {

	static    String     Classname = "Super";

	protected JspWriter          out;
	protected JspRequest         request;
	protected HttpServletRequest req;

	  ////////////////////
	 // コンストラクタ //
	////////////////////
	public Super() {
	}

	  ///////////////////
	 // JspWriter設定 //
	///////////////////
	public void setJspWriter( JspWriter jWriter ) {
		out = jWriter;
	}

	  ////////////////////
	 // JspRequest設定 //
	////////////////////
	public void setJspRequest( HttpServletRequest Request ) {
		request = new JspRequest( Request );
	}

	  /////////////////
	 // Request設定 //
	/////////////////
	public void setRequest( HttpServletRequest Request ) {
		req = Request;
	}

}
