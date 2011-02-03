package edu.unc.major.proteomics.client.ui.widget.table;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.user.cellview.client.Column;

public abstract class IdColumn<T, C, I> extends Column<T,C> {
	private I id;

	public IdColumn(Cell cell, I id) {
		super(cell);
		setId(id);
	}

	public I getId(){
		return id;
	}
	
	public void setId(final I id) {
		this.id = id;
	}
}
