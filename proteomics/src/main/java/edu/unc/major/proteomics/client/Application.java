package edu.unc.major.proteomics.client;

import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SuggestBox;

import edu.unc.major.proteomics.client.constants.Labels;
import edu.unc.major.proteomics.share.model.TppProtein;
import edu.unc.major.proteomics.share.service.TppProteinIndProteinService;
import edu.unc.major.proteomics.share.service.TppProteinIndProteinServiceAsync;
import edu.unc.major.proteomics.share.service.TppProteinService;
import edu.unc.major.proteomics.share.service.TppProteinServiceAsync;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Application implements EntryPoint
{

	TppProteinIndProteinServiceAsync proteinIndService = (TppProteinIndProteinServiceAsync) GWT.create(TppProteinIndProteinService.class);
	TppProteinServiceAsync proteinService = (TppProteinServiceAsync) GWT.create(TppProteinService.class);

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad()
	{
		final ServiceDefTarget e = (ServiceDefTarget) proteinIndService;
		e.setServiceEntryPoint(GWT.getModuleBaseURL() + "tppProteinIndProteinService");
		
		final ServiceDefTarget e2 = (ServiceDefTarget) proteinService;
		e2.setServiceEntryPoint(GWT.getModuleBaseURL() + "tppProteinService");
		
		final MultiWordSuggestOracle oracle = new MultiWordSuggestOracle();
		final SuggestBox sb = new SuggestBox();
		final Label sbLabel = new Label(Labels.getSuggestBoxLabel());
		final Button searchButton = new Button(Labels.getSearchButtonLabel());
		final FlowPanel searchPanel = new FlowPanel();

		searchPanel.add(sbLabel);
		searchPanel.add(sb);
		searchPanel.add(searchButton);

		RootPanel.get("content").add(searchPanel);

		AsyncCallback<List<TppProtein>> callback = new AsyncCallback<List<TppProtein>>() {
			public void onSuccess(List<TppProtein> result) {
				for (TppProtein i : result) {
					oracle.add(String.valueOf(i.getIdentifiers().size()));
				}
				SuggestBox temp = new SuggestBox(oracle);
				searchPanel.insert(temp, 1);
				searchPanel.remove(sb);			
			}

			public void onFailure(Throwable caught) {
				Window.alert(caught.getMessage());
			}
		};

		proteinService.getProteins(callback);
	}
}
