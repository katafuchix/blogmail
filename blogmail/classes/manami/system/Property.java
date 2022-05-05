/*
********************************************************************************
* システム：ai-land i-mode live BBS for Queen 	                               *
*==============================================================================*
* クラス　：Property                ｜モジュール：Property.java                *
* 概要　　：プロパティファイル関連                                             *
* 作成　　：2004/04/16 K.Katafuchi(affinitynation)                             *
*			プロパティファイル　AI.properties　を読み込みます                  *
*------------------------------------------------------------------------------*
* 更新　　：2004/07/20 K.Katafuchi(affinity)								   *
*			ai-land i-mode より移植											   *
********************************************************************************
*/

/*-- パッケージ --------------------------------------------------------------*/
package manami.system;

/*-- インポート --------------------------------------------------------------*/
import  java.util.*;
import  java.io.*;

/*-- クラス定義 --------------------------------------------------------------*/
public class Property {

	static    String     Classname = "Property";

	static    boolean    _IsGetProperty = false;
	static    String     _HomeDir;

	private   Properties _Property = null;
	private   String     _Classname;

	  ////////////////////
	 // コンストラクタ //
	////////////////////
	public Property() {

		if( ! _IsGetProperty ) {
			open();
			_HomeDir  = getString( "HomeDirectory" );
			_IsGetProperty = true;
		}

	}

	  //////////////////
	 // デストラクタ //
	//////////////////
	public void finalize() {
		_Property = null;
	}

	  ////////////////////////////
	 // ホームディレクトリ取得 //
	////////////////////////////
	static public String getHomeDir() {
		return( _HomeDir );
	}

	  //////////
	 // 生成 //
	//////////
//	static private void _create() {
//		try {
////			_StaticProperty.load( Property.class.getResourceAsStream( "System.properties" ) );
//			_StaticProperty.load( Property.class.getResourceAsStream( "RS.properties" ) );
//		} catch( Exception X ) {
////        	System.out.println( X.toString() );
//		}
//	}
	public void open() {
		open( null );
	}
	public void open( String Classname ) {

		_Property = null;
		_Property = new Properties();
		try {
//			_Property.load( new FileInputStream( _ConfFile ) );
			_Property.load( Property.class.getResourceAsStream( "Queen.properties" ) );
		} catch( Exception X ) {
//			System.out.println( X.toString() );
		}
		_Classname = Classname;
	}

	  //////////////////
	 // 環境変数取得 //
	//////////////////
	static public String getEnv( String Key ) {

		try {
			Runtime           rt  = Runtime.getRuntime();
if( rt  == null ) System.out.println( "Runtime is null" );
			Process           pr  = rt.exec( "printenv" );
if( pr  == null ) System.out.println( "Process is null" );
			InputStream       is  = pr.getInputStream();
if( is  == null ) System.out.println( "InputStream is null" );
			InputStreamReader isr = new InputStreamReader( is );
if( isr == null ) System.out.println( "InputStreamReader is null" );
			BufferedReader    br  = new BufferedReader( isr );
if( br  == null ) System.out.println( "BufferedReader is null" );
			String            Line;
			while( ( Line = br.readLine() ) != null ) {
				int Index = Line.indexOf( '=' );
				if( Index > 0 ) {
					if( Line.substring( 0, Index ).equals( Key ) ) {
						pr.destroy();
						return( Line.substring( Index + 1 ) );
					}
				}
			}
		} catch( Exception X ) {
			System.out.println( X.toString() );
		}

		return( null );
	}

	  ////////////////
	 // 文字列取得 //
	////////////////
	public String getString( String Key ) {
		return( getString( _Classname, Key ) );
	}
	public String getString( String Classname, String Key ) {
		String Entry = "";
		if( Classname != null ) Entry = Classname + ".";
		Entry += Key;
		String Value = _Property.getProperty( Entry );
		if( Value == null ) Value = _Property.getProperty( Key );
		return( Value );
	}

	  //////////////
	 // 数値取得 //
	//////////////
	public int getInt( String Key ) {
		return( Integer.parseInt( getString( Key ) ) );
	}
	public int getInt( String Classname, String Key ) {
		return( Integer.parseInt( getString( Classname, Key ) ) );
	}

	  ////////////////////
	 // true/false取得 //
	////////////////////
	public boolean getBoolean( String Key ) {
		return( Boolean.valueOf( getString( Key ) ).booleanValue() );
	}
	public boolean getBoolean( String Classname, String Key ) {
		return( Boolean.valueOf( getString( Classname, Key ) ).booleanValue() );
	}

}
