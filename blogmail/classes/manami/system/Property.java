/*
********************************************************************************
* �V�X�e���Fai-land i-mode live BBS for Queen 	                               *
*==============================================================================*
* �N���X�@�FProperty                �b���W���[���FProperty.java                *
* �T�v�@�@�F�v���p�e�B�t�@�C���֘A                                             *
* �쐬�@�@�F2004/04/16 K.Katafuchi(affinitynation)                             *
*			�v���p�e�B�t�@�C���@AI.properties�@��ǂݍ��݂܂�                  *
*------------------------------------------------------------------------------*
* �X�V�@�@�F2004/07/20 K.Katafuchi(affinity)								   *
*			ai-land i-mode ���ڐA											   *
********************************************************************************
*/

/*-- �p�b�P�[�W --------------------------------------------------------------*/
package manami.system;

/*-- �C���|�[�g --------------------------------------------------------------*/
import  java.util.*;
import  java.io.*;

/*-- �N���X��` --------------------------------------------------------------*/
public class Property {

	static    String     Classname = "Property";

	static    boolean    _IsGetProperty = false;
	static    String     _HomeDir;

	private   Properties _Property = null;
	private   String     _Classname;

	  ////////////////////
	 // �R���X�g���N�^ //
	////////////////////
	public Property() {

		if( ! _IsGetProperty ) {
			open();
			_HomeDir  = getString( "HomeDirectory" );
			_IsGetProperty = true;
		}

	}

	  //////////////////
	 // �f�X�g���N�^ //
	//////////////////
	public void finalize() {
		_Property = null;
	}

	  ////////////////////////////
	 // �z�[���f�B���N�g���擾 //
	////////////////////////////
	static public String getHomeDir() {
		return( _HomeDir );
	}

	  //////////
	 // ���� //
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
	 // ���ϐ��擾 //
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
	 // ������擾 //
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
	 // ���l�擾 //
	//////////////
	public int getInt( String Key ) {
		return( Integer.parseInt( getString( Key ) ) );
	}
	public int getInt( String Classname, String Key ) {
		return( Integer.parseInt( getString( Classname, Key ) ) );
	}

	  ////////////////////
	 // true/false�擾 //
	////////////////////
	public boolean getBoolean( String Key ) {
		return( Boolean.valueOf( getString( Key ) ).booleanValue() );
	}
	public boolean getBoolean( String Classname, String Key ) {
		return( Boolean.valueOf( getString( Classname, Key ) ).booleanValue() );
	}

}
