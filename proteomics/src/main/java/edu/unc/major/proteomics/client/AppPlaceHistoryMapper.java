package edu.unc.major.proteomics.client;

import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;

import edu.unc.major.proteomics.client.ui.place.SearchPlace;

@WithTokenizers({SearchPlace.Tokenizer.class})
public interface AppPlaceHistoryMapper extends PlaceHistoryMapper
{
}
