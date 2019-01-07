package Print_Service.quotes;

import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class ColorQuote {
  private static int hc = 0;
  private static ColorQuote instance = null;

  public ColorQuote() {

    if (Utils.equals(hc, 0)) {
      hc = super.hashCode();
    }
  }

  public static ColorQuote getInstance() {

    if (Utils.equals(instance, null)) {
      instance = new ColorQuote();
    }

    return instance;
  }

  public int hashCode() {

    return hc;
  }

  public boolean equals(final Object obj) {

    return obj instanceof ColorQuote;
  }

  public String toString() {

    return "<Color>";
  }
}
