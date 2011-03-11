package edu.unc.major.proteomics.client.ui.widget;

import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

import edu.unc.major.proteomics.client.Application;
import edu.unc.major.proteomics.client.constants.Labels;
import edu.unc.major.proteomics.share.dao.PageResults;
import edu.unc.major.proteomics.share.model.Band;
import edu.unc.major.proteomics.share.model.TppProtein;

public class BandPopupWidget extends ClassPopupWidget<Band> {

	public BandPopupWidget() {
		super();
	}
	
	private SafeHtmlBuilder addRow(String label, String value, SafeHtmlBuilder sb) {
		sb.appendHtmlConstant("<tr><th align='right' class='th'>");
		sb.appendEscaped(label);
		sb.appendHtmlConstant("</th><td align='left' class='td'>");
		sb.appendEscaped(value);
		sb.appendHtmlConstant("</td></tr>");
		return sb;
	}

	@Override
	protected Widget createWidget() {
		SimplePanel container = new SimplePanel();
		HTML html = new HTML();
		SafeHtmlBuilder sb = new SafeHtmlBuilder();
		
		sb.appendHtmlConstant("<table width='100%'>");	
		
		sb = addRow(Labels.getBaitLabel(), toDisplay.getBait().getMutatedName(), sb);
		sb = addRow(Labels.getBandNameLabel(), toDisplay.getName(), sb);
		sb = addRow(Labels.getExperimentBiologicalMaterialLabel(), toDisplay.getExperiment().getBiologicalMaterialName(), sb);
		sb = addRow(Labels.getExperimentAffinityPurificationLabel(), toDisplay.getExperiment().getAffinityPurificationName(), sb);
		sb = addRow(Labels.getExperimentLcmsLabel(), toDisplay.getExperiment().getLcmsName(), sb);
		sb = addRow(Labels.getExperimentPeptidePreparationLabel(), toDisplay.getExperiment().getPeptidePreparationName(), sb);
		sb = addRow(Labels.getExperimentNotesLabel(), toDisplay.getExperiment().getNotes(), sb);
		sb = addRow(Labels.getBandProteinCountLabel(), String.valueOf(toDisplay.getProteinCount()), sb);
		sb = addRow(Labels.getBandBaitCountLabel(), String.valueOf(toDisplay.getBaitCount()), sb);	
		
		sb.appendHtmlConstant("</table>");
		
		html.setHTML(sb.toSafeHtml());
		container.add(html);
		return container;
	}

	@Override
	public void getDataAndShow(Long id, final int left,final int top) {
		showPopup(left, top);
		AsyncCallback<Band> callback = new AsyncCallback<Band>() {
			public void onFailure(Throwable caught) {
				Window.alert(caught.getMessage());
			}
			public void onSuccess(Band result) {
				toDisplay = result;
				setWidget(createWidget());
			}	
		};
		Application.bandService.getById(id, callback);
	}

}
