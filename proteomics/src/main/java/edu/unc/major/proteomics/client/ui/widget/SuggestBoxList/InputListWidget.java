package edu.unc.major.proteomics.client.ui.widget.SuggestBoxList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.TextBox;

/**
 * Facebook Style Autocompleter.
 * CSS and DIV structure from http://loopj.com/tokeninput/demo.html:
 */
public class InputListWidget extends Composite {
    List<String> itemsSelected = new ArrayList<String>();
    ArrayList<String> options;
    MultiWordSuggestOracle oracle;
    Boolean isPaste = false;
    Boolean isKeyboard = true;
    
    
    public InputListWidget() {
    	this(new HashSet<String>());
    }

    public InputListWidget(Collection<String> options) {
    	this.options = new ArrayList<String>(options);
    	Collections.sort(this.options);
    	oracle = new MultiWordSuggestOracle();
    	oracle.addAll(options);
    	
    	sinkEvents(Event.ONPASTE);

    	
        SimplePanel panel = new SimplePanel();
        panel.setStyleName("autoSuggestList");
        initWidget(panel);
        // 2. Show the following element structure and set the last <div> to display: block
        /*
        <ul class="token-input-list-facebook">
            <li class="token-input-input-token-facebook">
                <input type="text" style="outline-color: -moz-use-text-color; outline-style: none; outline-width: medium;"/>
            </li>
        </ul>
        <div class="token-input-dropdown-facebook" style="display: none;"/>
         */
        final BulletList list = new BulletList();
        list.setStyleName("token-input-list-facebook");
        final ListItem item = new ListItem();
        item.setStyleName("token-input-input-token-facebook");
        final TextBox itemBox = new TextBox();
        itemBox.getElement().setAttribute("style", "outline-color: -moz-use-text-color; outline-style: none; outline-width: medium;");
        final SuggestBox box = new SuggestBox(oracle, itemBox);
        box.getElement().setId("suggestion_box");
        box.setLimit(5);
        item.add(box);
        list.add(item);
        
        itemBox.addKeyPressHandler(new KeyPressHandler() {
        	public void onKeyPress(KeyPressEvent event) {
				if (event.getCharCode() == ' ') {
					deselectItem(itemBox, list, true);
				}
			}        	
        });
        
        itemBox.addKeyUpHandler(new KeyUpHandler() {
        	public void onKeyUp(KeyUpEvent event) {
        		if (isKeyboard) {
        			deselectItem(itemBox, list, false);
        		} else {
        			deselectItem(itemBox, list, false);
        		}
        		isKeyboard = true;
			}        	
        });
        
        // this needs to be on the itemBox rather than box, or backspace will get executed twice
        itemBox.addKeyDownHandler(new KeyDownHandler() {
            public void onKeyDown(KeyDownEvent event) {
            	if (isPaste) {
            		isKeyboard = false;
            		deselectItem(itemBox, list, true);
            	} 
                if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER ) {
                        deselectItem(itemBox, list, true);
                }
                // handle backspace
                if (event.getNativeKeyCode() == KeyCodes.KEY_BACKSPACE) {
                    if ("".equals(itemBox.getValue().trim())) {
                        ListItem li = (ListItem) list.getWidget(list.getWidgetCount() - 2);
                        Paragraph p = (Paragraph) li.getWidget(0);
                        if (itemsSelected.contains(p.getText())) {
                            itemsSelected.remove(p.getText());
                            GWT.log("Removing selected item '" + p.getText() + "'", null);
                            GWT.log("Remaining: " + itemsSelected, null);
                        }
                        list.remove(li);
                        itemBox.setFocus(true);
                    }
                }
            }
        });

        box.addSelectionHandler(new SelectionHandler<SuggestOracle.Suggestion>() {
            public void onSelection(SelectionEvent selectionEvent) {
                deselectItem(itemBox, list, true);
            }
        });

        panel.add(list);

        panel.getElement().setAttribute("onclick", "document.getElementById('suggestion_box').focus()");
        box.setFocus(true);
        /* Div structure after a few elements have been added:
            <ul class="token-input-list-facebook">
                <li class="token-input-token-facebook">
                    <p>What's New Scooby-Doo?</p>
                    <span class="token-input-delete-token-facebook">x</span>
                </li>
                <li class="token-input-token-facebook">
                    <p>Fear Factor</p>
                    <span class="token-input-delete-token-facebook">x</span>
                 </li>
                 <li class="token-input-input-token-facebook">
                     <input type="text" style="outline-color: -moz-use-text-color; outline-style: none; outline-width: medium;"/>
                 </li>
            </ul>
         */
    }
    
    private void deselectItemText(final String item, final BulletList list) {
    	if (item != null && !"".equals(item)) {
    		final ListItem displayItem = new ListItem();
            if (Collections.binarySearch(options, item) >= 0) {
            	displayItem.setStyleName("token-input-token-facebook");
            } else {
            	displayItem.setStyleName("token-input-token-facebook");
            	displayItem.addStyleName("false");
            }
            
            Paragraph p = new Paragraph(item);
            
            Span span = new Span("x");
            span.addClickHandler(new ClickHandler() {
                public void onClick(ClickEvent clickEvent) {
                    removeListItem(displayItem, list);
                }
            });

            displayItem.add(p);
            displayItem.add(span);
            // hold the original value of the item selected

            GWT.log("Adding selected item '" + item + "'", null);
            itemsSelected.add(item);
            GWT.log("Total: " + itemsSelected, null);

            list.insert(displayItem, list.getWidgetCount() - 1);
    	}
    }

    private void deselectItem(final TextBox itemBox, final BulletList list, final Boolean doLast) {
        if (itemBox.getValue() != null && !"".equals(itemBox.getValue().trim())) {
        	String output = "";
            if (isPaste) {
            	output = parseInput(itemBox, list, true);
            } else if (doLast){
            	deselectItemText(itemBox.getValue(), list);
            } else {
            	output = itemBox.getText();
            }
        	
            itemBox.setValue(output);
            itemBox.setFocus(true);
        }
    }

    private void removeListItem(ListItem displayItem, BulletList list) {
        GWT.log("Removing: " + displayItem.getWidget(0).getElement().getInnerHTML(), null);
        itemsSelected.remove(displayItem.getWidget(0).getElement().getInnerHTML());
        list.remove(displayItem);
    }
    
    private String parseInput(final TextBox itemBox, final BulletList list, final Boolean doLast) {
    	isPaste = false;
		String[] items = itemBox.getText().split("\\W");
		for (int i = 0; i < items.length -1; ++i) {
			deselectItemText(items[i],list);
		}
		if (doLast) {
			deselectItemText(items[items.length-1], list);
			return "";
		}
		return items[items.length-1];
    }
    
    @Override
    public void onBrowserEvent(Event event)
    {
        super.onBrowserEvent(event);
        switch (event.getTypeInt())
        {
            case Event.ONPASTE:
            {
            	isPaste = true;
                break;
            }
        }
    }

}
