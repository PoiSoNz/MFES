package MFES_Printing_Service.quotes;

import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class BlackInkEmptyQuote {
  private static int hc = 0;
  private static BlackInkEmptyQuote instance = null;

  public BlackInkEmptyQuote() {

    if (Utils.equals(hc, 0)) {
      hc = super.hashCode();
    }
  }

  public static BlackInkEmptyQuote getInstance() {

    if (Utils.equals(instance, null)) {
      instance = new BlackInkEmptyQuote();
    }

    return instance;
  }

  public int hashCode() {

    return hc;
  }

  public boolean equals(final Object obj) {

    return obj instanceof BlackInkEmptyQuote;
  }

  public String toString() {

    return "<BlackInkEmpty>";
  }
}
