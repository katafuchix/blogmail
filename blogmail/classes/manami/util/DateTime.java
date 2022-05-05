/*
********************************************************************************
* システム： ai-land i-mode live BBS for Queen 		                           *
*==============================================================================*
* クラス　：DateTime                ｜モジュール：DateTime.java                *
* 概要　　：日付関連                                                           *
* 作成　　：2004/04/20 K.Katafuchi(affinity)                                   *
*------------------------------------------------------------------------------*
* 更新　　：2004/07/20 K.Katafuchi(affinity)								   *
*			ai-land i-mode より移植											   *
********************************************************************************
*/

/*-- パッケージ --------------------------------------------------------------*/
package manami.util;

/*-- インポート --------------------------------------------------------------*/
import  java.beans.*;
import  java.lang.*;
import  java.util.*;
import  java.text.*;
import  manami.system.*;

/*-- クラス定義 --------------------------------------------------------------*/
public class DateTime extends java.util.Date {

	static    String     Classname = "DateTime";

	  ////////////////////
	 // コンストラクタ //
	////////////////////
	public DateTime() {
		super();
		super.setTime( (super.getTime( ) / 1000) * 1000 );
	}
	public DateTime( String DateString ) {
		super();
		setString( DateString );
	}
	public DateTime( String Format, String DateString ) {
		super();
		setString( Format, DateString );
	}
	public DateTime( int DateValue ) {
		super();
		setInt( DateValue );
	}
	public DateTime( String Format, int DateValue ) {
		super();
		setInt( Format, DateValue );
	}

	  ////////////////
	 // 文字列設定 //
	////////////////
	public void setString( String DateString ) {
		setString( "yyyyMMdd", DateString );
	}
	public void setString( String Format, String DateString ) {

		SimpleDateFormat df;
		try {
			df = new SimpleDateFormat( Format );
			super.setTime( df.parse( DateString ).getTime() );
		} catch( Exception X ) {
			Static.Error.out( X );
		}

	}

	  //////////////
	 // 数値設定 //
	//////////////
	public void setInt( int DateValue ) {
		setInt( "yyyyMMdd", DateValue );
	}
	public void setInt( String Format, int DateValue ) {

		SimpleDateFormat df;
		try {
			df = new SimpleDateFormat( Format );
			super.setTime( df.parse( Integer.toString( DateValue ) ).getTime() );
		} catch( Exception X ) {
			Static.Error.out( X );
		}

	}

	  ////////////////
	 // 文字列取得 //
	////////////////
	public String getString() {
		return( getString( "yyyyMMdd" ) );
	}
	public String getString( String Format ) {

		String DateString = "";
		SimpleDateFormat df;
		try {
			df = new SimpleDateFormat( Format );
			DateString = df.format( this );
		} catch( Exception X ) {
			Static.Error.out( X );
		} finally {

//		return( DateString );   //original
		}

		return( DateString );

	}

	  //////////////
	 // 数値取得 //
	//////////////
	public int getInt() {
		return( getInt( "yyyyMMdd" ) );
	}
	public int getInt( String Format ) {

		String DateString = getString( Format );
		int    DateValue  = 0;
		try {
			DateValue = Integer.parseInt( DateString );
		} catch( Exception X ) {
			Static.Error.out( X );
		} finally {

//		return( DateValue );	//original 

		}
		return( DateValue );
	}

}
