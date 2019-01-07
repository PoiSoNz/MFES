package Print_Service.quotes;

import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class NotPrintedQuote {
  private static int hc = 0;
  private static NotPrintedQuote instance = null;

  public NotPrintedQuote() {

    if (Utils.equals(hc, 0)) {
      hc = super.hashCode();
    }
  }

  public static NotPrintedQuote getInstance() {

    if (Utils.equals(instance, null)) {
      instance = new NotPrintedQuote();
    }

    return instance;
  }

  public int hashCode() {

    return hc;
  }

  public boolean equals(final Object obj) {

    return obj instanceof NotPrintedQuote;
  }

  public String toString() {

    return "<NotPrinted>";
  }
}
