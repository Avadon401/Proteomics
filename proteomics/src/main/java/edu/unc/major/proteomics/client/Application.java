package edu.unc.major.proteomics.client;

import java.util.Set;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

import edu.unc.major.proteomics.client.constants.Labels;
import edu.unc.major.proteomics.client.ui.widget.SuggestBoxList.InputListWidget;
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

	public static final TppProteinIndProteinServiceAsync proteinIndService = (TppProteinIndProteinServiceAsync) GWT.create(TppProteinIndProteinService.class);
	public static final TppProteinServiceAsync proteinService = (TppProteinServiceAsync) GWT.create(TppProteinService.class);
	public static final NcbiGeneServiceAsync ncbiGeneService = (NcbiGeneServiceAsync) GWT.create(NcbiGeneService.class);
	public static final ConstantServiceAsync constantService = (ConstantServiceAsync) GWT.create(ConstantService.class);
	
	static {
		bindService(proteinIndService, "tppProteinIndProteinService");
		bindService(proteinService, "tppProteinService");
		bindService(ncbiGeneService, "ncbiGeneService");
		bindService(constantService, "constantService");
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
    	
    	
		final InputListWidget sb = new InputListWidget() {
			public void getMatches(String[] geneNames) {
				this.geneNames = geneNames;
				constantService.getMatchedSymbols(geneNames, callback);
			}
		};
		
		final Label sbLabel = new Label(Labels.getSuggestBoxLabel());
		DOM.setElementAttribute(sbLabel.getElement(), "id", "searchLabel");
		final Button searchButton = new Button(Labels.getSearchButtonLabel());
		final HorizontalPanel searchPanel = new HorizontalPanel();

		searchPanel.add(sbLabel);
		searchPanel.add(sb);
		searchPanel.add(searchButton);

		searchPanel.setCellVerticalAlignment(sbLabel, HasVerticalAlignment.ALIGN_MIDDLE);
		searchPanel.setCellVerticalAlignment(searchButton, HasVerticalAlignment.ALIGN_MIDDLE);

		DOM.setElementAttribute(searchPanel.getElement(), "id", "searchPanel");

		RootPanel.get("content").add(searchPanel);
	
		// Throw away the loading message that users see while GWT
		// starts up and is able to finish loading its resources.
		final Element lm = RootPanel.get("loadingmessage").getElement();
		DOM.removeChild(DOM.getParent(lm), lm);
	}
}
