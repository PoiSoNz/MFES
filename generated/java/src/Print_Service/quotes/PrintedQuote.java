package Print_Service.quotes;

import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class PrintedQuote {
  private static int hc = 0;
  private static PrintedQuote instance = null;

  public PrintedQuote() {

    if (Utils.equals(hc, 0)) {
      hc = super.hashCode();
    }
  }

  public static PrintedQuote getInstance() {

    if (Utils.equals(instance, null)) {
      instance = new PrintedQuote();
    }

    return instance;
  }

  public int hashCode() {

    return hc;
  }

  public boolean equals(final Object obj) {

    return obj instanceof PrintedQuote;
  }

  public String toString() {

    return "<Printed>";
  }
}
