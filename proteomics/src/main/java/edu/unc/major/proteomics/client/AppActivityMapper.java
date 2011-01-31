package edu.unc.major.proteomics.client;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;

import edu.unc.major.proteomics.client.ui.activity.SearchActivity;
import edu.unc.major.proteomics.client.ui.place.SearchPlace;

public class AppActivityMapper implements ActivityMapper {
    private ClientFactory clientFactory;

    public AppActivityMapper(ClientFactory clientFactory) {
        super();
        this.clientFactory = clientFactory;
    }

    @Override
    public Activity getActivity(Place place) {
        if (place instanceof SearchPlace)
            return new SearchActivity((SearchPlace) place, clientFactory);
        return null;
    }
}
