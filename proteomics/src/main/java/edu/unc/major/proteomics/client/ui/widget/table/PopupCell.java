package edu.unc.major.proteomics.client.ui.widget.table;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;

public class PopupCell extends AbstractCell<String> {

    @Override
    public void render(String value, Object key, SafeHtmlBuilder sb) {

      // Always do a null check on the value. Cell widgets can pass null to cells
      // if the underlying data contains a null, or if the data arrives out of order.
      if (value == null) {
        return;
      }

      // If the value comes from the user, we escape it to avoid XSS attacks.
      SafeHtml safeValue = SafeHtmlUtils.fromString(value);

      // Append some HTML that sets the text color.
      sb.appendHtmlConstant("<div style=\"color:" + safeValue.asString()
          + "\">");
      sb.append(safeValue);
      sb.appendHtmlConstant("</div>");
    }
  }

