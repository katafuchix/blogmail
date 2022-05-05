/*
********************************************************************************
* �V�X�e���F ai-land i-mode live BBS for Queen 		                           *
*==============================================================================*
* ���W���[���FEasyDate_F.java                                                  *
* �N���X���@�FEasyDate_F                                                       *
* �T�v�@�@�@�F���t�ȈՃN���X                                                   *
* �쐬�@�@�@�F2004/04/10 K.Katafuchi(affinity)                                 *
*------------------------------------------------------------------------------*
* �X�V�@�@�F2004/07/20 K.Katafuchi(affinity)								   *
*			ai-land i-mode ���ڐA											   *
********************************************************************************
*/

/*-- �p�b�P�[�W --------------------------------------------------------------*/
package manami.util;

/*-- �C���|�[�g --------------------------------------------------------------*/
import java.lang.Math;
import java.util.Calendar;
import manami.util.DateTime;

/*-- �N���X��` --------------------------------------------------------------*/
public class EasyDate_F extends DateTime
{
	//--------------------------------------------------------
	//  ���\�b�h�F�w�������j����Ԃ�
	//--------------------------------------------------------
	public int getDayOfWeek(String date) { return getDayOfWeek( new DateTime(date) ); }
	public int getDayOfWeek(int    date) { return getDayOfWeek( new DateTime(date) ); }
	public int getDayOfWeek(DateTime dt) {
		Calendar cl = Calendar.getInstance();
		cl.set( dt.getInt("yyyy"), dt.getInt("MM")-1, dt.getInt("dd") );
		return cl.get( cl.DAY_OF_WEEK );
	}

	//--------------------------------------------------------
	// ���\�b�h�F�w�������m���O�E��̓��t��Ԃ�
	//--------------------------------------------------------
	public int getDays(int    date, int count) { return getDays( new DateTime(date), count ); }
	public int getDays(String date, int count) { return getDays( new DateTime(date), count ); }
	public int getDays(DateTime dt, int count)
	{
		DateTime wk = new DateTime( dt.getInt() );
		int f = (count<0) ? -1 : 1;
		for(int i=0; i<Math.abs(count); i++ ){
			wk.setInt( wk.getInt() + f );
		}
		return wk.getInt();
	}

	//--------------------------------------------------------
	// ���\�b�h�F�w�������m���O�E��̓��t��Ԃ�
	//--------------------------------------------------------
	public int getMonths(int    date, int count) { return getMonths( new DateTime(date), count ); }
	public int getMonths(String date, int count) { return getMonths( new DateTime(date), count ); }
	public int getMonths(DateTime dt, int count)
	{
		int nY = dt.getInt( "yyyy" );
		int nM = dt.getInt( "MM" );
		int nD = dt.getInt( "dd" );

		int f = (count<0) ? -1 : 1;

		for(int i=0; i<Math.abs(count); i++ ){
			nM += f;
			if( nM > 12 ){
				nM = 1;
				nY += f;
			}
			if( nM < 1 ){
				nM = 12;
				nY += f;
			}
		}

		// �����"dd"���m����̌��������z���Ă���ꍇ�͒���������B
		int n_end = endOfMonth( nY * 10000 + nM * 100 + 1 );
		if( n_end < nD ) nD = n_end;

		return nY * 10000 + nM * 100 + nD;
	}

	//--------------------------------------------------------
	//  ���\�b�h�F�w������猎������Ԃ�
	//--------------------------------------------------------
	public int endOfMonth(int    date) { return endOfMonth( new DateTime(date) ); }
	public int endOfMonth(String date) { return endOfMonth( new DateTime(date) ); }
	public int endOfMonth(DateTime dt)
	{
		/*****************
		DateTime wk = new DateTime( dt.getInt("yyyyMM") * 100 + 28);

		int m_end = 0;
		while( dt.getInt("MM") == wk.getInt("MM") ){
			m_end = wk.getInt("dd");
			wk.setInt( wk.getInt("yyyyMMdd") + 1 );
		}
		return m_end;
		*******************/

		int ny = dt.getInt("yyyy");
		int nm = dt.getInt("MM");

		if( nm == 2 ){
			if( ny%4 == 0 && ny%100 != 0 || ny%400 == 0 ) return( 29 );
			else                                          return( 28 );
		} else {
			int n_abs = (int)Math.round( Math.abs(7.5-nm) );
			return( (n_abs % 2) + 30 );
		}
	}

	//--------------------------------------------------------
	// ���\�b�h�F�Q�̓��t�̍�������ŕԂ�
	//--------------------------------------------------------
	public int diff(int      nfm, int      nto) { return diff( new DateTime(nfm), new DateTime(nto) ); }
	public int diff(String   sfm, String   sto) { return diff( new DateTime(sfm), new DateTime(sto) ); }
	public int diff(DateTime efm, DateTime eto) {
		int nfm = efm.getInt();
		int nto = eto.getInt();
		int adjust = 1;
		if( nfm > nto ) {
			int tmp = nto;
			nto     = nfm;
			nfm     = tmp;
			adjust  = -1;
		}
		Calendar cfm = Calendar.getInstance();
		Calendar cto = Calendar.getInstance();
		DateTime dfm = new DateTime( nfm );
		DateTime dto = new DateTime( nto );

		cfm.clear();
		cto.clear();
		cfm.set( dfm.getInt("yyyy"), dfm.getInt("MM")-1, dfm.getInt("dd") );
		cto.set( dto.getInt("yyyy"), dto.getInt("MM")-1, dto.getInt("dd") );

		int d_cnt = 0;

		// �����N�̏ꍇ
		if( dfm.getInt("yyyyy") == dto.getInt("yyyyy") ) {
			d_cnt = cto.get(cto.DAY_OF_YEAR) - cfm.get(cfm.DAY_OF_YEAR);
		}
		// �Ⴄ�N�̏ꍇ
		else {
			int df = cfm.get(cfm.DAY_OF_YEAR);
			d_cnt  = cto.get(cto.DAY_OF_YEAR);

			cfm.set( dfm.getInt("yyyy"), 12-1, 31 );	// �N�����ɕύX
			for (int yy=dfm.getInt("yyyy"); yy<dto.getInt("yyyy"); yy++ ) {
				d_cnt += ( cfm.get(cfm.DAY_OF_YEAR) - df );
				if( yy == dfm.getInt("yyyy") ) df = 0;
				cfm.roll( cfm.YEAR, true );
			}
		}

		return( d_cnt * adjust );
	}
}
