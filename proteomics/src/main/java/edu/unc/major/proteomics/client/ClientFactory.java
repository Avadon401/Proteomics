package edu.unc.major.proteomics.client;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;

import edu.unc.major.proteomics.client.ui.view.SearchView;

public interface ClientFactory {
	EventBus getEventBus();
	PlaceController getPlaceController();
	SearchView getSearchView();
}
