package edu.unc.major.proteomics.client.ui.widget.table;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.TableCellElement;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
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
import com.google.gwt.view.client.SelectionChangeEvent.Handler;
import com.google.gwt.view.client.SelectionModel;

import edu.unc.major.proteomics.client.Application;
import edu.unc.major.proteomics.client.ui.widget.BandPopupWidget;
import edu.unc.major.proteomics.share.dao.PageResults;
import edu.unc.major.proteomics.share.model.Band;
import edu.unc.major.proteomics.share.model.TppProtein;

public class BandCellTable extends Composite {
	Table<Band> cellTable;
	private SimplePager pager;
	private Set<String> geneSymbols;
	final OrderedMultiSelectionModel<Band, Long> selectionModel;
	
	public BandCellTable() {
		VerticalPanel panel = new VerticalPanel();
		initWidget(panel);
		// Create a CellTable.
	    // Set a key provider that provides a unique key for each contact. If key is
	    // used to identify contacts when fields (such as the name and address)
	    // change.
	    cellTable =  new Table<Band>(KeyProvider.BandKeyProvider);

	    // Create a Pager to control the table.
	    SimplePager.Resources pagerResources = GWT.create(SimplePager.Resources.class);
	    pager = new SimplePager(TextLocation.CENTER, pagerResources, false, 0, true);
	    pager.setDisplay(cellTable);
	    pager.setPageSize(10);

	    // Add a selection model so we can select cells.
	    selectionModel = new OrderedMultiSelectionModel<Band, Long>(KeyProvider.BandKeyProvider);
	    cellTable.setSelectionModel(selectionModel);
	    
	    // Initialize the columns.
	    initTableColumns(selectionModel);

	    // Add the CellList to the adapter in the database.
	    provider.addDataDisplay(cellTable);
	    
	    panel.add(cellTable); 
		panel.add(pager);
		
		panel.setCellHorizontalAlignment(pager, HasHorizontalAlignment.ALIGN_CENTER);
	}
	
	public void initTableColumns(final SelectionModel<Band> selectionModel) {
		AdvancedColumn<Band, Boolean> checkColumn2 = new AdvancedColumn<Band, Boolean>(){
			public List<ColumnWithHeader<Band, Boolean>> getColumns() {
				List<ColumnWithHeader<Band, Boolean>> result = new ArrayList<ColumnWithHeader<Band, Boolean>>();
				ColumnWithHeader<Band, Boolean> col = new ColumnWithHeader<Band, Boolean>(
				        new CheckboxCell(true),
					    new Header<String>(new TextCell()){
							public String getValue() {
								return "";
							}	    		
				    	}, true, false) {
				    	public Boolean getValue(Band object) {
					        return selectionModel.isSelected(object);
					    }
				    };
				col.setFieldUpdater(new FieldUpdater<Band, Boolean>() {
				      public void update(int index, Band object, Boolean value) {
					        selectionModel.setSelected(object, value);
					      }
					    });
				result.add(col);
				return result;
			}	
		};
		// Checkbox column. This table will uses a checkbox column for selection.
	    // Alternatively, you can call cellTable.setSelectionEnabled(true) to enable
	    // mouse selection.
	    Column<Band, Boolean> checkColumn = new Column<Band, Boolean>(
	        new CheckboxCell(true)) {
	      @Override
	      public Boolean getValue(Band object) {
	        // Get the value from the selection model.
	        return selectionModel.isSelected(object);
	      }
	    };
	    checkColumn.setFieldUpdater(new FieldUpdater<Band, Boolean>() {
	      public void update(int index, Band object, Boolean value) {
	        // Called when the user clicks on a checkbox.
	        selectionModel.setSelected(object, value);
	      }
	    });
	    cellTable.addColumn(checkColumn, SafeHtmlUtils.fromSafeConstant("<br>"));

	    // bait gene name
	    Column<Band, String> baitColumn = new Column<
	        Band, String>(new ClickableTextCell()) {
	      @Override
	      public String getValue(Band object) {
				return object.getBait().getMutatedName();
	      }
	    };
	    baitColumn.setFieldUpdater(new FieldUpdater<Band, String>() {
	        public void update(int index, Band object, String value) {
	        	int row = index%cellTable.getPageSize();
	        	TableCellElement target = cellTable.getRowElement(row).getCells().getItem(1);
	        	int left = target.getAbsoluteLeft()+target.getOffsetWidth();
	        	int top = cellTable.getRowElement(row).getAbsoluteTop();
	        	new BandPopupWidget().getDataAndShow(object.getId(),left,top);
	        }
	      });
	    cellTable.addColumn(baitColumn, "Bait");

	    // ms run name
	    Column<Band, String> nameColumn = new Column<
	        Band, String>(new TextCell()) {
	      @Override
	      public String getValue(Band object) {
	    	  String bandName = object.getName();
				if (bandName.contains("---")) {
					bandName = bandName.substring(bandName.indexOf("---")+3);
				}
				return bandName;
	      }
	    };
	    cellTable.addColumn(nameColumn, "MS Run");
	    
	    // # proteins
	    Column<Band, String> countColumn = new Column<
	        Band, String>(new TextCell()) {
	      @Override
	      public String getValue(Band object) {
	    	  return String.valueOf(object.getProteinCount());
	      }
	    };
	    cellTable.addColumn(countColumn, "Proteins");
	}
	
	private void updateTable(final int start, final int length) {
		AsyncCallback<PageResults<Band>> callback = new AsyncCallback<PageResults<Band>>() {
			public void onFailure(Throwable caught) {
				Window.alert(caught.getMessage());
			}
			public void onSuccess(PageResults<Band> result) {
				if (result == null) {
					return;
				}
				provider.updateRowCount(result.getSize(), true);
				provider.updateRowData(start,result.getResults());
			}
			
		};
		Application.bandService.getByGeneSymbolsPage(geneSymbols, start, length, callback);
	}
	
	private AsyncDataProvider<Band> provider = new AsyncDataProvider<Band>() {
		protected void onRangeChanged(HasData<Band> display) {
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
	
	public AsyncDataProvider<Band> getProvider() {
		return provider;
	}
	
	public void addSelectionChangeHandler(final Handler handler) {
		selectionModel.addSelectionChangeHandler(handler);
	}
	
	public OrderedMultiSelectionModel<Band, Long> getSelectionModel() {
		return selectionModel;
	}
	
	public List<Long> getSelectedKeys() {
		return selectionModel.getSelectedKeys();
	}
	
	public void clearSelectedKeys() {
		for (Band b : selectionModel.getSelectedList()) {
			selectionModel.setSelected(b, false);
		}
	}

}
