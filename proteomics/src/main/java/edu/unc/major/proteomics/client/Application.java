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
import edu.unc.major.proteomics.share.model.TppProteinIndProtein;
import edu.unc.major.proteomics.share.service.TppProteinIndProteinService;
import edu.unc.major.proteomics.share.service.TppProteinIndProteinServiceAsync;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Application implements EntryPoint
{

	TppProteinIndProteinServiceAsync proteinService = (TppProteinIndProteinServiceAsync) GWT.create(TppProteinIndProteinService.class);

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad()
	{
		final ServiceDefTarget e = (ServiceDefTarget) proteinService;
		e.setServiceEntryPoint(GWT.getModuleBaseURL() + "tppProteinIndProteinService");
		final MultiWordSuggestOracle oracle = new MultiWordSuggestOracle();
		final SuggestBox sb = new SuggestBox();
		final Label sbLabel = new Label(Labels.getSuggestBoxLabel());
		final Button searchButton = new Button(Labels.getSearchButtonLabel());
		final FlowPanel searchPanel = new FlowPanel();

		searchPanel.add(sbLabel);
		searchPanel.add(sb);
		searchPanel.add(searchButton);

		RootPanel.get("content").add(searchPanel);

		AsyncCallback<List<TppProteinIndProtein>> callback = new AsyncCallback<List<TppProteinIndProtein>>() {
			public void onSuccess(List<TppProteinIndProtein> result) {
				for (TppProteinIndProtein i : result) {
					oracle.add(i.getGeneName());
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
