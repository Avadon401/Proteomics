/*
 * Copyright 2010 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package edu.unc.major.proteomics.client.ui.widget.table;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionModel.AbstractSelectionModel;

/**
 * A simple selection model that allows multiple objects to be selected.
 *
 * @param <T> the record data type
 */
public class OrderedMultiSelectionModel<T, K> extends AbstractSelectionModel<T> {

  // Ensure one value per key
  private final HashMap<K, T> selectedSet = new HashMap<K, T>();
  private final List<K> order = new LinkedList<K>(); 

  private class SelectionChange<T> {
	  public T item;
	  public Boolean selected;
	  public SelectionChange(T item, Boolean selected) {
		  this.item = item;
		  this.selected = selected;
	  }
  }
  
  private List<SelectionChange<T>> selectionChanges = new LinkedList<SelectionChange<T>>();
  //private final HashMap<T, Boolean> selectionChanges = new HashMap<T, Boolean>();

  /**
   * Constructs a MultiSelectionModel without a key provider.
   */
  public OrderedMultiSelectionModel() {
    super(null);
  }
  
  /**
   * Constructs a MultiSelectionModel with the given key provider.
   *
   * @param keyProvider an instance of ProvidesKey<T>, or null if the record
   *        object should act as its own key
   */
  public OrderedMultiSelectionModel(ProvidesKey<T> keyProvider) {
    super(keyProvider);
  }

  /**
   * Get the set of selected items as a copy.
   *
   * @return the set of selected items
   */
  public List<T> getSelectedList() {
    resolveChanges();
    List<T> ordered = new LinkedList<T>();
    for (K key : order) {
    	ordered.add(selectedSet.get(key));
    }
    return ordered;
  }
  
  public List<K> getSelectedKeys() {
	  return order;
  }

  public boolean isSelected(T object) {
    resolveChanges();
    return selectedSet.containsKey(getKey(object));
  }

  public void setSelected(T object, boolean selected) {
    selectionChanges.add(new SelectionChange<T>(object, selected));
    scheduleSelectionChangeEvent();
  }

  @Override
  protected void fireSelectionChangeEvent() {
    if (isEventScheduled()) {
      setEventCancelled(true);
    }
    resolveChanges();
  }

  private void resolveChanges() {
    if (selectionChanges.isEmpty()) {
      return;
    }

    boolean changed = false;
    for (SelectionChange<T> entry : selectionChanges) {
      T object = entry.item;
      boolean selected = entry.selected;

      K key = (K) getKey(object);
      T oldValue = selectedSet.get(key);
      if (selected) {
        if (oldValue == null || !oldValue.equals(object)) {
          selectedSet.put(key, object);
          order.add(key);
          changed = true;
        }
      } else {
        if (oldValue != null) {
          selectedSet.remove(key);
          order.remove(key);
          changed = true;
        }
      }
    }
    selectionChanges.clear();

    // Fire a selection change event.
    if (changed) {
      SelectionChangeEvent.fire(this);
    }
  }
}
