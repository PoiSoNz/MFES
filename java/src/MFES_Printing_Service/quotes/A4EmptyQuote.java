package MFES_Printing_Service.quotes;

import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class A4EmptyQuote {
  private static int hc = 0;
  private static A4EmptyQuote instance = null;

  public A4EmptyQuote() {

    if (Utils.equals(hc, 0)) {
      hc = super.hashCode();
    }
  }

  public static A4EmptyQuote getInstance() {

    if (Utils.equals(instance, null)) {
      instance = new A4EmptyQuote();
    }

    return instance;
  }

  public int hashCode() {

    return hc;
  }

  public boolean equals(final Object obj) {

    return obj instanceof A4EmptyQuote;
  }

  public String toString() {

    return "<A4Empty>";
  }
}
