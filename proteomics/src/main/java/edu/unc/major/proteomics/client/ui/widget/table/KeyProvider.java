package edu.unc.major.proteomics.client.ui.widget.table;

import com.google.gwt.view.client.ProvidesKey;

import edu.unc.major.proteomics.share.model.Band;
import edu.unc.major.proteomics.share.model.SiRNAVal;
import edu.unc.major.proteomics.share.model.TppProtein;

public class KeyProvider {

	public static ProvidesKey<Band> BandKeyProvider = new ProvidesKey<Band>() {
		@Override
		public Object getKey(Band item) {
			return (item == null) ? null : item.getId();
		}
	};
	
	public static ProvidesKey<TppProtein> TppProteinKeyProvider = new ProvidesKey<TppProtein>() {
		@Override
		public Object getKey(TppProtein item) {
			return (item == null) ? null : item.getId();
		}
	};
	
	public static ProvidesKey<SiRNAVal> SiRNAValKeyProvider = new ProvidesKey<SiRNAVal>() {
		@Override
		public Object getKey(SiRNAVal item) {
			return (item == null) ? null : item.getId();
		}
	};

}
