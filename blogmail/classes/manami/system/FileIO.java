/*
********************************************************************************
* システム：ai-land i-mode live BBS for Queen 			                       *
*==============================================================================*
* クラス　：FileIO                  ｜モジュール：FileIO.java                  *
* 概要　　：ファイル／ディレクトリ関連                                         *
* 作成　　：2004/04/16 K.Katafuchi(affinity)                                   *
*------------------------------------------------------------------------------*
* 更新　　：2004/07/20 K.Katafuchi(affinity)								   *
*			ai-land i-mode より移植											   *
********************************************************************************
*/

/*-- パッケージ --------------------------------------------------------------*/
package manami.system;

/*-- インポート --------------------------------------------------------------*/
import  java.io.*;

/*-- クラス定義 --------------------------------------------------------------*/
public class FileIO extends java.io.File {

	static    String     Classname = "FileIO";

	private   int        _MaxLength = 1024;

	  ////////////////////
	 // コンストラクタ //
	////////////////////
	public FileIO( String Pathname ) {
		super( Pathname );
	}

	  ////////////
	 // コピー //
	////////////
	public boolean copyTo( File file ) {

		boolean Result = false;

		// コピー元存在チェック
		if( ! exists() ) return( false );

		RandomAccessFile r = null;
		RandomAccessFile w = null;
		try {
			// オープン
			r = new RandomAccessFile( this, "rw" );
			w = new RandomAccessFile( file, "rw" );

			// コピー
			byte Buf[] = new byte[_MaxLength];
			int  Length;
			while( ( Length = r.read( Buf, 0, _MaxLength ) ) > 0 ) {
				w.write( Buf, 0, Length );
			}
			// 正常
			Result = true;
		} catch( Exception X ) {
			Static.Error.out( X );
		} finally {
			// クローズ
			try {
				if( r != null ) { r.close(); r = null; }
				if( w != null ) { w.close(); w = null; }
			} catch( IOException Ex ) {}

		//return( Result );  //org 下へ移動

		}

		return( Result ); 
	}

	  ////////////////
	 // パス名連結 //
	////////////////
	static public String addPath( String BeforeString, String AfterString ) {

		String Separater = null;
		String ResultString;

		if( BeforeString != null ) {
			if( BeforeString.endsWith( "/" ) ) {
				Separater = "/";
			} else
			if( BeforeString.endsWith( "\\" ) ) {
				Separater = "\\";
			}
		}

		if( AfterString == null ) return( BeforeString );

		if( AfterString.startsWith( "/" ) ) {
			if( Separater == null ) {
				ResultString = BeforeString + AfterString;
			} else {
				ResultString = BeforeString + AfterString.substring( 1 );
			}
		} else
		if( AfterString.startsWith( "\\" ) ) {
			if( Separater == null ) {
				ResultString = BeforeString + AfterString;
			} else {
				ResultString = BeforeString + AfterString.substring( 1 );
			}
		} else {
			if( Separater == null ) {
				ResultString = BeforeString + "/" + AfterString;
			} else {
				ResultString = BeforeString + AfterString;
			}
		}

		return( ResultString );
	}

}
