/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net

*/


package swingwtx.swing;

import java.util.*;

public class SpinnerListModel extends AbstractSpinnerModel {
    
    private List list;
    private int index;

    public SpinnerListModel(List values) {
	this.list = values;
	this.index = 0;
    }

    public SpinnerListModel(Object[] values) {
	this.list = Arrays.asList(values);
        this.index = 0;
    }

    public SpinnerListModel() {
	this(new Object[]{ "empty" });
    }

    public List getList() {
	return list;
    }

    public void setList(List list) {
        this.list = list;
        index = 0;
        fireStateChanged();
    }

    public Object getValue() {
	return list.get(index);
    }

    public void setValue(Object elt) {
	this.index = list.indexOf(elt);
	fireStateChanged();
    }

    public Object getNextValue() {
	return (index >= (list.size() - 1)) ? null : list.get(index + 1);
    }

    public Object getPreviousValue() {
	return (index <= 0) ? null : list.get(index - 1);
    }

}

