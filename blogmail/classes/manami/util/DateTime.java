/*
********************************************************************************
* �V�X�e���F ai-land i-mode live BBS for Queen 		                           *
*==============================================================================*
* �N���X�@�FDateTime                �b���W���[���FDateTime.java                *
* �T�v�@�@�F���t�֘A                                                           *
* �쐬�@�@�F2004/04/20 K.Katafuchi(affinity)                                   *
*------------------------------------------------------------------------------*
* �X�V�@�@�F2004/07/20 K.Katafuchi(affinity)								   *
*			ai-land i-mode ���ڐA											   *
********************************************************************************
*/

/*-- �p�b�P�[�W --------------------------------------------------------------*/
package manami.util;

/*-- �C���|�[�g --------------------------------------------------------------*/
import  java.beans.*;
import  java.lang.*;
import  java.util.*;
import  java.text.*;
import  manami.system.*;

/*-- �N���X��` --------------------------------------------------------------*/
public class DateTime extends java.util.Date {

	static    String     Classname = "DateTime";

	  ////////////////////
	 // �R���X�g���N�^ //
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
	 // ������ݒ� //
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
	 // ���l�ݒ� //
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
	 // ������擾 //
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
	 // ���l�擾 //
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
