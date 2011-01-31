package edu.unc.major.proteomics.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.place.shared.PlaceController;

import edu.unc.major.proteomics.client.ui.view.SearchView;
import edu.unc.major.proteomics.client.ui.view.SearchViewImpl;

public class ClientFactoryImpl implements ClientFactory{
	private final EventBus eventBus = new SimpleEventBus();
	private final PlaceController placeController = new PlaceController(eventBus);
	private final SearchView searchView = new SearchViewImpl();

	public EventBus getEventBus() {
		return eventBus;
	}

	public PlaceController getPlaceController() {
		return placeController;
	}

	public SearchView getSearchView() {
		return searchView;
	}
}
