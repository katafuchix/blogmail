/*
********************************************************************************
* �V�X�e���F ai-land i-mode live BBS for Queen                                 *	                               *
*==============================================================================*
* �N���X�@�FTrace                   �b���W���[���FTrace.java                   *
* �T�v�@�@�F�g���[�X�t�@�C���o�͊֘A                                           *
* �쐬�@�@�F2004/04/16 K.Katafuchi(affinity)                                   *
*------------------------------------------------------------------------------*
* �X�V�@�@�F2004/07/20 K.Katafuchi(affinity)								   *
*			ai-land i-mode ���ڐA											   *
********************************************************************************
*/

/*-- �p�b�P�[�W --------------------------------------------------------------*/
package manami.system;

/*-- �C���|�[�g --------------------------------------------------------------*/
import  javax.servlet.*;
import  manami.system.*;

/*-- �N���X��` --------------------------------------------------------------*/
public class Trace extends Log {

	static    String     Classname = "Trace";

	static    boolean    _IsGetProperty = false;
	static    String     _Directory;
	static    String     _Encode;
	static    boolean    _DebugMode;

	  ////////////////////
	 // �R���X�g���N�^ //
	////////////////////
	public Trace() {
		_initialize();
	}
	public Trace( String SubDir ) {
		_initialize();
		setDirectory( FileIO.addPath( _Directory, SubDir ) + "/" );
	}

	  ////////////
	 // ������ //
	////////////
	private void _initialize() {

		 // �v���p�e�B�擾
		if( ! _IsGetProperty ) {
			Property P = new Property();
			P.open( Classname );
			_Directory = FileIO.addPath( Static.HomeDir, P.getString( "LogDirectory" ) );
			_Encode    = P.getString(  "Encode" );
			_DebugMode = P.getBoolean( "DebugMode" );
			_IsGetProperty = true;
		}

		// �e��ݒ�
		setDirectory( _Directory );
		setExtension( ".trc" );
		setEncode( _Encode );
		setIsOut( _DebugMode );
		setIsAddDate( false );

	}

}
