/*
********************************************************************************
* �V�X�e���F ai-land i-mode live BBS for Queen 	                               *
*==============================================================================*
* ���W���[���FEasyDate.java                                                    *
* �N���X���@�FEasyDate                                                         *
* �T�v�@�@�@�F���t�ȈՃN���X                                                   *
* �쐬�@�@�@�F2004/04/20 K.Katafuchi(affinity)                                 *
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
public class EasyDate extends DateTime
{
	public static final int	SUNDAY    = Calendar.getInstance().SUNDAY;
	public static final int	MONDAY    = Calendar.getInstance().MONDAY;
	public static final int	TUESDAY   = Calendar.getInstance().TUESDAY;
	public static final int	WEDNESDAY = Calendar.getInstance().WEDNESDAY;
	public static final int	THURSDAY  = Calendar.getInstance().THURSDAY;
	public static final int	FRIDAY    = Calendar.getInstance().FRIDAY;
	public static final int	SATURDAY  = Calendar.getInstance().SATURDAY;

	private EasyDate_F _ED = new EasyDate_F();
	//--------------------------------------------------------
	// �R���X�g���N�^
	//--------------------------------------------------------
  	public EasyDate()                     { super();     }
  	public EasyDate( String d )           { super(d);    }
  	public EasyDate( String f, String d ) { super(f, d); }
  	public EasyDate( int d )              { super(d);    }
  	public EasyDate( String f, int d )    { super(f, d); }

	//--------------------------------------------------------
	//  ���\�b�h�F�j����Ԃ�
	//--------------------------------------------------------
//	public int getDayOfWeek() { return _ED.getDayOfWeek( super ); }
	public int getDayOfWeek() {
		DateTime a = new DateTime( super.getString()  );
		return _ED.getDayOfWeek( a );
	}

	public String getWeek()   { return _getWeek( false );         }
	public String get�j��()   { return _getWeek( true );          }
	public String get���j��() { return _getWeek( true ) + "�j��"; }

	private String _getWeek( boolean mode )  {
		if(getDayOfWeek() == SUNDAY) {
			return (mode) ? "��" : "Sunday";
		} else if(getDayOfWeek() == MONDAY) {
			return (mode) ? "��" : "Monday";
		} else if(getDayOfWeek() == TUESDAY) {
			return (mode) ? "��" : "Tuesday";
		} else if(getDayOfWeek() == WEDNESDAY) {
			return (mode) ? "��" : "Wendnesday";
		} else if(getDayOfWeek() == THURSDAY) {
			return (mode) ? "��" : "Thursday";
		} else if(getDayOfWeek() == FRIDAY) {
			return (mode) ? "��" : "Friday";
		} else if(getDayOfWeek() == SATURDAY) {
			return (mode) ? "�y" : "Saturday";
		} else {
			return (mode) ? "�H" : "????day";
		}
/*
		switch( getDayOfWeek() ){
			case SUNDAY:    return (mode) ? "��" : "Sunday";
			case MONDAY:    return (mode) ? "��" : "Monday";
			case TUESDAY:   return (mode) ? "��" : "Tuesday";
			case WEDNESDAY: return (mode) ? "��" : "Wendnesday";
			case THURSDAY:  return (mode) ? "��" : "Thursday";
			case FRIDAY:    return (mode) ? "��" : "Friday";
			case SATURDAY:  return (mode) ? "�y" : "Saturday";
			default:        return (mode) ? "�H" : "????day";
		}
*/
	}

	//--------------------------------------------------------
	//  ���\�b�h�F�m���O�E��̓��t��Ԃ�
	//--------------------------------------------------------
//	public int getDays(int count) { return _ED.getDays( super, count ); }
	public int getDays(int count) {
		DateTime a = new DateTime( super.getString()  );
		return _ED.getDays( a, count );
	}
	//--------------------------------------------------------
	//  ���\�b�h�F�m���O�E��̓��t��Ԃ�
	//--------------------------------------------------------
//	public int getMonths(int count) { return _ED.getMonths( super, count ); }
	public int getMonths(int count) {
		DateTime a = new DateTime( super.getString() );
		return _ED.getMonths( a, count );
	}

	//--------------------------------------------------------
	//  ���\�b�h�F��������Ԃ�
	//--------------------------------------------------------
//	public int endOfMonth() { return _ED.endOfMonth( super ); }
	public int endOfMonth() {
		DateTime a = new DateTime( super.getString()  );
		return _ED.endOfMonth( a );
	}
	//--------------------------------------------------------
	// ���\�b�h�F�Q�̓��t�̍�������ŕԂ�
	//--------------------------------------------------------
//	public int diff(int    to_date) { return _ED.diff( super, new DateTime(to_date) ); }
//	public int diff(String to_date) { return _ED.diff( super, new DateTime(to_date) ); }
	public int diff(int    to_date) {
		DateTime a = new DateTime( super.getInt()  );
		return _ED.diff( a, new DateTime(to_date) );
	}
	public int diff(String to_date) {
		DateTime a = new DateTime( super.getString()  );
		return _ED.diff( a, new DateTime(to_date) );
	}
}
