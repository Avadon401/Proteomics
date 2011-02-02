package edu.unc.major.proteomics.client.ui.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import edu.unc.major.proteomics.client.ui.widget.InputListWidgetSearch;
import edu.unc.major.proteomics.client.ui.widget.table.BandCellTable;
import edu.unc.major.proteomics.client.ui.widget.table.PreyCellTable;
import edu.unc.major.proteomics.client.ui.widget.table.SiRNAValCellTable;

public class SearchViewImpl extends Composite implements SearchView {

	private static SearchViewImplUiBinder uiBinder = GWT.create(SearchViewImplUiBinder.class);

	interface SearchViewImplUiBinder extends UiBinder<Widget, SearchViewImpl> {}

	@UiField
	Label searchLabel;
	@UiField
	InputListWidgetSearch searchBox;
	@UiField
	Button searchButton;
	@UiField
	BandCellTable bandCellTable;
	@UiField
	PreyCellTable preyCellTable;
	@UiField
	SiRNAValCellTable siRNAValCellTable;
	
	
	private Presenter presenter;

	public SearchViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
		bandCellTable.setVisible(false);
		preyCellTable.setVisible(false);
		siRNAValCellTable.setVisible(false);
	}
	
	@UiHandler("searchButton")
	void handleClick(ClickEvent e) {
		bandCellTable.setVisible(true);
		bandCellTable.update(searchBox.getItemsSelected());
		preyCellTable.setVisible(true);
		preyCellTable.update(searchBox.getItemsSelected());
		siRNAValCellTable.setVisible(true);
		siRNAValCellTable.update(searchBox.getItemsSelected());
	}


	@Override
	public Widget asWidget() {
		return this;
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}

}
