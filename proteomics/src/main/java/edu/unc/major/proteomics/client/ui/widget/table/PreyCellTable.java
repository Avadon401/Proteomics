package edu.unc.major.proteomics.client.ui.widget.table;

import java.util.Set;

import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
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
import edu.unc.major.proteomics.share.model.Band;
import edu.unc.major.proteomics.share.model.TppProtein;

public class PreyCellTable extends Composite {

	CellTable<TppProtein> cellTable;
	private SimplePager pager;
	private Set<String> geneSymbols;
	
	public PreyCellTable() {
		VerticalPanel panel = new VerticalPanel();
		initWidget(panel);
		// Create a CellTable.
	    // Set a key provider that provides a unique key for each contact. If key is
	    // used to identify contacts when fields (such as the name and address)
	    // change.
	    cellTable =  new CellTable<TppProtein>(KeyProvider.TppProteinKeyProvider);

	    // Create a Pager to control the table.
	    SimplePager.Resources pagerResources = GWT.create(SimplePager.Resources.class);
	    pager = new SimplePager(TextLocation.CENTER, pagerResources, false, 0, true);
	    pager.setDisplay(cellTable);
	    pager.setPageSize(10);

	    // Add a selection model so we can select cells.
	    final MultiSelectionModel<TppProtein> selectionModel = new MultiSelectionModel<TppProtein>(KeyProvider.TppProteinKeyProvider);
	    cellTable.setSelectionModel(selectionModel);
	    
	    // Initialize the columns.
	    initTableColumns(selectionModel);

	    // Add the CellList to the adapter in the database.
	    provider.addDataDisplay(cellTable);
	    
	    panel.add(cellTable); 
		panel.add(pager);
		
		panel.setCellHorizontalAlignment(pager, HasHorizontalAlignment.ALIGN_CENTER);
	}
	
	public void initTableColumns(final SelectionModel<TppProtein> selectionModel) {
		// Checkbox column. This table will uses a checkbox column for selection.
	    // Alternatively, you can call cellTable.setSelectionEnabled(true) to enable
	    // mouse selection.
	    Column<TppProtein, Boolean> checkColumn = new Column<TppProtein, Boolean>(new CheckboxCell(true)) {
	      public Boolean getValue(TppProtein object) {
	        // Get the value from the selection model.
	        return selectionModel.isSelected(object);
	      }
	    };
	    checkColumn.setFieldUpdater(new FieldUpdater<TppProtein, Boolean>() {
	      public void update(int index, TppProtein object, Boolean value) {
	        // Called when the user clicks on a checkbox.
	        selectionModel.setSelected(object, value);
	      }
	    });
	    cellTable.addColumn(checkColumn, SafeHtmlUtils.fromSafeConstant("<br>"));
	    
	    // # prey
	    Column<TppProtein, String> preyColumn = new Column<TppProtein, String>(new TextCell()) {
	      public String getValue(TppProtein object) {
	    	  return String.valueOf(object.getGene().getGeneName());
	      }
	    };
	    cellTable.addColumn(preyColumn, "Prey");

	    // bait gene name
	    Column<TppProtein, String> baitColumn = new Column<TppProtein, String>(new TextCell()) {
	      public String getValue(TppProtein object) {
	    	  String val = object.getBand().getBait().getGene().getGeneName();
				if (!"".equals(object.getBand().getBait().getMutation())) {
					val += " (" + object.getBand().getBait().getMutation() + ")";
				}
				return val;
	      }
	    };
	    cellTable.addColumn(baitColumn, "Bait");
	    
	    // # unique peptides
	    Column<TppProtein, String> uniCountColumn = new Column<TppProtein, String>(new TextCell()) {
	      public String getValue(TppProtein object) {
	    	  return String.valueOf(object.getUniqueNumPeptides());
	      }
	    };
	    cellTable.addColumn(uniCountColumn, "U");
	    
	    // # tot peptides
	    Column<TppProtein, String> totCountColumn = new Column<TppProtein, String>(new TextCell()) {
	      public String getValue(TppProtein object) {
	    	  return String.valueOf(object.getTotalNumPeptides());
	      }
	    };
	    cellTable.addColumn(totCountColumn, "T");
	    
	    // ms run name
	    Column<TppProtein, String> nameColumn = new Column<TppProtein, String>(new TextCell()) {
	      public String getValue(TppProtein object) {
	    	  String bandName = object.getBand().getName();
				if (bandName.contains("---")) {
					bandName = bandName.substring(bandName.indexOf("---")+3);
				}
				return bandName;
	      }
	    };
	    // cellTable.addColumn(nameColumn, "MS Run");
	}
	
	private void updateTable(final int start, final int length) {
		AsyncCallback<PageResults<TppProtein>> callback = new AsyncCallback<PageResults<TppProtein>>() {
			public void onFailure(Throwable caught) {
				Window.alert(caught.getMessage());
			}
			public void onSuccess(PageResults<TppProtein> result) {
				if (result == null) {
					return;
				}
				provider.updateRowCount(result.getSize(), true);
				provider.updateRowData(start,result.getResults());
			}
			
		};
		Application.proteinService.getByGeneSymbolsPage(geneSymbols, start, length, callback);
	}
	
	private AsyncDataProvider<TppProtein> provider = new AsyncDataProvider<TppProtein>() {
		protected void onRangeChanged(HasData<TppProtein> display) {
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
	
	public AsyncDataProvider<TppProtein> getProvider() {
		return provider;
	}

}
