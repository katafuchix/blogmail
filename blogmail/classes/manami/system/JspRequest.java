/*
********************************************************************************
* �V�X�e���Fai-land i-mode live BBS for Queen 	                               *
*==============================================================================*
* ���W���[���FJspRequest.java                                                  *
* �N���X���@�FJspRequest                                                       *
* �T�v�@�@�@�FJSP��request�I�u�W�F�N�g�̔ėp�N���X                             *
* �쐬�@�@�@�F2004/04/20 K.Katafuchi(affinity)                                 *
*------------------------------------------------------------------------------*
* �X�V�@�@�F2004/07/20 K.Katafuchi(affinity)								   *
*			ai-land i-mode ���ڐA											   *
********************************************************************************
*/

/*-- �p�b�P�[�W --------------------------------------------------------------*/
package manami.system;

/*-- �C���|�[�g --------------------------------------------------------------*/
import java.io.IOException;
import java.util.*;
import java.net.*;
import javax.servlet.*;

/*-- �N���X��` --------------------------------------------------------------*/
public class JspRequest
{
	static    String     Classname = "JspRequest";

	static    boolean    _IsGetProperty = false;
	static    String     _Encode;

	private   ServletRequest _Request = null;

	public JspRequest() {
		_initialize();
	}
	public JspRequest( ServletRequest request ) {
		_Request = request;
		_initialize();
	}

	private void _initialize() {

		// �v���p�e�B�擾
		if( ! _IsGetProperty ) {
			Property P = new Property();
			P.open( Classname );
			_Encode = P.getString( "Encode" );
			_IsGetProperty = true;
		}

	}

	//--------------------------------------------------------
	// ���\�b�h�F�p�����[�^�擾
	//
	// �߂�l  �F�f�R�[�h�ς݂̒l
	//
	// ����    �F�^              �ϐ���         ���e
	//           ---------------------------------------------
	//           ServletRequest  cReq           ���N�G�X�g�I�u�W�F�N�g
	//           String          parameterName  �p�����[�^
	//--------------------------------------------------------
	public String getParameter( ServletRequest cReq, String parameterName ) {

		String Result = null;
		try {
			String Encode = cReq.getParameter( "CHAT_Encoding" );
			if( Encode == null ) Encode = _Encode;

			String Parameter = cReq.getParameter( parameterName );
			if( Parameter != null ) {
				Result = new String( Parameter.getBytes("8859_1"), Encode );
//				Result = URLDecoder.decode( Parameter );
			}
		} catch( Exception X ) {
		} finally {
		//return Result; 
		}
		return Result; 
	}
	public String getParameter(String parameterName)
	{
		return( getParameter(_Request, parameterName) );
	}

	public String[] getParameterNames( ServletRequest cReq ) throws IOException {

		String Encode = cReq.getParameter( "CHAT_Encoding" );
		if( Encode == null ) Encode = _Encode;

		Enumeration edata = cReq.getParameterNames();
		Vector vdata = new Vector();
		while( edata.hasMoreElements() ) {
			vdata.add( edata.nextElement() );
        }
		String data[] = new String[vdata.size()];
		edata = vdata.elements();
		for( int i = 0; edata.hasMoreElements(); i ++ ) {
			String sdata = ( String )edata.nextElement();
			data[i] = new String( sdata.getBytes( "8859_1" ), Encode );
//			data[i] = URLDecoder.decode( sdata );
		}
		return data;
	}
	public String[] getParameterNames() throws IOException {
		return( getParameterNames( _Request ) );
	}

	public String[] getParameterValues( ServletRequest cReq, String parameterName ) throws IOException {

		String Encode = cReq.getParameter( "CHAT_Encoding" );
		if( Encode == null ) Encode = _Encode;

		String  data[] = cReq.getParameterValues( parameterName );
		if( data != null ) {
			for( int i = 0; i < data.length; i ++ ) {
				data[i] = new String( data[i].getBytes( "8859_1" ), Encode );
//				data[i] = URLDecoder.decode( data[i] );
			}
		}
		return data;
	}
	public String[] getParameterValues( String parameterName ) throws IOException {
		return( getParameterValues( _Request, parameterName ) );
	}

	//--------------------------------------------------------
	// ���\�b�h�F�p�����[�^�擾
	//
	// �߂�l  �F�f�R�[�h�ς݂̒l(Hashtable)
	//
	// ����    �F�^              �ϐ���         ���e
	//           ---------------------------------------------
	//           ServletRequest  cReq           ���N�G�X�g�I�u�W�F�N�g
	//--------------------------------------------------------
	public Hashtable getParameters( ServletRequest cReq ) throws IOException {

		String Encode = cReq.getParameter( "CHAT_Encoding" );
		if( Encode == null ) Encode = _Encode;

		Hashtable data    = new Hashtable();
		String    Names[] = getParameterNames( cReq );

		for( int i = 0; i < Names.length; i ++ ) {
			String Values[] = getParameterValues( cReq, Names[i] );
			data.put( Names[i], new String( Values[0].getBytes( "8859_1" ), Encode ) );
//			data.put( Names[i], URLDecoder.decode( Values[0] ) );
		}
		return data;
	}
	public Hashtable getParameters() throws IOException {
		return( getParameters( _Request ) );
	}

}
