package Print_Service.quotes;

import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class QueuedQuote {
  private static int hc = 0;
  private static QueuedQuote instance = null;

  public QueuedQuote() {

    if (Utils.equals(hc, 0)) {
      hc = super.hashCode();
    }
  }

  public static QueuedQuote getInstance() {

    if (Utils.equals(instance, null)) {
      instance = new QueuedQuote();
    }

    return instance;
  }

  public int hashCode() {

    return hc;
  }

  public boolean equals(final Object obj) {

    return obj instanceof QueuedQuote;
  }

  public String toString() {

    return "<Queued>";
  }
}
