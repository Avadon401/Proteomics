package edu.unc.major.proteomics.client;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

import edu.unc.major.proteomics.client.ui.place.ExportPlace;
import edu.unc.major.proteomics.client.ui.place.SearchPlace;
import edu.unc.major.proteomics.share.service.BandService;
import edu.unc.major.proteomics.share.service.BandServiceAsync;
import edu.unc.major.proteomics.share.service.ConstantService;
import edu.unc.major.proteomics.share.service.ConstantServiceAsync;
import edu.unc.major.proteomics.share.service.ExportService;
import edu.unc.major.proteomics.share.service.ExportServiceAsync;
import edu.unc.major.proteomics.share.service.NcbiGeneService;
import edu.unc.major.proteomics.share.service.NcbiGeneServiceAsync;
import edu.unc.major.proteomics.share.service.SiRNAValService;
import edu.unc.major.proteomics.share.service.SiRNAValServiceAsync;
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
	private Place exportPlace = new ExportPlace();
	private SimplePanel appWidget = new SimplePanel();
	
	private PlaceHistoryHandler historyHandler;
	private EventBus eventBus;
	private PlaceController placeController;

	public static final TppProteinIndProteinServiceAsync proteinIndService = (TppProteinIndProteinServiceAsync) GWT.create(TppProteinIndProteinService.class);
	public static final TppProteinServiceAsync proteinService = (TppProteinServiceAsync) GWT.create(TppProteinService.class);
	public static final NcbiGeneServiceAsync ncbiGeneService = (NcbiGeneServiceAsync) GWT.create(NcbiGeneService.class);
	public static final ConstantServiceAsync constantService = (ConstantServiceAsync) GWT.create(ConstantService.class);
	public static final BandServiceAsync bandService = (BandServiceAsync) GWT.create(BandService.class);
	public static final SiRNAValServiceAsync siRNAValService = (SiRNAValServiceAsync) GWT.create(SiRNAValService.class);
	public static final ExportServiceAsync exportService = (ExportServiceAsync) GWT.create(ExportService.class);
	
	static {
		bindService(proteinIndService, "tppProteinIndProteinService");
		bindService(proteinService, "tppProteinService");
		bindService(ncbiGeneService, "ncbiGeneService");
		bindService(constantService, "constantService");
		bindService(bandService, "bandService");
		bindService(siRNAValService, "siRNAValService");
		bindService(exportService, "exportService");
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
		eventBus = clientFactory.getEventBus();
		placeController = clientFactory.getPlaceController();
		
		// Start ActivityManager for the main widget with out ActivityMapper
		ActivityMapper activityMapper = new AppActivityMapper(clientFactory);
		ActivityManager activityManager = new ActivityManager(activityMapper, eventBus);
		activityManager.setDisplay(appWidget);
		
		// Start PlaceHistoryHandler with our PlaceHistoryMapper
		AppPlaceHistoryMapper historyMapper = GWT.create(AppPlaceHistoryMapper.class);
		historyHandler = new PlaceHistoryHandler(historyMapper);
		historyHandler.register(placeController, eventBus, defaultPlace);
		
		RootPanel.get("menu").add(createMenuBar());
		RootPanel.get("content").add(appWidget);
		historyHandler.handleCurrentHistory();

		// Throw away the loading message that users see while GWT
		// starts up and is able to finish loading its resources.
		final Element lm = RootPanel.get("loadingmessage").getElement();
		DOM.removeChild(DOM.getParent(lm), lm); 
	} 
	
	public Widget createMenuBar() {
		MenuBar menu = new MenuBar();
		menu.setAutoOpen(true);
		menu.setAnimationEnabled(true);
		
		MenuItem search = new MenuItem("Search", new Command() {
		      public void execute() {
		          historyHandler.register(placeController, eventBus, defaultPlace);
		          historyHandler.handleCurrentHistory();
		        }
		      });
		MenuItem export = new MenuItem("Export", new Command() {
		      public void execute() {
		          historyHandler.register(placeController, eventBus, exportPlace);
		          historyHandler.handleCurrentHistory();
		        }
		      });
		menu.addItem(search);
		menu.addItem(export);
		return menu;
	}
}
