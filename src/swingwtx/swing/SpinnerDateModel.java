/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/

package swingwtx.swing;

import java.util.*;

public class SpinnerDateModel extends AbstractSpinnerModel {
    
    private Comparable start, end;
    private Calendar value;
    private int calendarField;

    public SpinnerDateModel() { this(new Date(), null, null, Calendar.DAY_OF_MONTH); }
    public SpinnerDateModel(Date value, Comparable start, Comparable end, int calendarField) {
	this.start = start;
	this.end = end;
	this.calendarField = calendarField;
        this.value = Calendar.getInstance();
	this.value.setTime(value);
    }

    public void setStart(Comparable start) {
        this.start = start;
        fireStateChanged();
    }

    public Comparable getStart() {
	return start;
    }
    
    public void setEnd(Comparable end) {
        this.end = end;
        fireStateChanged();
    }

    public Comparable getEnd() {
	return end;
    }

    public void setCalendarField(int calendarField) {
        this.calendarField = calendarField;
        fireStateChanged();
    } 

    public int getCalendarField() {
	return calendarField;
    }

    public Object getNextValue() {
	Calendar cal = Calendar.getInstance();
	cal.setTime(value.getTime());
	cal.add(calendarField, 1);
	Date next = cal.getTime();
	return ((end == null) || (end.compareTo(next) >= 0)) ? next : null;
    }

    public Object getPreviousValue() {
	Calendar cal = Calendar.getInstance();
	cal.setTime(value.getTime());
	cal.add(calendarField, -1);
	Date prev = cal.getTime();
	return ((start == null) || (start.compareTo(prev) <= 0)) ? prev : null;
    }

    public Date getDate() {
	return value.getTime();
    }

    public Object getValue() {
	return value.getTime();
    }

    public void setValue(Object value) {
        this.value.setTime((Date)value);
        fireStateChanged();
    }
}
