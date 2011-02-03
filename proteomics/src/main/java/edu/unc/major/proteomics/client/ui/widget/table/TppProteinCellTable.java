package edu.unc.major.proteomics.client.ui.widget.table;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.NumberCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.Header;
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

import edu.unc.major.proteomics.client.Application;
import edu.unc.major.proteomics.share.dao.PageResults;
import edu.unc.major.proteomics.share.model.Band;
import edu.unc.major.proteomics.share.model.TppProtein;

public class TppProteinCellTable extends Composite {
	
	CellTable<List<TppProtein>> cellTable;
	private SimplePager pager;
	private List<Long> bandIds;
	private int numCols = 0;
	final MultiSelectionModel<List<TppProtein>> selectionModel = new MultiSelectionModel<List<TppProtein>>(KeyProvider.TppProteinListKeyProvider);
	
	public TppProteinCellTable() {
		VerticalPanel panel = new VerticalPanel();
		initWidget(panel);
		bandIds = new ArrayList<Long>();
		// Create a CellTable.
	    // Set a key provider that provides a unique key for each contact. If key is
	    // used to identify contacts when fields (such as the name and address)
	    // change.
	    cellTable =  new CellTable<List<TppProtein>>(KeyProvider.TppProteinListKeyProvider);

	    // Create a Pager to control the table.
	    SimplePager.Resources pagerResources = GWT.create(SimplePager.Resources.class);
	    pager = new SimplePager(TextLocation.CENTER, pagerResources, false, 0, true);
	    pager.setDisplay(cellTable);
	    pager.setPageSize(20);

	    // Add a selection model so we can select cells.
	    cellTable.setSelectionModel(selectionModel);
	    
	    // Initialize the columns.
	    //initTableColumns(selectionModel);

	    // Add the CellList to the adapter in the database.
	    provider.addDataDisplay(cellTable);
	    
	    panel.add(cellTable); 
		panel.add(pager);
		
		panel.setCellHorizontalAlignment(pager, HasHorizontalAlignment.ALIGN_CENTER);
	}
	
	public void updateTableColumns(List<Band> keys) {
		for (int i = 0; i < numCols; i++) {
			cellTable.removeColumn(0);
		}
		numCols = 0;
		
	    Column<List<TppProtein>, Boolean> checkColumn = new Column<List<TppProtein>, Boolean>(
	        new CheckboxCell(true)) {
	      @Override
	      public Boolean getValue(List<TppProtein> object) {
	        // Get the value from the selection model.
	        return selectionModel.isSelected(object);
	      }
	    };
	    checkColumn.setFieldUpdater(new FieldUpdater<List<TppProtein>, Boolean>() {
	      public void update(int index, List<TppProtein> object, Boolean value) {
	        // Called when the user clicks on a checkbox.
	        selectionModel.setSelected(object, value);
	      }
	    });
	    //cellTable.addColumn(checkColumn, SafeHtmlUtils.fromSafeConstant("<br>"));
	    //numCols++;
	    
	    
	    // prey gene name
	    Column<List<TppProtein>, String> preyColumn = new Column<
	    List<TppProtein>, String>(new TextCell()) {
	      @Override
	      public String getValue(List<TppProtein> object) {
	    	  for (TppProtein i : object) {
	    		  if (i.getId() != 0) {
	    	    	  return i.getGene().getGeneName();
	    		  }
	    	  }
	    	  return "";
	      }
	    };
	    cellTable.addColumn(preyColumn, "Gene");
	    numCols++;
	    
	    for (final Band key : keys) {
	    	Header<String> header = new Header<String>(new TextCell()){
				@Override
				public String getValue() {
					return key.getNiceName();
				}	    		
	    	};
			// uni peps
			IdColumn<List<TppProtein>, Number, Long> uniColumn = new IdColumn<List<TppProtein>, Number, Long>(new NumberCell(), key.getId()) {
				public Number getValue(List<TppProtein> object) {
					for (TppProtein i : object) {					
						if (i.getId() != 0 && i.getBand().getId().longValue() == getId().longValue()) {
							return i.getUniqueNumPeptides();
						}
					}
					return 0;
				}
			};
			cellTable.addColumn(uniColumn, header);
			numCols++;
			
			// tot peps
			IdColumn<List<TppProtein>, Number, Long> totColumn = new IdColumn<List<TppProtein>, Number, Long>(new NumberCell(), key.getId()) {
				public Number getValue(List<TppProtein> object) {
					for (TppProtein i : object) {
						if (i.getId() != 0 && i.getBand().getId().longValue() == getId().longValue()) {
							return i.getTotalNumPeptides();
						}
					}
					return 0;
				}
			};
			cellTable.addColumn(totColumn, header);
			numCols++;
	    }

	}
	
	private void updateTable(final int start, final int length) {
		AsyncCallback<PageResults<List<TppProtein>>> callback = new AsyncCallback<PageResults<List<TppProtein>>>() {
			public void onFailure(Throwable caught) {
				Window.alert(caught.getMessage());
			}
			public void onSuccess(PageResults<List<TppProtein>> result) {
				if (result == null) {
					return;
				}
				provider.updateRowCount(result.getSize(), true);
				provider.updateRowData(start,result.getResults());
			}
			
		};
		Application.proteinService.getListByBandId(bandIds, start, length, callback);
	}
	
	private AsyncDataProvider<List<TppProtein>> provider = new AsyncDataProvider<List<TppProtein>>() {
		protected void onRangeChanged(HasData<List<TppProtein>> display) {
			final int start = display.getVisibleRange().getStart();			
			final int length = display.getVisibleRange().getLength();
			updateTable(start,length);
		}	
	};
	
	public void setBandIds(final List<Band> bands) {
		bandIds.clear();
		for (Band b : bands) {
			bandIds.add(b.getId());
		}
	}
	
	public void update(final List<Band> bands) {
		setBandIds(bands);
		pager.setPage(0);
		updateTableColumns(bands);
		updateTable(0,pager.getPageSize());
	}
	
	public AsyncDataProvider<List<TppProtein>> getProvider() {
		return provider;
	}

}
