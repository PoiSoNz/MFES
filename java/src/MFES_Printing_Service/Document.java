package MFES_Printing_Service;

import java.util.*;
import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class Document {
  public static Number id_count = 1L;
  public Number id;
  public Object color;
  public Object size;
  public Number inkQuantity;
  public Number paperQuantity;
  public Number price;
  public Object status = MFES_Printing_Service.quotes.NotPrintedQuote.getInstance();
  public Client owner = null;

  public void cg_init_Document_1(
      final Object color_,
      final Object size_,
      final Number inkQuantity_,
      final Number paperQuantity_) {

    id = Document.id_count;
    id_count = Document.id_count.longValue() + 1L;
    color = color_;
    size = size_;
    inkQuantity = inkQuantity_;
    paperQuantity = paperQuantity_;
    price = calculatePrice(((Object) color_), ((Object) size_), paperQuantity_);
    status = MFES_Printing_Service.quotes.NotPrintedQuote.getInstance();
    return;
  }

  public Document(
      final Object color_,
      final Object size_,
      final Number inkQuantity_,
      final Number paperQuantity_) {

    cg_init_Document_1(color_, size_, inkQuantity_, paperQuantity_);
  }

  public void queued() {

    status = MFES_Printing_Service.quotes.QueuedQuote.getInstance();
  }

  public void printed() {

    status = MFES_Printing_Service.quotes.PrintedQuote.getInstance();
  }

  public void setOwner(final Client client) {

    owner = client;
  }

  public Document() {}

  public static Number calculatePrice(
      final Object color_1, final Object size_1, final Number paperQuantity_1) {

    return sheetCost(((Object) size_1)).doubleValue()
        * paperQuantity_1.longValue()
        * colorCostFactor(((Object) color_1)).doubleValue();
  }

  public static Number colorCostFactor(final Object color) {

    Number casesExpResult_1 = null;

    Object quotePattern_1 = color;
    Boolean success_1 =
        Utils.equals(quotePattern_1, MFES_Printing_Service.quotes.BlackWhiteQuote.getInstance());

    if (!(success_1)) {
      Object quotePattern_2 = color;
      success_1 =
          Utils.equals(quotePattern_2, MFES_Printing_Service.quotes.ColorQuote.getInstance());

      if (success_1) {
        casesExpResult_1 = 1.5;
      }

    } else {
      casesExpResult_1 = 1L;
    }

    return casesExpResult_1;
  }

  public static Number sheetCost(final Object sheetSize) {

    Number casesExpResult_2 = null;

    Object quotePattern_3 = sheetSize;
    Boolean success_2 =
        Utils.equals(quotePattern_3, MFES_Printing_Service.quotes.A3Quote.getInstance());

    if (!(success_2)) {
      Object quotePattern_4 = sheetSize;
      success_2 = Utils.equals(quotePattern_4, MFES_Printing_Service.quotes.A4Quote.getInstance());

      if (!(success_2)) {
        Object quotePattern_5 = sheetSize;
        success_2 =
            Utils.equals(quotePattern_5, MFES_Printing_Service.quotes.A5Quote.getInstance());

        if (success_2) {
          casesExpResult_2 = 0.03;
        }

      } else {
        casesExpResult_2 = 0.05;
      }

    } else {
      casesExpResult_2 = 0.07;
    }

    return casesExpResult_2;
  }

  public String toString() {

    return "Document{"
        + "id_count := "
        + Utils.toString(id_count)
        + ", id := "
        + Utils.toString(id)
        + ", color := "
        + Utils.toString(color)
        + ", size := "
        + Utils.toString(size)
        + ", inkQuantity := "
        + Utils.toString(inkQuantity)
        + ", paperQuantity := "
        + Utils.toString(paperQuantity)
        + ", price := "
        + Utils.toString(price)
        + ", status := "
        + Utils.toString(status)
        + ", owner := "
        + Utils.toString(owner)
        + "}";
  }
}
