package edu.unc.major.proteomics.client.ui.widget.SuggestBoxList;

import java.util.ArrayList;
import java.util.Collection;
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
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.TextBox;

import edu.unc.major.proteomics.client.Application;

/**
 * Facebook Style Autocompleter.
 * CSS and DIV structure from http://loopj.com/tokeninput/demo.html:
 */
public abstract class InputListWidget extends Composite {
    private List<String> itemsSelected = new ArrayList<String>();
    private MultiWordSuggestOracle oracle;
    private Boolean isPaste = false;
    private Boolean isKeyboard = true;
    private Boolean isUp = false;
    protected String[] geneNames;
    final private BulletList list = new BulletList();
    private String output = "";
    final private TextBox itemBox = new TextBox();
    
    protected AsyncCallback<Boolean[]> callback = new AsyncCallback<Boolean[]>() {
		public void onSuccess(Boolean[] result) {
			for (String name : geneNames)
				GWT.log(name);
			for (int i = 0; i < result.length; ++i) {
				deselectItemText(geneNames[i], result[i], list);
			}		
            geneNames = null;
		}

		public void onFailure(Throwable arg0) {

		}
	};
    
    
    public InputListWidget() {
    	this(new HashSet<String>());
    }

    public InputListWidget(Collection<String> options) {
    	//Collections.sort(this.options);
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
        list.setStyleName("token-input-list-facebook");
        final ListItem item = new ListItem();
        item.setStyleName("token-input-input-token-facebook");
        itemBox.getElement().setAttribute("style", "outline-color: -moz-use-text-color; outline-style: none; outline-width: medium;");
        final SuggestBox box = new SuggestBox(oracle, itemBox);
        //final TextBox box = new TextBox();
        box.getElement().setId("suggestion_box");
        //box.setLimit(10);
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
                    if ("".equals(itemBox.getValue().trim()) && list.getWidgetCount() > 1) {
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
    
    private void deselectItemText(final String item, final Boolean match, final BulletList list) {
    	if (item != null && !"".equals(item)) {
    		final ListItem displayItem = new ListItem();
            if (match) {
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
        	output = "";
        	System.out.print(isPaste);
            if (isPaste) {
            	output = parseInput(itemBox, list, true);
            } else if (doLast){
            	output = parseInput(itemBox, list, true);
            	//itemBox.setValue(itemBox.getValue().toUpperCase());
            	//getMatches(new String[] {itemBox.getValue()});
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
    	itemBox.setValue(itemBox.getValue().toUpperCase());
    	isPaste = false;
		String[] items = itemBox.getText().split("[^a-zA-Z_0-9\\-]");
		if (doLast) {
			geneNames = items;
			getMatches(items);
			return "";
		} else {
			String returnVal = items[items.length-1];
			String[] firstItems = new String[items.length-1];
			for (int i = 0; i < items.length-1; ++i) {
				firstItems[i] = items[i];
			}
			geneNames = firstItems;
			getMatches(firstItems);
			return returnVal;
		}
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
    
    public abstract void getMatches(String[] geneNames);

}
