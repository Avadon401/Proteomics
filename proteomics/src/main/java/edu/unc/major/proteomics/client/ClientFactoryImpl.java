package edu.unc.major.proteomics.client;

import com.google.gwt.app.place.PlaceController;
import com.google.gwt.event.shared.HandlerManager;

public class ClientFactoryImpl implements ClientFactory{
	private final HandlerManager eventBus = new HandlerManager();
	private final PlaceController placeController = new PlaceController(eventBus);
}
