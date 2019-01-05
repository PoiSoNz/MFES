package MFES_Printing_Service;

import java.util.*;
import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class PrintingServiceTest {
  private Printer p1 = new Printer(SeqUtil.seq(50L, 50L), SeqUtil.seq(50L, 50L, 50L), 'a');
  private Printer p2 = new Printer(SeqUtil.seq(10L, 10L), SeqUtil.seq(10L, 10L, 10L), 'a');
  private Printer p3 = new Printer(SeqUtil.seq(3L, 10L), SeqUtil.seq(3L, 10L, 50L), 'b');
  private Document smallDocument =
      new Document(
          MFES_Printing_Service.quotes.BlackWhiteQuote.getInstance(),
          MFES_Printing_Service.quotes.A5Quote.getInstance(),
          2L,
          5L);
  private Document mediumDocument =
      new Document(
          MFES_Printing_Service.quotes.ColorQuote.getInstance(),
          MFES_Printing_Service.quotes.A4Quote.getInstance(),
          5L,
          10L);
  private Document bigDocument =
      new Document(
          MFES_Printing_Service.quotes.BlackWhiteQuote.getInstance(),
          MFES_Printing_Service.quotes.A3Quote.getInstance(),
          10L,
          20L);

  private void assertTrue(final Boolean cond) {

    return;
  }

  protected void assertEqual(final Object expected, final Object actual) {

    if (!(Utils.equals(expected, actual))) {
      IO.print("Actual value (");
      IO.print(((Object) actual));
      IO.print(") different from expected (");
      IO.print(((Object) expected));
      IO.println(")\n");
    }
  }

  private void testClientMoneyDeposit() {

    Client c1 = new Client("Joao");
    Client c2 = new Client("Miguel", 20L);
    assertEqual(c1.moneyBalance, 0L);
    c1.moneyDeposit(4L);
    assertEqual(c1.moneyBalance, 4L);
    c1.moneyDeposit(5L);
    assertEqual(c1.moneyBalance, 9L);
    assertEqual(c2.moneyBalance, 20L);
    c2.moneyDeposit(0.5);
    assertEqual(c2.moneyBalance, 20.5);
    c2.moneyDeposit(2.33);
    assertEqual(c2.moneyBalance, 22.83);
    c2.moneyDeposit(10.15);
    assertEqual(c2.moneyBalance, 32.98);
  }

  private void testClientAssignDocuments() {

    Client client = new Client("Joao");
    Document doc1 =
        new Document(
            MFES_Printing_Service.quotes.BlackWhiteQuote.getInstance(),
            MFES_Printing_Service.quotes.A3Quote.getInstance(),
            2L,
            5L);
    Document doc2 =
        new Document(
            MFES_Printing_Service.quotes.BlackWhiteQuote.getInstance(),
            MFES_Printing_Service.quotes.A3Quote.getInstance(),
            2L,
            5L);
    Document doc3 =
        new Document(
            MFES_Printing_Service.quotes.BlackWhiteQuote.getInstance(),
            MFES_Printing_Service.quotes.A3Quote.getInstance(),
            2L,
            5L);
    client.assignDocument(doc1);
    assertEqual(doc1.owner, client);
    assertTrue(SetUtil.inSet(doc1, SeqUtil.elems(client.documents)));
    client.assignDocument(doc2);
    assertEqual(doc2.owner, client);
    assertTrue(SetUtil.inSet(doc2, SeqUtil.elems(client.documents)));
    client.assignDocument(doc3);
    assertEqual(doc3.owner, client);
    assertTrue(SetUtil.inSet(doc3, SeqUtil.elems(client.documents)));
    assertTrue(SetUtil.subset(SetUtil.set(doc1, doc2, doc3), SeqUtil.elems(client.documents)));
  }

  private void testClientRequestPrints() {

    Client client = new Client("Joao", 10L);
    Document doc1 =
        new Document(
            MFES_Printing_Service.quotes.BlackWhiteQuote.getInstance(),
            MFES_Printing_Service.quotes.A3Quote.getInstance(),
            2L,
            5L);
    Document doc2 =
        new Document(
            MFES_Printing_Service.quotes.BlackWhiteQuote.getInstance(),
            MFES_Printing_Service.quotes.A3Quote.getInstance(),
            2L,
            5L);
    Printer printer1 = new Printer(SeqUtil.seq(50L, 50L), SeqUtil.seq(50L, 50L, 50L), 'a');
    Printer printer2 = new Printer(SeqUtil.seq(50L, 50L), SeqUtil.seq(50L, 50L, 50L), 'b');
    PrintingService ps = new PrintingService(SetUtil.set(printer1, printer2));
    client.assignDocument(doc1);
    client.assignDocument(doc2);
    assertEqual(((Object) doc1.status), MFES_Printing_Service.quotes.NotPrintedQuote.getInstance());
    client.requestPrint(doc1);
    assertEqual(((Object) doc1.status), MFES_Printing_Service.quotes.QueuedQuote.getInstance());
    assertEqual(((Object) doc2.status), MFES_Printing_Service.quotes.NotPrintedQuote.getInstance());
    client.requestPrint(doc2, 'b');
    assertEqual(((Object) doc2.status), MFES_Printing_Service.quotes.QueuedQuote.getInstance());
    assertEqual(client.moneyBalance, 10L - doc1.price.doubleValue() - doc2.price.doubleValue());
    assertTrue(SetUtil.subset(SetUtil.set(doc1), SeqUtil.elems(printer1.printingQueue)));
    assertTrue(SetUtil.subset(SetUtil.set(doc2), SeqUtil.elems(printer2.printingQueue)));
  }

  public static void main() {

    new PrintingServiceTest().testClientMoneyDeposit();
    new PrintingServiceTest().testClientAssignDocuments();
    new PrintingServiceTest().testClientRequestPrints();
  }

  public PrintingServiceTest() {}

  public String toString() {

    return "PrintingServiceTest{"
        + "p1 := "
        + Utils.toString(p1)
        + ", p2 := "
        + Utils.toString(p2)
        + ", p3 := "
        + Utils.toString(p3)
        + ", smallDocument := "
        + Utils.toString(smallDocument)
        + ", mediumDocument := "
        + Utils.toString(mediumDocument)
        + ", bigDocument := "
        + Utils.toString(bigDocument)
        + "}";
  }
}
