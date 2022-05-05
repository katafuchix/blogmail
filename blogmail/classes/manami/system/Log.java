/*
********************************************************************************
* システム：ai-land i-mode live BBS for Queen 		                           *
*==============================================================================*
* クラス　：Log                     ｜モジュール：Log.java                     *
* 概要　　：ログファイル出力関連                                               *
*			ai-land i-mode より移植											   *
* 作成　　：2004/04/20 K.Katafuchi(affinity)                                   *
*------------------------------------------------------------------------------*
* 更新　　：2004/07/20 K.Katafuchi(affinity)								   *
********************************************************************************
*/

/*-- パッケージ --------------------------------------------------------------*/
package manami.system;

/*-- インポート --------------------------------------------------------------*/
import  java.io.*;
import  java.sql.*;
import  javax.servlet.*;
import  javax.servlet.jsp.*;	// For Debug
import  manami.system.*;
import  manami.util.*;

/*-- クラス定義 --------------------------------------------------------------*/
public class Log {

	static    String     Classname = "Log";

	static    boolean    _IsGetProperty = false;
	static    String     _Directory;
	static    String     _Encode;

	private   String     _LogDirectory;
	private   String     _LogFilename  = null;
	private   String     _LogExtension = ".log";
	private   String     _LogEncode;
	private   boolean    _LogIsOut     = true;
	private   boolean    _LogIsAddDate = true;
	private   String     _LogSubDir     = "";

	private   OutputStreamWriter _Out;
	private   BufferedWriter     _Buf;

	protected ServletRequest     _Request    = null;
	private   String             _Clientname = "";

	  ////////////////////
	 // コンストラクタ //
	////////////////////
	public Log() {
		_initialize();
	}
	public Log( String SubDir ) {
		_LogSubDir = SubDir;
		_initialize();
	}

	  ////////////
	 // 初期化 //
	////////////
	private void _initialize() {

		// プロパティ取得
//		if( ! _IsGetProperty ) {
			Property P = new Property();
			P.open( Classname );
//			_Directory = FileIO.addPath( Static.HomeDir, P.getString( "LogDirectory" ) );
			_Directory = "C:/Program Files/Apache Software Foundation/Tomcat 5.0/webapps/chat/";
//			_Encode    = P.getString( "Encode" );
			_Encode    = "SJIS";

			_IsGetProperty = true;
//		}

		// 各種設定
		String Pathname = _Directory;
		if( ! _LogSubDir.equals( "" ) ) Pathname += _LogSubDir + "/";
		setDirectory( Pathname );
		setEncode( _Encode );

	}

	  ////////////////////////
	 // クライアント名取得 //
	////////////////////////
	public String getClientname() {
		return( _Clientname );
	}

	  //////////////////////
	 // ディレクトリ取得 //
	//////////////////////
	public String getDirectory() {
		return( _LogDirectory );
	}

	  //////////////////////
	 // ディレクトリ設定 //
	//////////////////////
	public void setDirectory( String Directory ) {
		_LogDirectory = Directory;
	}

	  ////////////////////
	 // ファイル名設定 //
	////////////////////
	public void setFilename( String Filename ) {
		_LogFilename = Filename;
	}

	  ////////////////
	 // 拡張子設定 //
	////////////////
	public void setExtension( String Extension ) {
		_LogExtension = Extension;
	}

	  //////////////////////////
	 // サブディレクトリ設定 //
	//////////////////////////
	public void setSubDir( String SubDir ) {
		_LogSubDir = SubDir;
		_initialize();
	}

	  ////////////////////
	 // エンコード設定 //
	////////////////////
	public void setEncode( String Encode ) {
		_LogEncode = Encode;
	}

	  ////////////////////
	 // 出力モード設定 //
	////////////////////
	public void setIsOut( boolean IsOut ) {
		_LogIsOut = IsOut;
	}

	  //////////////////
	 // 日付出力設定 //
	//////////////////
	public void setIsAddDate( boolean IsAddDate ) {
		_LogIsAddDate = IsAddDate;
	}

	  ////////////////////
	 // リクエスト設定 //
	////////////////////
	public void setRequest( ServletRequest Request ) {
		_Request    = Request;
		_Clientname = Request.getRemoteHost();
	}

	  //////////
	 // 出力 //
	//////////
	public void out( String OutString ) {
		out( OutString, false );
	}
	public void out( String OutString, boolean isStandardOut ) {

		// 非出力モードチェック
		if( ! _LogIsOut ) return;

		DateTime d = new DateTime();
		try {
			// ディレクトリ作成
			String Pathname = _LogDirectory;
			File f = new File( Pathname );
			f.mkdirs();

			// ログファイルオープン
			if( _LogFilename != null ) Pathname += _LogFilename;
			else                       Pathname += d.getString();
			Pathname += _LogExtension;

//FilePermission fp = new FilePermission( Pathname, "read,write,delete" );

			_Out = new OutputStreamWriter( new FileOutputStream( Pathname, true ), _LogEncode );
			_Buf = new BufferedWriter( _Out );

			// ログ編集
			String DateString = "";
			if( _LogIsAddDate ) DateString = d.getString( "yyyy/MM/dd HH:mm:ss" ) + " ";

			// 標準出力
			if( isStandardOut ) System.out.println( OutString );
			// ログファイル出力
			_Buf.write( DateString + OutString );
			_Buf.newLine();
		} catch ( Exception Ex ) {
			System.out.println( "Log: unexpected error." );
		} finally {
			// ログファイルクローズ
			try {
				if( _Buf != null ) _Buf.close();
				if( _Out != null ) _Out.close();
			} catch( IOException Ex ) {}
		}

	}
	  //////////////////////////
	 // エラーメッセージ出力 //
	//////////////////////////
	public void out( Exception X ) {
		out( X, false );
	}
	public void out( Exception X, boolean isStandardOut ) {
		// エラーメッセージ出力
		out( X.toString(), isStandardOut );
        String Message = X.getMessage();
		if( Message != null) out( Message, isStandardOut );
		StringWriter StringOut = new StringWriter();
		PrintWriter  PrintOut  = new PrintWriter( StringOut );
		X.printStackTrace( PrintOut );
        out( StringOut.toString(), isStandardOut );
	}
	public void out( SQLException X ) {
		out( X, false );
	}
	public void out( SQLException X, boolean isStandardOut ) {
		// エラーメッセージ取得
		String ErrorString = "";
		for( SQLException SX = X; SX != null; SX = SX.getNextException() ) {
			ErrorString += SX.toString() + "\n";
		}
		// エラーメッセージ出力
		out( ErrorString, isStandardOut );
        String Message = X.getMessage();
		if( Message != null) out( Message, isStandardOut );
		StringWriter StringOut = new StringWriter();
		PrintWriter  PrintOut  = new PrintWriter( StringOut );
		X.printStackTrace( PrintOut );
        out( StringOut.toString(), isStandardOut );
	}


}
