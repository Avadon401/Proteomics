package edu.unc.major.proteomics.client.ui.widget.table;

import java.util.List;

public abstract class AdvancedColumn<T,C> {
	private SelectionWidget select;
	
	public AdvancedColumn() {

	}
	
	public AdvancedColumn(final SelectionWidget select) {
		this.select = select;
	}
	
	public abstract List<ColumnWithHeader<T,C>> getColumns();
	
	public SelectionWidget getSelectionWidget() {
		return select;
	}
	
}
