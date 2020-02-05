package ng.com.neodezigns.app.ws.custom;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

public class CustomMethods {

	public static Date parseDateFromArray(final ArrayList<Integer> array) throws ParseException {
		final GregorianCalendar calendar = new GregorianCalendar(array.get(0), array.get(1), array.get(2));
		final SimpleDateFormat format = new SimpleDateFormat("dd MMMM yyyy");
		return format.parse(format.format(calendar.getTime()));
	}

	public static ArrayList<Integer> parseDateToArray(final Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String strDate = formatter.format(date);
		String[] str = strDate.split("-");
		ArrayList<Integer> l = new ArrayList<Integer>();
		l.add(Integer.parseInt(str[0]));
		l.add(Integer.parseInt(str[1]));
		l.add(Integer.parseInt(str[2]));
		System.out.println(l);
		return l;

	}
}
