package Print_Service.quotes;

import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class A5EmptyQuote {
  private static int hc = 0;
  private static A5EmptyQuote instance = null;

  public A5EmptyQuote() {

    if (Utils.equals(hc, 0)) {
      hc = super.hashCode();
    }
  }

  public static A5EmptyQuote getInstance() {

    if (Utils.equals(instance, null)) {
      instance = new A5EmptyQuote();
    }

    return instance;
  }

  public int hashCode() {

    return hc;
  }

  public boolean equals(final Object obj) {

    return obj instanceof A5EmptyQuote;
  }

  public String toString() {

    return "<A5Empty>";
  }
}
