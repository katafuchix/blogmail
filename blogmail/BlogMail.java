/*
********************************************************************************
* システム： サンケイスポーツスカウトキャラバン　ブログ更新クラス 			   *
*==============================================================================*
* クラス　：BlogMail                 ｜モジュール：BlogMail.java               *
* 概要　　：blog@sanspoidol.jpに送られてきたメールを読み込んでブログを更新     *
* 作成　　：2006/06/23 K.Katafuchi(affinity)                                   *
*------------------------------------------------------------------------------*
* 更新　　：																   *
********************************************************************************
*/

/*-- インポート --------------------------------------------------------------*/
import javax.mail.*;
import javax.mail.internet.*;
import java.io.*;
import java.util.*;
import manami.system.*;
import manami.util.*;

/*-- クラス定義 --------------------------------------------------------------*/
public class BlogMail {
  static int attachnum = 1;


   static String _Title = "";
   static String _Body  = "";
   static String _Tempu = "";


  public static void main(String args[]) {
	  DateTime dt = new DateTime();
    try {
      //String host="mail.sanspoidol.jp"; 	// ホストアドレス
      String host="192.168.10.51"; 

      String user="sanspo-blog"; 			// アカウント
      String password="2cb3oc"; 			// パスワード

      // 接続します
      Session session = Session.getDefaultInstance(System.getProperties(), null);
      Store store = session.getStore("pop3");
      store.connect(host, -1, user, password);

      // フォルダーを開きます
      Folder folder = store.getFolder("INBOX");
	  folder.open(Folder.READ_WRITE);				//削除できる形式で開く
      //folder.open(Folder.READ_ONLY);


      // フォルダーにあるメッセージの数を取得します
      int totalMessages = folder.getMessageCount();
      if (totalMessages == 0) {
          Static.Tommy.out( dt.getString() + " メールは０件です ");
          folder.close(false);
          store.close();
          return;
      }

      // メッセージを取得します
      Message[] messages = folder.getMessages();
      for (int i = 0; i < messages.length; i++) {
        // メッセージを表示します
        dumpPart(messages[i]);
				messages[i].setFlag(Flags.Flag.DELETED, true); 	//削除フラグをつけてメッセージ取得
      }

      // フォルダーを閉じます
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

    if (p.isMimeType("text/plain")) { 					// テキストの場合
      	Static.Error.out("内容：\n" + p.getContent());
		_Body = p.getContent().toString();
    } else if (p.isMimeType("multipart/*")) { 			// マルチパートの場合
      	Multipart mp = (Multipart)p.getContent();
      	int count = mp.getCount();
      	for (int i = 0; i < count; i++) {
        	dumpPart(mp.getBodyPart(i));
      	}
    } else if (p.isMimeType("message/rfc822")) { 		// メッセージの場合
      	dumpPart((Part)p.getContent());
    } else if (p.isMimeType("text/html")) { 			// HTMLの場合
      	html = ".html";
      	attachment = true;
    } else { 											// その他の場合
      	attachment = true;
    }	



	  //////////////////////////////
	 // 添付ファイルを保存します //
	//////////////////////////////

    if (attachment) {
      String disp = p.getDisposition();

      // 添付ファイルの場合
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

         		 if (f.exists()) {			//同名のファイルが存在する場合
					f.delete();				// ファイルを削除
					f.createNewFile();		// ファイルを作成
          		}

          		OutputStream os = new BufferedOutputStream(new FileOutputStream(f));
         	 	InputStream is = p.getInputStream();
          		int c;
          		while ((c = is.read()) != -1) {
          	  		os.write(c);
          		}
         	 	os.close();


          		Static.Tommy.out( Time + "." + filename + "を保存しました。");


				//ブログから見える場所にコピーする
				// ディレクトリ作成
				String _dir = "/home/sansupo/htdocs/nucleus/media/" + Time;
				File _f 	= new File( _dir );
				_f.mkdir();				


				Runtime rt		=	Runtime.getRuntime();
				String cp = "cp "  + filename +  "  " + _dir + "/" + filename;

				rt.exec(cp).waitFor();				

          		Static.Tommy.out( filename + " を移動しました。");

			//}
		} catch (IOException e) {
        	  	Static.Error.out("添付ファイルの保存に失敗しました。" + e);
		}


      }
    }
  }


	  //////////////////////////////
	 // メールの内容を保存します //
	//////////////////////////////
/*
BLOG.php のデータ作成部分を実行
$query = 'INSERT INTO '.sql_table('item').' (ITITLE, IBODY, IMORE, IBLOG, IAUTHOR, ITIME, ICLOSED, IDRAFT, ICAT) '
. "VALUES ('$title', '$body', '$more', $blogid, $authorid, '$timestamp', $closed, $draft, $catid)";
*/
  public static void showMessage(Message m) throws Exception {

    DateTime dt 	= new DateTime();
	String _time   	=  dt.getString("yyyy") + "-" + dt.getString("MM") + "-" + dt.getString("dd") + " "
				   	+ dt.getString("HH") + ":" + dt.getString("mm") + ":" + dt.getString("ss") ;

    Address[] a;

    // 差出人
    if ((a = m.getFrom())!=null) {

			Static.Tommy.out( "  " );
			Static.Tommy.out( "------------------------------------------------" );

      for (int j = 0; j < a.length; j++) {
			Static.Tommy.out("差出人: " + MimeUtility.decodeText(a[j].toString()));
      }
    }

    // 宛先
    if ((a = m.getRecipients(Message.RecipientType.TO))!=null) {
      for (int j = 0; j < a.length; j++) {
			Static.Tommy.out("宛先: " + MimeUtility.decodeText(a[j].toString()));
      }
    }

    // 題名
	Static.Tommy.out("題名: " + m.getSubject());

	_Title = (String)Null.replace( m.getSubject(),"" );
	if( _Title.length() ==0 ) _Title = dt.getString("yyyy") + "年" + dt.getString("MM") + "月" + dt.getString("dd") + "日";


    // 日付
    Date d = m.getSentDate();
	Static.Tommy.out("日付: " + (d!= null ? d.toString() : "不明"));


    // サイズ
	Static.Tommy.out("サイズ: " + m.getSize());

	
  }
}
