/*
********************************************************************************
* �V�X�e���F ai-land i-mode live BBS for Queen 	                               *
*==============================================================================*
* ���W���[���FUid.java                                    			           *
* �N���X���@�FUid		                                                       *
* �T�v�@�@�@�FNTT�h�R�� uid �擾�N���X                                         *
* �쐬�@�@�@�F2004/05/14 K.Katafuchi(affinity)                                 *
*------------------------------------------------------------------------------*
* �X�V�@�@�F2004/07/20 K.Katafuchi(affinity)								   *
*			ai-land i-mode ���ڐA											   *
********************************************************************************
*/

/*-- �p�b�P�[�W --------------------------------------------------------------*/
package manami.util;

/*-- �C���|�[�g --------------------------------------------------------------*/
import java.io.*;
import javax.servlet.http.*;
import manami.system.*;


/*-- �N���X��` --------------------------------------------------------------*/
public class Uid
{

	private String _Uid;
	public  void   setUid( String s ) { _Uid  = s;   }
	public  String getUid()           { return _Uid; }

	private HttpServletRequest _Request;

	//--------------------------------------------------------
	// �R���X�g���N�^
	//--------------------------------------------------------
	public Uid()
	{
		clear();
	}
	public Uid( HttpServletRequest request ) {
		setRequest( request );
		clear();
	}

	public void setRequest( HttpServletRequest request ) {
		_Request = request;
	}

	private void clear() {
		_Uid = "";
	}

	//--------------------------------------------------------
	// ���\�b�h�Fuid �擾 12��
	// �߂�l  �Fuid�i12���j
	//--------------------------------------------------------
	public String createID()
	{
		String UID = null;
		if( _Request != null ) UID = _Request.getParameter( "uid" );
		if( UID		 == null || UID.equals("NULLGWDOCOMO")) UID = createID_dummy();
		return( UID );
	}



/* �e�X�g�p�Ƀ_�~�[uid���s���\�b�h */

	public String createID_dummy()
	{
		String UID = "";
		try {
			InputStreamReader _In  = new InputStreamReader(
			  new FileInputStream( Static.HomeDir + "uid.txt" ), "SJIS" );
			BufferedReader    _Buf = new BufferedReader( _In );
			// EOF�Ȃ�I��
			if( _Buf.ready() ) {
				// 1�s�Ǎ���
				UID = _Buf.readLine();
			}
		} catch( Exception X ) {
			Static.Error.out( X.toString() );
		}

		return( UID );
	}
}
