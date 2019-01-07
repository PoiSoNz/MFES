package Print_Service.quotes;

import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class BlackWhiteQuote {
  private static int hc = 0;
  private static BlackWhiteQuote instance = null;

  public BlackWhiteQuote() {

    if (Utils.equals(hc, 0)) {
      hc = super.hashCode();
    }
  }

  public static BlackWhiteQuote getInstance() {

    if (Utils.equals(instance, null)) {
      instance = new BlackWhiteQuote();
    }

    return instance;
  }

  public int hashCode() {

    return hc;
  }

  public boolean equals(final Object obj) {

    return obj instanceof BlackWhiteQuote;
  }

  public String toString() {

    return "<BlackWhite>";
  }
}
