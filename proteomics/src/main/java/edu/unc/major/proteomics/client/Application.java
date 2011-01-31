package edu.unc.major.proteomics.client;

import java.util.HashSet;
import java.util.Set;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;

import edu.unc.major.proteomics.client.ui.place.SearchPlace;
import edu.unc.major.proteomics.share.model.Band;
import edu.unc.major.proteomics.share.service.BandService;
import edu.unc.major.proteomics.share.service.BandServiceAsync;
import edu.unc.major.proteomics.share.service.ConstantService;
import edu.unc.major.proteomics.share.service.ConstantServiceAsync;
import edu.unc.major.proteomics.share.service.NcbiGeneService;
import edu.unc.major.proteomics.share.service.NcbiGeneServiceAsync;
import edu.unc.major.proteomics.share.service.TppProteinIndProteinService;
import edu.unc.major.proteomics.share.service.TppProteinIndProteinServiceAsync;
import edu.unc.major.proteomics.share.service.TppProteinService;
import edu.unc.major.proteomics.share.service.TppProteinServiceAsync;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Application implements EntryPoint
{
	private Place defaultPlace = new SearchPlace();
	private SimplePanel appWidget = new SimplePanel();

	public static final TppProteinIndProteinServiceAsync proteinIndService = (TppProteinIndProteinServiceAsync) GWT.create(TppProteinIndProteinService.class);
	public static final TppProteinServiceAsync proteinService = (TppProteinServiceAsync) GWT.create(TppProteinService.class);
	public static final NcbiGeneServiceAsync ncbiGeneService = (NcbiGeneServiceAsync) GWT.create(NcbiGeneService.class);
	public static final ConstantServiceAsync constantService = (ConstantServiceAsync) GWT.create(ConstantService.class);
	public static final BandServiceAsync bandService = (BandServiceAsync) GWT.create(BandService.class);
	
	static {
		bindService(proteinIndService, "tppProteinIndProteinService");
		bindService(proteinService, "tppProteinService");
		bindService(ncbiGeneService, "ncbiGeneService");
		bindService(constantService, "constantService");
		bindService(bandService, "bandService");
	}
	
	private static void bindService(final Object svc, final String name) {
		final ServiceDefTarget e = (ServiceDefTarget) svc;
		e.setServiceEntryPoint(GWT.getModuleBaseURL() + name);
	}

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad()
	{
		ClientFactory clientFactory = GWT.create(ClientFactory.class);
		EventBus eventBus = clientFactory.getEventBus();
		PlaceController placeController = clientFactory.getPlaceController();
		
		// Start ActivityManager for the main widget with out ActivityMapper
		ActivityMapper activityMapper = new AppActivityMapper(clientFactory);
		ActivityManager activityManager = new ActivityManager(activityMapper, eventBus);
		activityManager.setDisplay(appWidget);
		
		// Start PlaceHistoryHandler with our PlaceHistoryMapper
		AppPlaceHistoryMapper historyMapper = GWT.create(AppPlaceHistoryMapper.class);
		PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(historyMapper);
		historyHandler.register(placeController, eventBus, defaultPlace);
		
		RootPanel.get("content").add(appWidget);
		historyHandler.handleCurrentHistory();
	
		Set<String> geneSymbols = new HashSet<String>();
		geneSymbols.add("FAM123A");
		bandService.getByGeneSymbols(geneSymbols, new AsyncCallback<Set<Band>>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				Window.alert("duh");
			}

			@Override
			public void onSuccess(Set<Band> result) {
				Window.alert("holy shit " + String.valueOf(result.size()));
			}
			
		});
		// Throw away the loading message that users see while GWT
		// starts up and is able to finish loading its resources.
		final Element lm = RootPanel.get("loadingmessage").getElement();
		DOM.removeChild(DOM.getParent(lm), lm); 
	} 
}
