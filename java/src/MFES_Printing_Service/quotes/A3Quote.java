package MFES_Printing_Service.quotes;

import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class A3Quote {
  private static int hc = 0;
  private static A3Quote instance = null;

  public A3Quote() {

    if (Utils.equals(hc, 0)) {
      hc = super.hashCode();
    }
  }

  public static A3Quote getInstance() {

    if (Utils.equals(instance, null)) {
      instance = new A3Quote();
    }

    return instance;
  }

  public int hashCode() {

    return hc;
  }

  public boolean equals(final Object obj) {

    return obj instanceof A3Quote;
  }

  public String toString() {

    return "<A3>";
  }
}
