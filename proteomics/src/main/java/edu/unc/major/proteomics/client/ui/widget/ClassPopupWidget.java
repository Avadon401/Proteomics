package edu.unc.major.proteomics.client.ui.widget;

import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public abstract class ClassPopupWidget<T> extends PopupPanel{
	protected T toDisplay;
	
	public ClassPopupWidget() {
		this.setAutoHideEnabled(true);
		this.setAnimationEnabled(true);
		this.setGlassEnabled(false);
		this.setAutoHideOnHistoryEventsEnabled(true);
		SimplePanel container = new SimplePanel();
		Image loading = new Image("/edu.unc.major.proteomics.Application/images/ajax-loader.gif");
		container.add(loading);
		setWidget(container);		
	}
	
	protected abstract Widget createWidget();
	
	public abstract void getDataAndShow(Long id, int left, int top);
	
	public void showPopup(int left, int top) {
		this.setPopupPosition(left, top);
		show();
	}
}
