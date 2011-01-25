package edu.unc.major.proteomics.client;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Timer;
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
		
		final InputListWidget sb = new InputListWidget();
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

		AsyncCallback<List<TppProtein>> callback = new AsyncCallback<List<TppProtein>>() {
			public void onSuccess(List<TppProtein> result) {
				Set<String> options = new HashSet<String>();
				for (TppProtein i : result) {
					//options.add(String.valueOf(i.getIdentifiers().size()));
				}
				for (int i = 0; i < 1000; ++i) {
					options.add(String.valueOf(i));
				}
				InputListWidget temp = new InputListWidget(options);
				searchPanel.insert(temp, 1);
				searchPanel.remove(sb);			
			     // Throw away the loading message that users see while GWT
			     // starts up and is able to finish loading its resources.
				    	final Element lm = RootPanel.get("loadingmessage").getElement();
				        DOM.removeChild(DOM.getParent(lm), lm);

			}

			public void onFailure(Throwable caught) {
				Window.alert(caught.getMessage());
			}
		};

		proteinService.getProteins(callback);
	}
}
