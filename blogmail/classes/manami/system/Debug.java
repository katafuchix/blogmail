/*
********************************************************************************
* �V�X�e���Fai-land i-mode live BBS for Queen 			                       *
*==============================================================================*
* �N���X�@�FDebug                   �b���W���[���FDebug.java                   *
* �T�v�@�@�F�f�o�b�O�p                                                         *
* �쐬�@�@�F2004/04/16 K.Katafuchi(affinity)                                   *
*			JDK1.2 �I���W�i���쐬�̂��߃R���p�C�����ʂ�Ȃ��B�����C��          *
*------------------------------------------------------------------------------*
* �X�V�@�@�F2004/07/20 K.Katafuchi(affinity)								   *
*			ai-land i-mode ���ڐA											   *
********************************************************************************
*/

/*-- �p�b�P�[�W --------------------------------------------------------------*/
package manami.system;

/*-- �C���|�[�g --------------------------------------------------------------*/
import  java.io.*;
//import  javax.servlet.jsp.*;   //original
import  manami.system.*;

/*-- �N���X��` --------------------------------------------------------------*/
public class Debug {

	static    String     Classname = "Debug";

	static    boolean    _IsGetProperty = false;
	static    boolean    _DebugMode;

	  ////////////////////
	 // �R���X�g���N�^ //
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
	 // JSP�o�� //
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
	 // �W���o�� //
	//////////////
	static public void out( String OutString ) {
		if( _DebugMode ) {
			System.out.println( OutString );
		}
	}

}
