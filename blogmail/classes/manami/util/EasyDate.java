/*
********************************************************************************
* システム： ai-land i-mode live BBS for Queen 	                               *
*==============================================================================*
* モジュール：EasyDate.java                                                    *
* クラス名　：EasyDate                                                         *
* 概要　　　：日付簡易クラス                                                   *
* 作成　　　：2004/04/20 K.Katafuchi(affinity)                                 *
*------------------------------------------------------------------------------*
* 更新　　：2004/07/20 K.Katafuchi(affinity)								   *
*			ai-land i-mode より移植											   *
********************************************************************************
*/

/*-- パッケージ --------------------------------------------------------------*/
package manami.util;

/*-- インポート --------------------------------------------------------------*/
import java.lang.Math;
import java.util.Calendar;
import manami.util.DateTime;

/*-- クラス定義 --------------------------------------------------------------*/
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
	// コンストラクタ
	//--------------------------------------------------------
  	public EasyDate()                     { super();     }
  	public EasyDate( String d )           { super(d);    }
  	public EasyDate( String f, String d ) { super(f, d); }
  	public EasyDate( int d )              { super(d);    }
  	public EasyDate( String f, int d )    { super(f, d); }

	//--------------------------------------------------------
	//  メソッド：曜日を返す
	//--------------------------------------------------------
//	public int getDayOfWeek() { return _ED.getDayOfWeek( super ); }
	public int getDayOfWeek() {
		DateTime a = new DateTime( super.getString()  );
		return _ED.getDayOfWeek( a );
	}

	public String getWeek()   { return _getWeek( false );         }
	public String get曜日()   { return _getWeek( true );          }
	public String get何曜日() { return _getWeek( true ) + "曜日"; }

	private String _getWeek( boolean mode )  {
		if(getDayOfWeek() == SUNDAY) {
			return (mode) ? "日" : "Sunday";
		} else if(getDayOfWeek() == MONDAY) {
			return (mode) ? "月" : "Monday";
		} else if(getDayOfWeek() == TUESDAY) {
			return (mode) ? "火" : "Tuesday";
		} else if(getDayOfWeek() == WEDNESDAY) {
			return (mode) ? "水" : "Wendnesday";
		} else if(getDayOfWeek() == THURSDAY) {
			return (mode) ? "木" : "Thursday";
		} else if(getDayOfWeek() == FRIDAY) {
			return (mode) ? "金" : "Friday";
		} else if(getDayOfWeek() == SATURDAY) {
			return (mode) ? "土" : "Saturday";
		} else {
			return (mode) ? "？" : "????day";
		}
/*
		switch( getDayOfWeek() ){
			case SUNDAY:    return (mode) ? "日" : "Sunday";
			case MONDAY:    return (mode) ? "月" : "Monday";
			case TUESDAY:   return (mode) ? "火" : "Tuesday";
			case WEDNESDAY: return (mode) ? "水" : "Wendnesday";
			case THURSDAY:  return (mode) ? "木" : "Thursday";
			case FRIDAY:    return (mode) ? "金" : "Friday";
			case SATURDAY:  return (mode) ? "土" : "Saturday";
			default:        return (mode) ? "？" : "????day";
		}
*/
	}

	//--------------------------------------------------------
	//  メソッド：Ｎ日前・後の日付を返す
	//--------------------------------------------------------
//	public int getDays(int count) { return _ED.getDays( super, count ); }
	public int getDays(int count) {
		DateTime a = new DateTime( super.getString()  );
		return _ED.getDays( a, count );
	}
	//--------------------------------------------------------
	//  メソッド：Ｎ月前・後の日付を返す
	//--------------------------------------------------------
//	public int getMonths(int count) { return _ED.getMonths( super, count ); }
	public int getMonths(int count) {
		DateTime a = new DateTime( super.getString() );
		return _ED.getMonths( a, count );
	}

	//--------------------------------------------------------
	//  メソッド：月末日を返す
	//--------------------------------------------------------
//	public int endOfMonth() { return _ED.endOfMonth( super ); }
	public int endOfMonth() {
		DateTime a = new DateTime( super.getString()  );
		return _ED.endOfMonth( a );
	}
	//--------------------------------------------------------
	// メソッド：２つの日付の差を日数で返す
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
