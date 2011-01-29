package edu.unc.major.proteomics.client;

import com.google.gwt.app.place.PlaceController;

public interface ClientFactory {
	EventBus getEventBus();
	PlaceController getPlaceController();
	SearchView getSearchView();
}
