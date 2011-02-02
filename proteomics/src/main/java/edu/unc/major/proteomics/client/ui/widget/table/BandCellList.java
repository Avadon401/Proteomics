package edu.unc.major.proteomics.client.ui.widget.table;

import java.util.HashSet;
import java.util.Set;

import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.HasKeyboardPagingPolicy.KeyboardPagingPolicy;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionChangeEvent;

import edu.unc.major.proteomics.client.Application;
import edu.unc.major.proteomics.share.dao.PageResults;
import edu.unc.major.proteomics.share.model.Band;

public class BandCellList extends Composite{
	
	private CellList<Band> cellList;
	private Set<String> geneSymbols;
	private SimplePager pager;
	
	public BandCellList() {
		this(new HashSet<String>());
	}
	
	public BandCellList(final Set<String> geneSymbols) {
		this.geneSymbols = geneSymbols;
		final FlowPanel panel = new FlowPanel();
		initWidget(panel);
		BandCell bandCell = new BandCell();
		cellList = new CellList<Band>(bandCell, KeyProvider.BandKeyProvider);
		cellList.setPageSize(5);
		cellList.setKeyboardPagingPolicy(KeyboardPagingPolicy.INCREASE_RANGE);
		
		final MultiSelectionModel<Band> selectionModel = new MultiSelectionModel<Band>(KeyProvider.BandKeyProvider);
		cellList.setSelectionModel(selectionModel);
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				// interact with comparison table
			}
		});	
		provider.addDataDisplay(cellList);
		
		pager = new SimplePager();
		pager.setDisplay(cellList);
		pager.getElement().getStyle().setProperty("align", "center");
		Label label = new Label("Matching Bait");
		label.getElement().getStyle().setProperty("align", "center");
		cellList.getElement().getStyle().setProperty("border", "3px ridge #56A0D3");
		panel.add(label);
		panel.add(cellList); 
		panel.add(pager);
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
		//pager.setPage(0);
		updateTable(0,pager.getPageSize());
	}
	
	public AsyncDataProvider<Band> getProvider() {
		return provider;
	}
	
}
