package MFES_Printing_Service.quotes;

import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class DamagedQuote {
  private static int hc = 0;
  private static DamagedQuote instance = null;

  public DamagedQuote() {

    if (Utils.equals(hc, 0)) {
      hc = super.hashCode();
    }
  }

  public static DamagedQuote getInstance() {

    if (Utils.equals(instance, null)) {
      instance = new DamagedQuote();
    }

    return instance;
  }

  public int hashCode() {

    return hc;
  }

  public boolean equals(final Object obj) {

    return obj instanceof DamagedQuote;
  }

  public String toString() {

    return "<Damaged>";
  }
}
