package edu.unc.major.proteomics.client.ui.widget.table;

import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.view.client.ProvidesKey;

public class Table<T> extends CellTable<T>{
	  /**
	   * Constructs a table with a default page size of 15.
	   */
	  public Table() {
	    super();
	  }

	  /**
	   * Constructs a table with the given page size.
	   *
	   * @param pageSize the page size
	   */
	  public Table(final int pageSize) {
	    super(pageSize);
	  }

	  /**
	   * Constructs a table with a default page size of 15, and the given
	   * {@link ProvidesKey key provider}.
	   *
	   * @param keyProvider an instance of ProvidesKey<T>, or null if the record
	   *          object should act as its own key
	   */
	  public Table(ProvidesKey<T> keyProvider) {
	    super(keyProvider);
	  }

	  /**
	   * Constructs a table with the given page size with the specified
	   * {@link Resources}.
	   *
	   * @param pageSize the page size
	   * @param resources the resources to use for this widget
	   */
	  public Table(final int pageSize, Resources resources) {
	    super(pageSize, resources);
	  }

	  /**
	   * Constructs a table with the given page size and the given
	   * {@link ProvidesKey key provider}.
	   *
	   * @param pageSize the page size
	   * @param keyProvider an instance of ProvidesKey<T>, or null if the record
	   *          object should act as its own key
	   */
	  public Table(final int pageSize, ProvidesKey<T> keyProvider) {
	    super(pageSize, keyProvider);
	  }

	  /**
	   * Constructs a table with the given page size, the specified
	   * {@link Resources}, and the given key provider.
	   *
	   * @param pageSize the page size
	   * @param resources the resources to use for this widget
	   * @param keyProvider an instance of ProvidesKey<T>, or null if the record
	   *          object should act as its own key
	   */
	  public Table(final int pageSize, Resources resources,
	      ProvidesKey<T> keyProvider) {
		  super(pageSize, resources, keyProvider);
	  }
	  
	  public void addColumns(final AdvancedColumn<T, ?> col) {
		  for (ColumnWithHeader<T, ?> c : col.getColumns()){
			  this.addColumn(c,c.getHeader());
		  }
	  }
}
