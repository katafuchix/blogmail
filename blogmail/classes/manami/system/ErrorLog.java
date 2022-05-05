/*
********************************************************************************
* �V�X�e���Fai-land i-mode live BBS for Queen 				                   *
*==============================================================================*
* �N���X�@�FErrorLog                �b���W���[���FErrorLog.java                *
* �T�v�@�@�F�G���[���O�t�@�C���o�͊֘A                                         *
* �쐬�@�@�F2004/04/16 K.katafuchi(affinity)                                   *
*------------------------------------------------------------------------------*
* �X�V�@�@�F2004/07/20 K.Katafuchi(affinity)								   *
*			ai-land i-mode ���ڐA											   *
********************************************************************************
*/

/*-- �p�b�P�[�W --------------------------------------------------------------*/
package manami.system;

/*-- �C���|�[�g --------------------------------------------------------------*/
import manami.system.*;
import manami.util.*;

/*-- �N���X��` --------------------------------------------------------------*/
public class ErrorLog extends Log {

	static    String     Classname = "ErrorLog";

	static    boolean    _IsGetProperty = false;
	static    String     _Directory;
	static    String     _Encode;

	  ////////////////////
	 // �R���X�g���N�^ //
	////////////////////
	public ErrorLog() {
		_initialize();
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
			_Encode    = P.getString( "Encode" );
			_IsGetProperty = true;
		}

		// �e��ݒ�
		setDirectory( _Directory );
		setExtension( ".err" );
		setEncode( _Encode );

	}

}
