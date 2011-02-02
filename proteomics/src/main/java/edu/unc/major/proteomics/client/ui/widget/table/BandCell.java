package edu.unc.major.proteomics.client.ui.widget.table;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.Window;

import edu.unc.major.proteomics.share.model.Band;

public class BandCell extends AbstractCell<Band>{

	@Override
	public void render(Band value, Object key, SafeHtmlBuilder sb) {
		if (value == null) return;
		
		sb.appendHtmlConstant("<table width='100%'>");
		
		sb.appendHtmlConstant("<tr><td colspan='2'>");
		
		String bandName = value.getName();
		if (bandName.contains("---")) {
			bandName = bandName.substring(bandName.indexOf("---")+3);
		}
		
		sb.appendEscaped(value.getBait().getGene().getGeneName());
		if (!"".equals(value.getBait().getMutation())) {
			sb.appendEscaped(" (" + value.getBait().getMutation() + ")");
		}
		sb.appendHtmlConstant("</td><td align='right'>");
		sb.appendEscaped(" Protein Count: " + String.valueOf(value.getProteinCount()));
		sb.appendHtmlConstant("</td></tr><tr><td colspan='3'>");
		sb.appendEscaped(bandName);
		sb.appendHtmlConstant("</td></tr></table>");
		
	}

}
