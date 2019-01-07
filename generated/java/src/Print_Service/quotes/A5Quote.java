package Print_Service.quotes;

import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class A5Quote {
  private static int hc = 0;
  private static A5Quote instance = null;

  public A5Quote() {

    if (Utils.equals(hc, 0)) {
      hc = super.hashCode();
    }
  }

  public static A5Quote getInstance() {

    if (Utils.equals(instance, null)) {
      instance = new A5Quote();
    }

    return instance;
  }

  public int hashCode() {

    return hc;
  }

  public boolean equals(final Object obj) {

    return obj instanceof A5Quote;
  }

  public String toString() {

    return "<A5>";
  }
}
