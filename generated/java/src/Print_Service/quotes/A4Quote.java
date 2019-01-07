package Print_Service.quotes;

import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class A4Quote {
  private static int hc = 0;
  private static A4Quote instance = null;

  public A4Quote() {

    if (Utils.equals(hc, 0)) {
      hc = super.hashCode();
    }
  }

  public static A4Quote getInstance() {

    if (Utils.equals(instance, null)) {
      instance = new A4Quote();
    }

    return instance;
  }

  public int hashCode() {

    return hc;
  }

  public boolean equals(final Object obj) {

    return obj instanceof A4Quote;
  }

  public String toString() {

    return "<A4>";
  }
}
