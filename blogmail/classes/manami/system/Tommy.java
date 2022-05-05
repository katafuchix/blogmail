/*
********************************************************************************
* �V�X�e���Fai-land i-mode live BBS for Queen 	                               *
*==============================================================================*
* �N���X�@�FTommy                   �b���W���[���FTommy.java                   *
* �T�v�@�@�F�g���[�X�t�@�C���o�͊֘A                                           *
* �쐬�@�@�F2004/07/16 K.Katafuchi(affinity)                                   *
*------------------------------------------------------------------------------*
* �ڐA�@�@�F2004/07/20 K.Katafuchi(affinity)                                   *
********************************************************************************
*/

/*-- �p�b�P�[�W --------------------------------------------------------------*/
package manami.system;

/*-- �C���|�[�g --------------------------------------------------------------*/
import  javax.servlet.*;
import  manami.system.*;

/*-- �N���X��` --------------------------------------------------------------*/
public class Tommy extends Log {

	static    String     Classname = "Tommy";

	static    boolean    _IsGetProperty = false;
	static    String     _Directory;
	static    String     _Encode;
	static    boolean    _DebugMode;

	  ////////////////////
	 // �R���X�g���N�^ //
	////////////////////
	public Tommy() {
		_initialize();
	}
	public Tommy( String SubDir ) {
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
		setExtension( ".tommy" );
		setEncode( _Encode );
		setIsOut( _DebugMode );
		setIsAddDate( false );

	}

}
