/*
 SwingWT
 Copyright(c)2003-2008, R. Rawson-Tetley

 For more information on distributing and using this program, please
 see the accompanying "COPYING" file.

 Contact me by electronic mail: bobintetley@users.sourceforge.net


 */

package swingwtx.swing;

import swingwtx.swing.event.*;
import swingwt.awt.Container;
import swingwt.awt.Dimension;
import swingwt.awt.Rectangle;

import org.eclipse.swt.widgets.*;
import org.eclipse.swt.*;

import java.util.*;

public class JListTable extends swingwtx.swing.JList implements Scrollable,
		ListDataListener {

	protected org.eclipse.swt.widgets.Table ppeer = null;

	protected String pText = "";

	protected int pSelectedIndex = -1;

	protected ListCellRenderer cellRenderer = new DefaultListCellRenderer();

	protected ListModel listModel = null;

	protected ListSelectionModel listSelectionModel = new DefaultListSelectionModel(
			this);

	/** Thread safe property accessors */
	private Object[] retVals = null;

	private int iRetVal = 0;

	private int[] iRetVals = null;

	// Use this to set fixed CellWidth
	private int fixedCellWidth = -1;

	public JListTable() {
		listModel = new DefaultListModel();
		listModel.addListDataListener(this);
	}

	/**
	 * Creates a list with the specified items. WARNING:: SWT cannot store
	 * objects against the list like Swing can, so only the toString() method of
	 * your objects will be stored.
	 */
	public JListTable(Object[] items) {
		loadData(items);
	}

	/**
	 * Creates a list with the specified items. WARNING:: SWT cannot store
	 * objects against the list like Swing can, so only the toString() method of
	 * your objects will be stored.
	 */
	public JListTable(Vector items) {
		loadData(items);
	}

	public JListTable(ListModel model) {
		listModel = model;
		listModel.addListDataListener(this);
	}

	public void addListSelectionListener(ListSelectionListener l) {
		listSelectionModel.addListSelectionListener(l);
	}

	public void removeListSelectionListener(ListSelectionListener l) {
		listSelectionModel.removeListSelectionListener(l);
	}

	public void clearSelection() {
		ppeer.deselectAll();
	}

	public Object getSelectedValue() {
		if (!SwingWTUtils.isSWTControlAvailable(ppeer))
			// Since we know that the selected item in the list will be the
			// first item in the data model,
			// we can return that if we have a non-empty model.
			if (getModel().getSize() > 0)
				return getModel().getElementAt(0);
			else
				return null;
		else
			return listModel.getElementAt(ppeer.getSelectionIndex());
	}

	public void setSelectedValue(final int index) {
		SwingUtilities.invokeSync(new Runnable() {
			public void run() {
				if (!SwingWTUtils.isSWTControlAvailable(ppeer))
					pSelectedIndex = index;
				else
					ppeer.select(index);
			}
		});
	}

	protected int indexOf(Object value) {

		int index = -1;

		if (checkIsDefault())
			index = ((DefaultListModel) listModel).indexOf(value);
		else {
			for (int i = 0; i < listModel.getSize(); i++) {
				if (listModel.getElementAt(i).equals(value)) {
					index = i;
					break;
				}
			}
		}

		return index;
	}

	public void setSelectedValue(final Object value, boolean shouldscroll) {
		final boolean scroll = shouldscroll;
		SwingUtilities.invokeSync(new Runnable() {
			public void run() {
				if (!SwingWTUtils.isSWTControlAvailable(ppeer))
					pSelectedIndex = indexOf(value);
				else
					ppeer.select(indexOf(value));

				if (scroll)
					ppeer.showSelection();
			}
		});
	}

	public ListSelectionModel getSelectionModel() {
		return listSelectionModel;
	}

	public void setSelectionModel(ListSelectionModel l) {
		listSelectionModel = l;
	}

	public void setSelectionMode(int mode) {
		listSelectionModel.setSelectionMode(mode);
	}

	public Object[] getSelectedValues() {
		if (!SwingWTUtils.isSWTControlAvailable(ppeer))
			return null;
		retVals = null;
		SwingUtilities.invokeSync(new Runnable() {
			public void run() {
				retVals = new Object[ppeer.getSelectionCount()];
				for (int i = 0; i < ppeer.getSelectionIndices().length; i++) {
					retVals[i] = listModel.getElementAt(ppeer
							.getSelectionIndices()[i]);
				}
			}
		});
		return retVals;
	}

	public void addItem(final Object item) {
		if (!checkIsDefault())
			return;
		((DefaultListModel) listModel).addElement(item);
		SwingUtilities.invokeSync(new Runnable() {
			public void run() {
				if (SwingWTUtils.isSWTControlAvailable(ppeer))
					updateList();
			}
		});
	}

	public void insertItemAt(final Object item, final int index) {
		if (!checkIsDefault())
			return;
		((DefaultListModel) listModel).add(index, item);
		SwingUtilities.invokeSync(new Runnable() {
			public void run() {
				if (SwingWTUtils.isSWTControlAvailable(ppeer))
					updateList();
			}
		});
	}

	public int getSelectedIndex() {
		iRetVal = 0;
		SwingUtilities.invokeSync(new Runnable() {
			public void run() {
				if (!SwingWTUtils.isSWTControlAvailable(ppeer))
					iRetVal = -1;
				else
					iRetVal = ppeer.getSelectionIndex();
			}
		});
		return iRetVal;
	}

	public int[] getSelectedIndices() {
		iRetVals = null;
		SwingUtilities.invokeSync(new Runnable() {
			public void run() {
				if (SwingWTUtils.isSWTControlAvailable(ppeer))
					iRetVals = ppeer.getSelectionIndices();
				else
					iRetVals = new int[0];
			}
		});
		return iRetVals;
	}

	public Object getItemAt(int index) {
		return listModel.getElementAt(index);
	}

	public int getItemCount() {
		return listModel.getSize();
	}

	/** Scrolls the list to the selected item. This is not a Swing method */
	public void showSelection() {
		SwingUtilities.invokeSync(new Runnable() {
			public void run() {
				if (SwingWTUtils.isSWTControlAvailable(ppeer))
					ppeer.showSelection();
			}
		});
	}

	public void setSelectedIndex(final int index) {
		if (!SwingWTUtils.isSWTControlAvailable(ppeer))
			pSelectedIndex = index;
		else
			SwingUtilities.invokeSync(new Runnable() {
				public void run() {
					ppeer.select(index);
				}
			});
	}

	public void setSelectedIndices(final int[] selection) {
		if (!SwingWTUtils.isSWTControlAvailable(ppeer))
			return;
		SwingUtilities.invokeSync(new Runnable() {
			public void run() {
				ppeer.select(selection);
			}
		});
	}

	public void removeAllItems() {
		setModel(new DefaultListModel());
		if (SwingWTUtils.isSWTControlAvailable(ppeer))
			SwingUtilities.invokeSync(new Runnable() {
				public void run() {
					ppeer.removeAll();
					updateList();
				}
			});
	}

	public void removeItem(final Object item) {
		if (!checkIsDefault())
			return;
		final int index = ((DefaultListModel) listModel).indexOf(item);
		((DefaultListModel) listModel).removeElement(item);
		if (SwingWTUtils.isSWTControlAvailable(ppeer))
			SwingUtilities.invokeSync(new Runnable() {
				public void run() {
					ppeer.remove(index);
					updateList();
				}
			});
	}

	public void removeItemAt(final int index) {
		if (!checkIsDefault())
			return;
		((DefaultListModel) listModel).remove(index);
		if (SwingWTUtils.isSWTControlAvailable(ppeer))
			SwingUtilities.invokeSync(new Runnable() {
				public void run() {
					ppeer.remove(index);
				}
			});
	}

	/**
	 * Not a real Swing method - used to switch an already created item in the
	 * DefaultListModel. This is handy for the replace functionality of the AWT
	 * list.
	 */
	public void replaceItemAt(final Object replacement, final int index) {
		if (!checkIsDefault())
			return;
		((DefaultListModel) listModel).set(index, replacement);
		updateList();
	}

	/** Used to calculate preferred height */
	public void setVisibleRowCount(int rows) {
		Dimension d = getPreferredSize();
		d.height = rows * SwingWTUtils.getRenderStringHeight("W");
		if (d.width == 0)
			d.width = SwingWTUtils.getRenderStringWidth("WWWWWWWWWWWWWWWWWWW");
		setPreferredSize(d);
	}

	public int locationToIndex(final swingwt.awt.Point point) {
		// Do a close guess based on font height - this should be pretty
		// much accurate.
		return (point.y / SwingWTUtils.getRenderStringHeight("W"));
	}

	/** No way in SWT to set selection colour models */
	public void setSelectionBackground(swingwt.awt.Color color) {
	}

	/** No way in SWT to set selection colour models */
	public void setSelectionForeground(swingwt.awt.Color color) {
	}

	/** Fills the table with the list items */
	protected void updateList() {
		final JListTable list = this;
		SwingUtilities.invokeSync(new Runnable() {
			public void run() {

				// Don't do anything if we can't see the list anyway
				if (!SwingWTUtils.isSWTControlAvailable(ppeer))
					return;

				// Clear out all rows from the peer
				ppeer.removeAll();

				// Get a reference to the column
				org.eclipse.swt.widgets.TableColumn tc = ppeer.getColumn(0);

				// Use the width of the peer to size the only column
				// initially
				int maxWidth = ppeer.getBounds().width - SwingWTUtils.getRenderStringWidth("WW");
				if (SwingWTUtils.isMacOSX() || SwingWTUtils.isWindows())
					tc.setWidth(maxWidth);

				// Generate list data
				if (listModel != null) {

					for (int i = 0; i < listModel.getSize(); i++) {
						TableItem ti = new TableItem(ppeer, 0);
						// Get the cell renderer for this item
						JLabel renderer = (JLabel) cellRenderer
								.getListCellRendererComponent(list, listModel
										.getElementAt(i), i, false, false);

						// Use the text from the renderer
						ti.setText(0, renderer.getText());

						// If there's an image, render it:
						if (renderer.getIcon() != null)
							ti.setImage(0, SwingWTUtils
									.getSWTImageFromSwingIcon(list, renderer
											.getIcon()));

						// Work out the width and adjust if necessary
						int thiswidth = SwingWTUtils
								.getRenderStringWidth(ti.getText());
						if (thiswidth > maxWidth) {
							int iconwidth = 0;
							if (renderer.getIcon() != null)
								iconwidth = renderer.getIcon()
										.getIconWidth();
							tc.setWidth(thiswidth + iconwidth);
						}

						// Colours
						if (renderer.isBackgroundSet())
							if (renderer.getBackground().getSWTColor() != null)
								ti.setBackground(renderer.getBackground()
										.getSWTColor());
						if (renderer.isForegroundSet())
							if (renderer.getForeground().getSWTColor() != null)
								ti.setForeground(renderer.getForeground()
										.getSWTColor());

						// Cursor
						ppeer.setCursor(renderer.getCursor().getSWTCursor());
					}
				}
			}
		});
	}

	/**
	 * Fill list model from array
	 */
	public void setListData(Object[] listData) {
		loadData(listData);
		updateList();
	}

	/**
	 * Fill list model from vector
	 */
	public void setListData(Vector listData) {
		loadData(listData);
		updateList();
	}

	protected void loadData(Vector data) {
		loadData(data.toArray());
	}

	protected void loadData(Object[] data) {
		listModel = new DefaultListModel();
		for (int i = 0; i < data.length; i++) {
			((DefaultListModel) listModel).addElement(data[i]);
		}
		listModel.addListDataListener(this);
	}

	public void setCellRenderer(ListCellRenderer r) {
		cellRenderer = r;
	}

	public ListCellRenderer getCellRenderer() {
		return cellRenderer;
	}

	public ListModel getModel() {
		return listModel;
	}

	public void setModel(ListModel l) {
		listModel = l;
		listModel.addListDataListener(this);
		updateList();
	}

	/**
	 * Ensures the listmodel is an instance of DefaultListModel - returns false
	 * if it isn't.
	 */
	protected boolean checkIsDefault() {
		return listModel instanceof DefaultListModel;
	}

	/**
	 * Once a parent component receives an "add" call for a child, this being
	 * the child, this should be called to tell us to instantiate the peer and
	 * load in any cached properties.
	 */
	public void setSwingWTParent(swingwt.awt.Container parent) throws Exception {
		descendantHasPeer = true;

		// Create the table peer
		ppeer = new Table(
				parent.getComposite(),
				SWT.BORDER
						| (listSelectionModel.getSelectionMode() == ListSelectionModel.MULTIPLE_INTERVAL_SELECTION ? SWT.MULTI
								: SWT.SINGLE) | SWT.FULL_SELECTION);

		// Create a the single list column
		org.eclipse.swt.widgets.TableColumn tc = new org.eclipse.swt.widgets.TableColumn(
				ppeer, 0);

		peer = ppeer;
		this.parent = parent;

		// Cached values and events
		ppeer.setLinesVisible(false);
		registerSelectionEvents();

		// Add data
		updateList();

		if (pSelectedIndex != -1) {
			ppeer.select(pSelectedIndex);
			ppeer.showSelection();
		}
	}

	public void contentsChanged(swingwtx.swing.event.ListDataEvent e) {
		updateList();
	}

	public void intervalAdded(swingwtx.swing.event.ListDataEvent e) {
		updateList();
	}

	public void intervalRemoved(swingwtx.swing.event.ListDataEvent e) {
		updateList();
	}

	/**
	 * Assigns events to the peer for selection so that we can update the
	 * ListSelectionModel for this component.
	 */
	protected void registerSelectionEvents() {
		ppeer
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(
							org.eclipse.swt.events.SelectionEvent e) {
						if (listSelectionModel instanceof DefaultListSelectionModel) {
							if (listSelectionModel.getSelectionMode() == ListSelectionModel.MULTIPLE_INTERVAL_SELECTION) {
								int lowest = ppeer.getSelectionIndex();
								int highest = ppeer.getSelectionIndex();
								int sel[] = ppeer.getSelectionIndices();
								for (int i = 0; i < sel.length; i++) {
									if (lowest > sel[i])
										lowest = sel[i];
									if (highest < sel[i])
										highest = sel[i];
								}
								((DefaultListSelectionModel) listSelectionModel)
										.fireListSelectionEvent(JListTable.this,
												lowest, highest);
							} else {
								((DefaultListSelectionModel) listSelectionModel)
										.fireListSelectionEvent(JListTable.this,
												ppeer.getSelectionIndex(),
												ppeer.getSelectionIndex());
							}
						}
					}
				});
	}

	/*
	 * @see swingwtx.swing.Scrollable#getPreferredScrollableViewportSize()
	 */
	public Dimension getPreferredScrollableViewportSize() {
		return getPreferredSize();
	}

	/*
	 * @see swingwtx.swing.Scrollable#getScrollableBlockIncrement(swingwt.awt.Rectangle,
	 *      int, int)
	 */
	public int getScrollableBlockIncrement(Rectangle visibleRect,
			int orientation, int direction) {
		// not used... let swt scrollbars handle this
		return 0;
	}

	/*
	 * @see swingwtx.swing.Scrollable#getScrollableTracksViewportHeight()
	 */
	public boolean getScrollableTracksViewportHeight() {
		boolean retval = false;

		Container parent = getParent();
		if (parent instanceof JViewport)
			retval = ((JViewport) parent).getHeight() > getPreferredSize()
					.getHeight();

		return retval;
	}

	/*
	 * @see swingwtx.swing.Scrollable#getScrollableTracksViewportWidth()
	 */
	public boolean getScrollableTracksViewportWidth() {
		boolean retval = false;

		Container parent = getParent();
		if (parent instanceof JViewport)
			retval = ((JViewport) parent).getWidth() > getPreferredSize()
					.getWidth();

		return retval;
	}

	/*
	 * @see swingwtx.swing.Scrollable#getScrollableUnitIncrement(swingwt.awt.Rectangle,
	 *      int, int)
	 */
	public int getScrollableUnitIncrement(Rectangle visibleRect,
			int orientation, int direction) {
		// not used... let swt scrollbars handle this
		return 0;
	}

	public void ensureIndexIsVisible(final int selectedIndex) {
		SwingUtilities.invokeSync(new Runnable() {
			public void run() {
				ppeer.select(selectedIndex);
				ppeer.showSelection();
			}
		});
	}

	public boolean isSelectedIndex(int pos) {
		return getSelectedIndex() == pos;
	}

	public ListSelectionModel createSelectionModel() {
		return new DefaultListSelectionModel(this);
	}

	protected void setSelectionInterval(int index, int index2) {
		// TODO
	}

	public void addSelectionInterval(int index, int index2) {
		// TODO
	}

	public void removeSelectionInterval(int index, int index2) {
		// TODO
	}
	public void setFixedCellWidth(int width)
	{
		if (!SwingWTUtils.isSWTControlAvailable(ppeer))
			return;
		// TODO: try to set CellWidth
		org.eclipse.swt.widgets.TableColumn tc = null;
		/*
		for (int i = 0;i<ppeer.getColumnCount();i++)
		{
			 tc = ppeer.getColumn(i);
			 tc.setWidth(width);
		}
		*/
		tc = ppeer.getColumn(0);
		tc.setWidth(width);
	}

}
