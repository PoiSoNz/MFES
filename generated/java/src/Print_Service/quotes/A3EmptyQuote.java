package Print_Service.quotes;

import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class A3EmptyQuote {
  private static int hc = 0;
  private static A3EmptyQuote instance = null;

  public A3EmptyQuote() {

    if (Utils.equals(hc, 0)) {
      hc = super.hashCode();
    }
  }

  public static A3EmptyQuote getInstance() {

    if (Utils.equals(instance, null)) {
      instance = new A3EmptyQuote();
    }

    return instance;
  }

  public int hashCode() {

    return hc;
  }

  public boolean equals(final Object obj) {

    return obj instanceof A3EmptyQuote;
  }

  public String toString() {

    return "<A3Empty>";
  }
}
