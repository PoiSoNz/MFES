package MFES_Printing_Service.quotes;

import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class ColorInkEmptyQuote {
  private static int hc = 0;
  private static ColorInkEmptyQuote instance = null;

  public ColorInkEmptyQuote() {

    if (Utils.equals(hc, 0)) {
      hc = super.hashCode();
    }
  }

  public static ColorInkEmptyQuote getInstance() {

    if (Utils.equals(instance, null)) {
      instance = new ColorInkEmptyQuote();
    }

    return instance;
  }

  public int hashCode() {

    return hc;
  }

  public boolean equals(final Object obj) {

    return obj instanceof ColorInkEmptyQuote;
  }

  public String toString() {

    return "<ColorInkEmpty>";
  }
}
