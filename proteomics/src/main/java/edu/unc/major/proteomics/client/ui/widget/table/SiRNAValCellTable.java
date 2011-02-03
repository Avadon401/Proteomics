package edu.unc.major.proteomics.client.ui.widget.table;

import java.util.HashSet;
import java.util.Set;

import com.google.gwt.cell.client.NumberCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionModel;

import edu.unc.major.proteomics.client.Application;
import edu.unc.major.proteomics.share.dao.PageResults;
import edu.unc.major.proteomics.share.model.NcbiGene;
import edu.unc.major.proteomics.share.model.SiRNAVal;

public class SiRNAValCellTable extends Composite {

	CellTable<SiRNAVal> cellTable;
	private SimplePager pager;
	private Set<String> geneSymbols;

	public SiRNAValCellTable() {
		VerticalPanel panel = new VerticalPanel();
		initWidget(panel);
		// Create a CellTable.
		// Set a key provider that provides a unique key for each contact. If key is
		// used to identify contacts when fields (such as the name and address)
		// change.
		cellTable =  new CellTable<SiRNAVal>(KeyProvider.SiRNAValKeyProvider);

		// Create a Pager to control the table.
		SimplePager.Resources pagerResources = GWT.create(SimplePager.Resources.class);
		pager = new SimplePager(TextLocation.CENTER, pagerResources, false, 0, true);
		pager.setDisplay(cellTable);
		pager.setPageSize(10);

		// Add a selection model so we can select cells.
		final MultiSelectionModel<SiRNAVal> selectionModel = new MultiSelectionModel<SiRNAVal>(KeyProvider.SiRNAValKeyProvider);
		cellTable.setSelectionModel(selectionModel);

		// Initialize the columns.
		initTableColumns(selectionModel);

		// Add the CellList to the adapter in the database.
		provider.addDataDisplay(cellTable);

		panel.add(cellTable); 
		panel.add(pager);

		panel.setCellHorizontalAlignment(pager, HasHorizontalAlignment.ALIGN_CENTER);
	}

	public void initTableColumns(final SelectionModel<SiRNAVal> selectionModel) {


		// gene list
		Column<SiRNAVal, String> geneColumn = new Column<SiRNAVal, String>(new TextCell()) {
			public String getValue(SiRNAVal object) {
				Set<String> geneNames = new HashSet<String>();
				if (object.getData().getGene() != null) {
					geneNames.add(object.getData().getGene().getGeneName());
				}
				if (object.getData().getAlias() != null) {
					for (NcbiGene i : object.getData().getAlias().getGenes()){
						geneNames.add(i.getGeneName());
					}
				}
				String val = "";
				for (String i : geneNames) {
					val += i + ", ";
				}
				val = val.substring(0, val.length()-2);	    	  
				return String.valueOf(val);
			}
		};
		cellTable.addColumn(geneColumn, "Gene");

		// cell line
		Column<SiRNAVal, String> cellLineColumn = new Column<SiRNAVal, String>(new TextCell()) {
			public String getValue(SiRNAVal object) {
				if (object.getData().getCellLine() != null)
					return object.getData().getCellLine().getName();
				return "";
			}
		};
		cellTable.addColumn(cellLineColumn, "CellLine");

		// pathway
		Column<SiRNAVal, String> pathwayColumn = new Column<SiRNAVal, String>(new TextCell()) {
			public String getValue(SiRNAVal object) {
				return String.valueOf(object.getData().getExperiment().getPathway().getName());
			}
		};
		cellTable.addColumn(pathwayColumn, "Pathway");

		// siRNA type
		Column<SiRNAVal, String> typeColumn = new Column<SiRNAVal, String>(new TextCell()) {
			public String getValue(SiRNAVal object) {
				if (object.getData().getType() != null)
					return String.valueOf(object.getData().getType().getType());
				return "";
			}
		};
		cellTable.addColumn(typeColumn, "Type");

		// mean
		Column<SiRNAVal, Number> meanColumn = new Column<SiRNAVal, Number>(new NumberCell()) {
			public Number getValue(SiRNAVal object) {
				return object.getRepOverViaMean();
			}
		};
		cellTable.addColumn(meanColumn, "Mean");
		
		// mean
		Column<SiRNAVal, Number> medianColumn = new Column<SiRNAVal, Number>(new NumberCell()) {
			public Number getValue(SiRNAVal object) {
				return object.getRepOverViaMedian();
			}
		};
		cellTable.addColumn(medianColumn, "Median");

		// std
		Column<SiRNAVal, Number> stdColumn = new Column<SiRNAVal, Number>(new NumberCell()) {
			public Number getValue(SiRNAVal object) {
				return object.getRepOverViaStd();
			}
		};
		cellTable.addColumn(stdColumn, "Std");
	}

	private void updateTable(final int start, final int length) {
		AsyncCallback<PageResults<SiRNAVal>> callback = new AsyncCallback<PageResults<SiRNAVal>>() {
			public void onFailure(Throwable caught) {
				Window.alert(caught.getMessage());
			}
			public void onSuccess(PageResults<SiRNAVal> result) {
				if (result == null) {
					return;
				}
				provider.updateRowCount(result.getSize(), true);
				provider.updateRowData(start,result.getResults());
			}

		};
		Application.siRNAValService.getByGeneSymbolsPage(geneSymbols, start, length, callback);
	}

	private AsyncDataProvider<SiRNAVal> provider = new AsyncDataProvider<SiRNAVal>() {
		protected void onRangeChanged(HasData<SiRNAVal> display) {
			final int start = display.getVisibleRange().getStart();			
			final int length = display.getVisibleRange().getLength();
			updateTable(start,length);
		}	
	};

	public void setGeneSymbols(final Set<String> geneSymbols) {
		this.geneSymbols = geneSymbols;
	}

	public void update(final Set<String> geneSymbols) {
		setGeneSymbols(geneSymbols);
		pager.setPage(0);
		updateTable(0,pager.getPageSize());
	}

	public AsyncDataProvider<SiRNAVal> getProvider() {
		return provider;
	}

}
