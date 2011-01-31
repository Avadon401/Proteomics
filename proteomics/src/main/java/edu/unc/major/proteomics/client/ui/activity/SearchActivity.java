package edu.unc.major.proteomics.client.ui.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import edu.unc.major.proteomics.client.ClientFactory;
import edu.unc.major.proteomics.client.ui.place.SearchPlace;
import edu.unc.major.proteomics.client.ui.view.SearchView;

public class SearchActivity extends AbstractActivity implements SearchView.Presenter {
    // Used to obtain views, eventBus, placeController
    // Alternatively, could be injected via GIN
    private ClientFactory clientFactory;

    public SearchActivity(SearchPlace place, ClientFactory clientFactory) {
        this.clientFactory = clientFactory;
    }

    /**
     * Invoked by the ActivityManager to start a new Activity
     */
    @Override
    public void start(AcceptsOneWidget containerWidget, EventBus eventBus) {
    	SearchView searchView = clientFactory.getSearchView();
    	searchView.setPresenter(this);
        containerWidget.setWidget(searchView.asWidget());
    }

    /**
     * Ask user before stopping this activity
     */
    @Override
    public String mayStop() {
        return null;
    }

    /**
     * Navigate to a new Place in the browser
     */
    public void goTo(Place place) {
        clientFactory.getPlaceController().goTo(place);
    }
}
