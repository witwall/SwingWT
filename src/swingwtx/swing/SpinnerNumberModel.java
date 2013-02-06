/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/

package swingwtx.swing;

public class SpinnerNumberModel extends AbstractSpinnerModel {

    protected Comparable minimum;
    protected Comparable maximum;
    protected Number stepSize;
    protected Number value;

    public SpinnerNumberModel() { this(new Integer(0), null, null, new Integer(1)); }
    public SpinnerNumberModel(int value, int minimum, int maximum, int stepSize) { this(new Integer(value), new Integer(minimum), new Integer(maximum), new Integer(stepSize)); }
    public SpinnerNumberModel(double value, double minimum, double maximum, double stepSize) { this(new Double(value), new Double(minimum), new Double(maximum), new Double(stepSize)); }
    public SpinnerNumberModel(Number value, Comparable minimum, Comparable maximum, Number stepSize) {
	this.value = value;
	this.minimum = minimum;
	this.maximum = maximum;
	this.stepSize = stepSize;
    }

    public void setMinimum(Comparable minimum) {
        this.minimum = minimum;
	fireStateChanged();
    }

    public Comparable getMinimum() {
	return minimum;
    }

    public void setMaximum(Comparable maximum) {
	this.maximum = maximum;
	fireStateChanged();
    }

    public Comparable getMaximum() {
	return maximum;
    }

    public void setStepSize(Number stepSize) {
        this.stepSize = stepSize;
        fireStateChanged();
    }

    public Number getStepSize() {
	return stepSize;
    }
    
    public Object getNextValue() {
	return nextValue(+1);
    }

    public Object getPreviousValue() {
	return nextValue(-1);
    }

    public Number getNumber() {
	return value;
    }

    public Object getValue() {
	return value;
    }
    
    public void setValue(Object value) {
        this.value = (Number)value;
        fireStateChanged();
    }

    protected Number nextValue(int direction) {
	Number newValue;
	if ((value instanceof Float) || (value instanceof Double)) {
            
	    double v = value.doubleValue() + (stepSize.doubleValue() * (double) direction);
	    if (value instanceof Double) {
		newValue = new Double(v);
	    }
	    else {
		newValue = new Float(v);
	    }
	}
	else {
            
	    long v = value.longValue() + (stepSize.longValue() * (long) direction);

	    if (value instanceof Long) {
		newValue = new Long(v);
	    }
	    else if (value instanceof Integer) {
		newValue = new Integer((int)v);
	    }
	    else if (value instanceof Short) {
		newValue = new Short((short)v);
	    }
	    else {
		newValue = new Byte((byte)v);
	    }
	}

	if ((maximum != null) && (maximum.compareTo(newValue) < 0)) {
	    return getNumber();
	}
	if ((minimum != null) && (minimum.compareTo(newValue) > 0)) {
	    return getNumber();
	}
	else {
	    return newValue;
	}
    }
    
}

