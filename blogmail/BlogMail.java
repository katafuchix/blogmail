/*
********************************************************************************
* �V�X�e���F �T���P�C�X�|�[�c�X�J�E�g�L�����o���@�u���O�X�V�N���X 			   *
*==============================================================================*
* �N���X�@�FBlogMail                 �b���W���[���FBlogMail.java               *
* �T�v�@�@�Fblog@sanspoidol.jp�ɑ����Ă������[����ǂݍ���Ńu���O���X�V     *
* �쐬�@�@�F2006/06/23 K.Katafuchi(affinity)                                   *
*------------------------------------------------------------------------------*
* �X�V�@�@�F																   *
********************************************************************************
*/

/*-- �C���|�[�g --------------------------------------------------------------*/
import javax.mail.*;
import javax.mail.internet.*;
import java.io.*;
import java.util.*;
import manami.system.*;
import manami.util.*;

/*-- �N���X��` --------------------------------------------------------------*/
public class BlogMail {
  static int attachnum = 1;


   static String _Title = "";
   static String _Body  = "";
   static String _Tempu = "";


  public static void main(String args[]) {
	  DateTime dt = new DateTime();
    try {
      //String host="mail.sanspoidol.jp"; 	// �z�X�g�A�h���X
      String host="192.168.10.51"; 

      String user="sanspo-blog"; 			// �A�J�E���g
      String password="2cb3oc"; 			// �p�X���[�h

      // �ڑ����܂�
      Session session = Session.getDefaultInstance(System.getProperties(), null);
      Store store = session.getStore("pop3");
      store.connect(host, -1, user, password);

      // �t�H���_�[���J���܂�
      Folder folder = store.getFolder("INBOX");
	  folder.open(Folder.READ_WRITE);				//�폜�ł���`���ŊJ��
      //folder.open(Folder.READ_ONLY);


      // �t�H���_�[�ɂ��郁�b�Z�[�W�̐����擾���܂�
      int totalMessages = folder.getMessageCount();
      if (totalMessages == 0) {
          Static.Tommy.out( dt.getString() + " ���[���͂O���ł� ");
          folder.close(false);
          store.close();
          return;
      }

      // ���b�Z�[�W���擾���܂�
      Message[] messages = folder.getMessages();
      for (int i = 0; i < messages.length; i++) {
        // ���b�Z�[�W��\�����܂�
        dumpPart(messages[i]);
				messages[i].setFlag(Flags.Flag.DELETED, true); 	//�폜�t���O�����ă��b�Z�[�W�擾
      }

      // �t�H���_�[����܂�
      folder.close(true);
      store.close();
    } catch (Exception e) {
      e.printStackTrace();
	Static.Error.out(e.toString());
    }


	DB db = new DB();
	String _time   	=  dt.getString("yyyy") + "-" + dt.getString("MM") + "-" + dt.getString("dd") + " "
				   	+ dt.getString("HH") + ":" + dt.getString("mm") + ":" + dt.getString("ss") ;
	try{

		db.connect();
		db.create();
	
		String _sql = " INSERT INTO nucleus_item "
 					+ " (ITITLE, IBODY, IMORE, IBLOG, IAUTHOR, ITIME, ICLOSED, IDRAFT, ICAT) "
					+ " VALUES "
					+ " ('" + _Title + "', '" + _Body + "', '', 1, 1, '" + _time + "', 0, 0, 1)";

		
		db.prepare(_sql);
		db.insert();
		db.commit();

	}catch(Exception ex){
		Static.Error.out(ex.toString());
	}

	try{
		db.close();
		db.disconnect();
	}catch(Exception ex){
		Static.Error.out(ex.toString());
	}



  }


  public static void dumpPart(Part p) throws Exception {
    String html = "";
    boolean attachment = false;

    if (p instanceof Message) {
      	showMessage((Message)p);
    }

    if (p.isMimeType("text/plain")) { 					// �e�L�X�g�̏ꍇ
      	Static.Error.out("���e�F\n" + p.getContent());
		_Body = p.getContent().toString();
    } else if (p.isMimeType("multipart/*")) { 			// �}���`�p�[�g�̏ꍇ
      	Multipart mp = (Multipart)p.getContent();
      	int count = mp.getCount();
      	for (int i = 0; i < count; i++) {
        	dumpPart(mp.getBodyPart(i));
      	}
    } else if (p.isMimeType("message/rfc822")) { 		// ���b�Z�[�W�̏ꍇ
      	dumpPart((Part)p.getContent());
    } else if (p.isMimeType("text/html")) { 			// HTML�̏ꍇ
      	html = ".html";
      	attachment = true;
    } else { 											// ���̑��̏ꍇ
      	attachment = true;
    }	



	  //////////////////////////////
	 // �Y�t�t�@�C����ۑ����܂� //
	//////////////////////////////

    if (attachment) {
      String disp = p.getDisposition();

      // �Y�t�t�@�C���̏ꍇ
      if (disp == null || disp.equalsIgnoreCase(Part.ATTACHMENT)) {
        	String filename = p.getFileName();
		
			DateTime dt = new DateTime();
			String Time = dt.getString();

Static.Tommy.out(" TIME : " + dt.getString("HH") + ":" + dt.getString("mm") + ":" + dt.getString("ss") );

Static.Tommy.out( " filename : " + filename );

        try {

			int i = filename.lastIndexOf('.');
			String 	extension = filename.substring(i+1).toLowerCase();

			//if(	extension.equals("jpg")
			//	|| extension.equals("jpeg") 
			//	|| extension.equals("gif") ){

				//String cp = "cp  " + filename  + " "  + Time + "." + extension;

         		 File f = new File(filename);

         		 if (f.exists()) {			//�����̃t�@�C�������݂���ꍇ
					f.delete();				// �t�@�C�����폜
					f.createNewFile();		// �t�@�C�����쐬
          		}

          		OutputStream os = new BufferedOutputStream(new FileOutputStream(f));
         	 	InputStream is = p.getInputStream();
          		int c;
          		while ((c = is.read()) != -1) {
          	  		os.write(c);
          		}
         	 	os.close();


          		Static.Tommy.out( Time + "." + filename + "��ۑ����܂����B");


				//�u���O���猩����ꏊ�ɃR�s�[����
				// �f�B���N�g���쐬
				String _dir = "/home/sansupo/htdocs/nucleus/media/" + Time;
				File _f 	= new File( _dir );
				_f.mkdir();				


				Runtime rt		=	Runtime.getRuntime();
				String cp = "cp "  + filename +  "  " + _dir + "/" + filename;

				rt.exec(cp).waitFor();				

          		Static.Tommy.out( filename + " ���ړ����܂����B");

			//}
		} catch (IOException e) {
        	  	Static.Error.out("�Y�t�t�@�C���̕ۑ��Ɏ��s���܂����B" + e);
		}


      }
    }
  }


	  //////////////////////////////
	 // ���[���̓��e��ۑ����܂� //
	//////////////////////////////
/*
BLOG.php �̃f�[�^�쐬���������s
$query = 'INSERT INTO '.sql_table('item').' (ITITLE, IBODY, IMORE, IBLOG, IAUTHOR, ITIME, ICLOSED, IDRAFT, ICAT) '
. "VALUES ('$title', '$body', '$more', $blogid, $authorid, '$timestamp', $closed, $draft, $catid)";
*/
  public static void showMessage(Message m) throws Exception {

    DateTime dt 	= new DateTime();
	String _time   	=  dt.getString("yyyy") + "-" + dt.getString("MM") + "-" + dt.getString("dd") + " "
				   	+ dt.getString("HH") + ":" + dt.getString("mm") + ":" + dt.getString("ss") ;

    Address[] a;

    // ���o�l
    if ((a = m.getFrom())!=null) {

			Static.Tommy.out( "  " );
			Static.Tommy.out( "------------------------------------------------" );

      for (int j = 0; j < a.length; j++) {
			Static.Tommy.out("���o�l: " + MimeUtility.decodeText(a[j].toString()));
      }
    }

    // ����
    if ((a = m.getRecipients(Message.RecipientType.TO))!=null) {
      for (int j = 0; j < a.length; j++) {
			Static.Tommy.out("����: " + MimeUtility.decodeText(a[j].toString()));
      }
    }

    // �薼
	Static.Tommy.out("�薼: " + m.getSubject());

	_Title = (String)Null.replace( m.getSubject(),"" );
	if( _Title.length() ==0 ) _Title = dt.getString("yyyy") + "�N" + dt.getString("MM") + "��" + dt.getString("dd") + "��";


    // ���t
    Date d = m.getSentDate();
	Static.Tommy.out("���t: " + (d!= null ? d.toString() : "�s��"));


    // �T�C�Y
	Static.Tommy.out("�T�C�Y: " + m.getSize());

	
  }
}
