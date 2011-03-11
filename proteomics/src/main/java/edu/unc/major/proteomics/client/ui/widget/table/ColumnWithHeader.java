package edu.unc.major.proteomics.client.ui.widget.table;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.Header;

public abstract class ColumnWithHeader<T, C> extends Column<T, C>{
	private Header<?> header;
	private boolean isDisplayed;
	private boolean isSortable;
	
	public ColumnWithHeader(Cell<C> cell) {
		this(cell, new Header<String>(new TextCell()){
				public String getValue() {
					return "";
				}	    						
		});
	}
	
	public ColumnWithHeader(Cell<C> cell, Header<?> header) {
		this(cell, header, true, false);
	}
	
	public ColumnWithHeader(Cell<C> cell, Header<?> header, boolean isDisplayed, boolean isSortable) {
		super(cell);
		this.header = header;
		this.isDisplayed = isDisplayed;
		this.isSortable = isSortable;
	}
	
	public Header<?> getHeader() {
		return header;
	}
	
	public void setHeader(final Header<?> header) {
		this.header = header;
	}
	
	public boolean isDisplayed() {
		return isDisplayed;
	}
	
	public void setDisplayed(boolean isDisplayed) {
		this.isDisplayed = isDisplayed;
	}
	
	public boolean isSortable() {
		return isSortable;
	}
	
	public void setSortable(boolean isSortable) {
		this.isSortable = isSortable;
	}

}
