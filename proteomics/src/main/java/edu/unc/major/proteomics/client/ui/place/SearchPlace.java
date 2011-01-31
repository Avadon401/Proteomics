package edu.unc.major.proteomics.client.ui.place;

import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class SearchPlace extends Place{
        	
	public static class Tokenizer implements PlaceTokenizer<SearchPlace> {
        @Override
        public String getToken(SearchPlace place) {
        	 GWT.log("token");
            return null; //This place doesn't store any state with the url
        }

        @Override
        public SearchPlace getPlace(String token) {
        	 GWT.log("new place");
            return new SearchPlace();
        }
    }

}
