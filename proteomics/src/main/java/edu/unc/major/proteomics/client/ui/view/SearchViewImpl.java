package edu.unc.major.proteomics.client.ui.view;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionChangeEvent.Handler;

import edu.unc.major.proteomics.client.ui.widget.InputListWidgetSearch;
import edu.unc.major.proteomics.client.ui.widget.table.BandCellTable;
import edu.unc.major.proteomics.client.ui.widget.table.PreyCellTable;
import edu.unc.major.proteomics.client.ui.widget.table.SiRNAValCellTable;
import edu.unc.major.proteomics.client.ui.widget.table.TppProteinCellTable;
import edu.unc.major.proteomics.share.model.Band;

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
	Button clearButton;
	@UiField
	BandCellTable bandCellTable;
	@UiField
	PreyCellTable preyCellTable;
	@UiField
	SiRNAValCellTable siRNAValCellTable;
	@UiField
	TppProteinCellTable compTable;
	@UiField
	DisclosurePanel disclosurePanel;
	@UiField
	FlowPanel dropShadow;
	
	private Presenter presenter;

	public SearchViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
		bandCellTable.setVisible(false);
		preyCellTable.setVisible(false);
		siRNAValCellTable.setVisible(false);
		compTable.setVisible(false);
		disclosurePanel.setStylePrimaryName("criteria-collapse");
		disclosurePanel.setAnimationEnabled(true);
		final Label hideLabel = new Label("Hide");
		disclosurePanel.setHeader(hideLabel);
		disclosurePanel.setOpen(true);
		disclosurePanel.setVisible(false);
		dropShadow.setVisible(false);
		
		bandCellTable.addSelectionChangeHandler(new Handler() {
			@Override
			public void onSelectionChange(SelectionChangeEvent event) {
				List<Band> bands = new ArrayList<Band>();
				bands.addAll(bandCellTable.getSelectionModel().getSelectedList());
				if (bands.size() > 0) {
					compTable.setVisible(true);
					compTable.update(bands);
				} else compTable.setVisible(false);
			}
			
		});
	}
	
	@UiHandler("searchButton")
	void handleSearchClick(ClickEvent e) {
		searchBox.onClick();
		disclosurePanel.setVisible(true);
		dropShadow.setVisible(true);
		bandCellTable.clearSelectedKeys();
		bandCellTable.setVisible(true);
		bandCellTable.update(searchBox.getItemsSelected());
		preyCellTable.clearSelectedKeys();
		preyCellTable.setVisible(true);
		preyCellTable.update(searchBox.getItemsSelected());
		siRNAValCellTable.setVisible(true);
		siRNAValCellTable.update(searchBox.getItemsSelected());
		compTable.setVisible(false);
	}

	@UiHandler("clearButton")
	void handleClearClick(ClickEvent e) {
		searchBox.clear();
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
