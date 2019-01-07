package Print_Service;

import java.util.*;
import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class PrintingServiceTest {
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
            Print_Service.quotes.BlackWhiteQuote.getInstance(),
            Print_Service.quotes.A3Quote.getInstance(),
            2L,
            5L);
    Document doc2 =
        new Document(
            Print_Service.quotes.BlackWhiteQuote.getInstance(),
            Print_Service.quotes.A3Quote.getInstance(),
            2L,
            5L);
    Document doc3 =
        new Document(
            Print_Service.quotes.BlackWhiteQuote.getInstance(),
            Print_Service.quotes.A3Quote.getInstance(),
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
            Print_Service.quotes.BlackWhiteQuote.getInstance(),
            Print_Service.quotes.A3Quote.getInstance(),
            2L,
            5L);
    Document doc2 =
        new Document(
            Print_Service.quotes.BlackWhiteQuote.getInstance(),
            Print_Service.quotes.A3Quote.getInstance(),
            2L,
            5L);
    Printer printer1 = new Printer(SeqUtil.seq(50L, 50L), SeqUtil.seq(50L, 50L, 50L), 'a');
    Printer printer2 = new Printer(SeqUtil.seq(50L, 50L), SeqUtil.seq(50L, 50L, 50L), 'b');
    PrintingService ps = new PrintingService(SetUtil.set(printer1, printer2));
    client.assignDocument(doc1);
    client.assignDocument(doc2);
    assertEqual(((Object) doc1.status), Print_Service.quotes.NotPrintedQuote.getInstance());
    client.requestPrint(doc1);
    assertEqual(((Object) doc1.status), Print_Service.quotes.QueuedQuote.getInstance());
    assertEqual(((Object) doc2.status), Print_Service.quotes.NotPrintedQuote.getInstance());
    client.requestPrint(doc2, 'b');
    assertEqual(((Object) doc2.status), Print_Service.quotes.QueuedQuote.getInstance());
    assertEqual(client.moneyBalance, 10L - doc1.price.doubleValue() - doc2.price.doubleValue());
    assertTrue(SetUtil.subset(SetUtil.set(doc1), SeqUtil.elems(printer1.printingQueue)));
    assertTrue(SetUtil.subset(SetUtil.set(doc2), SeqUtil.elems(printer2.printingQueue)));
  }

  private void testPrinterAddToQueue() {

    Printer printer = new Printer(SeqUtil.seq(50L, 50L), SeqUtil.seq(50L, 50L, 50L), 'a');
    Document doc1 =
        new Document(
            Print_Service.quotes.BlackWhiteQuote.getInstance(),
            Print_Service.quotes.A3Quote.getInstance(),
            2L,
            5L);
    Document doc2 =
        new Document(
            Print_Service.quotes.ColorQuote.getInstance(),
            Print_Service.quotes.A5Quote.getInstance(),
            3L,
            10L);
    Document doc3 =
        new Document(
            Print_Service.quotes.BlackWhiteQuote.getInstance(),
            Print_Service.quotes.A4Quote.getInstance(),
            2L,
            5L);
    printer.addToQueue(doc1);
    assertTrue(SetUtil.inSet(doc1, SeqUtil.elems(printer.printingQueue)));
    printer.addToQueue(SeqUtil.seq(doc2, doc3));
    assertTrue(SetUtil.subset(SetUtil.set(doc1, doc2, doc3), SeqUtil.elems(printer.printingQueue)));
  }

  private void testReduceQueueInHalf() {

    Printer printer = new Printer(SeqUtil.seq(50L, 50L), SeqUtil.seq(50L, 50L, 50L), 'a');
    Document doc1 =
        new Document(
            Print_Service.quotes.BlackWhiteQuote.getInstance(),
            Print_Service.quotes.A3Quote.getInstance(),
            2L,
            5L);
    Document doc2 =
        new Document(
            Print_Service.quotes.ColorQuote.getInstance(),
            Print_Service.quotes.A5Quote.getInstance(),
            3L,
            10L);
    Document doc3 =
        new Document(
            Print_Service.quotes.BlackWhiteQuote.getInstance(),
            Print_Service.quotes.A4Quote.getInstance(),
            2L,
            5L);
    Document doc4 =
        new Document(
            Print_Service.quotes.ColorQuote.getInstance(),
            Print_Service.quotes.A4Quote.getInstance(),
            2L,
            10L);
    printer.addToQueue(SeqUtil.seq(doc1, doc2, doc3, doc4));
    assertEqual(printer.printingQueue.size(), 4L);
    printer.reduceQueueInHalf();
    assertEqual(printer.printingQueue.size(), 2L);
  }

  private void testRepairDamage() {

    Printer printer = new Printer(SeqUtil.seq(50L, 50L), SeqUtil.seq(50L, 50L, 50L), 'a');
    printer.damaged = true;
    printer.repairDamage();
    assertTrue(!(printer.damaged));
  }

  private void testRefillInkCartridge() {

    Printer printer = new Printer(SeqUtil.seq(0L, 0L), SeqUtil.seq(50L, 50L, 50L), 'a');
    printer.refillInkCartridge(50L, 1L);
    printer.refillInkCartridge(25L, 2L);
    assertEqual(((Number) Utils.get(printer.inkQuantities, 1L)), 50L);
    assertEqual(((Number) Utils.get(printer.inkQuantities, 2L)), 25L);
  }

  private void testRefillPaperSize() {

    Printer printer = new Printer(SeqUtil.seq(50L, 50L), SeqUtil.seq(0L, 0L, 0L), 'a');
    printer.refillPaperSize(50L, 1L);
    printer.refillPaperSize(20L, 2L);
    printer.refillPaperSize(30L, 3L);
    assertEqual(((Number) Utils.get(printer.paperQuantities, 1L)), 50L);
    assertEqual(((Number) Utils.get(printer.paperQuantities, 2L)), 20L);
    assertEqual(((Number) Utils.get(printer.paperQuantities, 3L)), 30L);
  }

  private void testPrintAll() {

    Client client = new Client("Joao", 100L);
    Printer printer = new Printer(SeqUtil.seq(50L, 50L), SeqUtil.seq(50L, 50L, 50L), 'a');
    PrintingService service = new PrintingService(SetUtil.set(printer));
    Document doc1 =
        new Document(
            Print_Service.quotes.BlackWhiteQuote.getInstance(),
            Print_Service.quotes.A3Quote.getInstance(),
            2L,
            5L);
    Document doc2 =
        new Document(
            Print_Service.quotes.ColorQuote.getInstance(),
            Print_Service.quotes.A4Quote.getInstance(),
            5L,
            7L);
    Document doc3 =
        new Document(
            Print_Service.quotes.ColorQuote.getInstance(),
            Print_Service.quotes.A3Quote.getInstance(),
            2L,
            10L);
    Document doc4 =
        new Document(
            Print_Service.quotes.BlackWhiteQuote.getInstance(),
            Print_Service.quotes.A5Quote.getInstance(),
            6L,
            5L);
    Document doc5 =
        new Document(
            Print_Service.quotes.ColorQuote.getInstance(),
            Print_Service.quotes.A4Quote.getInstance(),
            3L,
            3L);
    Boolean success = false;
    client.assignDocument(doc1);
    client.assignDocument(doc2);
    client.assignDocument(doc3);
    client.assignDocument(doc4);
    client.assignDocument(doc5);
    client.requestPrint(doc1);
    client.requestPrint(doc2);
    client.requestPrint(doc3);
    client.requestPrint(doc4);
    client.requestPrint(doc5);
    assertEqual(((Object) doc1.status), Print_Service.quotes.QueuedQuote.getInstance());
    assertEqual(((Object) doc2.status), Print_Service.quotes.QueuedQuote.getInstance());
    assertEqual(((Object) doc3.status), Print_Service.quotes.QueuedQuote.getInstance());
    assertEqual(((Object) doc4.status), Print_Service.quotes.QueuedQuote.getInstance());
    assertEqual(((Object) doc5.status), Print_Service.quotes.QueuedQuote.getInstance());
    success = printer.printAll();
    if (success) {
      assertEqual(((Object) doc1.status), Print_Service.quotes.PrintedQuote.getInstance());
      assertEqual(((Object) doc2.status), Print_Service.quotes.PrintedQuote.getInstance());
      assertEqual(((Object) doc3.status), Print_Service.quotes.PrintedQuote.getInstance());
      assertEqual(((Object) doc4.status), Print_Service.quotes.PrintedQuote.getInstance());
      assertEqual(((Object) doc5.status), Print_Service.quotes.PrintedQuote.getInstance());
      assertEqual(printer.printingQueue.size(), 0L);
    }
  }

  private void testRemoveReport() {

    Printer printer = new Printer(SeqUtil.seq(50L, 50L), SeqUtil.seq(50L, 50L, 50L), 'a');
    Object damaged = Print_Service.quotes.DamagedQuote.getInstance();
    Report report = new Report(((Object) damaged), printer);
    printer.openReports = SeqUtil.seq(report);
    assertTrue(SetUtil.inSet(report, SeqUtil.elems(printer.openReports)));
    printer.removeReport(damaged);
    assertTrue(!(SetUtil.inSet(report, SeqUtil.elems(printer.openReports))));
  }

  private void testReports() {

    Printer printer = new Printer(SeqUtil.seq(0L, 0L), SeqUtil.seq(0L, 0L, 0L), 'a');
    PrintingService service = new PrintingService(SetUtil.set(printer));
    Client client = new Client("Joao", 50L);
    Document document =
        new Document(
            Print_Service.quotes.BlackWhiteQuote.getInstance(),
            Print_Service.quotes.A3Quote.getInstance(),
            2L,
            5L);
    Boolean success = false;
    client.assignDocument(document);
    client.requestPrint(document);
    assertEqual(((Object) document.status), Print_Service.quotes.QueuedQuote.getInstance());
    success = printer.printNext();
    assertTrue(
        Utils.equals(
            ((Report) Utils.get(printer.openReports, 1L)).malfunction,
            Print_Service.quotes.BlackInkEmptyQuote.getInstance()));
    assertTrue(
        Utils.equals(
            ((Report) Utils.get(printer.openReports, 2L)).malfunction,
            Print_Service.quotes.A3EmptyQuote.getInstance()));
  }

  private void testRedistributeDocumentQueue() {

    Printer printer1 = new Printer(SeqUtil.seq(50L, 50L), SeqUtil.seq(50L, 50L, 50L), 'a');
    Printer printer2 = new Printer(SeqUtil.seq(50L, 50L), SeqUtil.seq(50L, 50L, 50L), 'a');
    PrintingService service = new PrintingService(SetUtil.set(printer1, printer2));
    Boolean flag = false;
    Client client = new Client("Joao", 500L);
    Document document0 =
        new Document(
            Print_Service.quotes.ColorQuote.getInstance(),
            Print_Service.quotes.A3Quote.getInstance(),
            1L,
            1L);
    Document document1 =
        new Document(
            Print_Service.quotes.BlackWhiteQuote.getInstance(),
            Print_Service.quotes.A4Quote.getInstance(),
            1L,
            1L);
    Document document2 =
        new Document(
            Print_Service.quotes.ColorQuote.getInstance(),
            Print_Service.quotes.A5Quote.getInstance(),
            1L,
            1L);
    Document document3 =
        new Document(
            Print_Service.quotes.BlackWhiteQuote.getInstance(),
            Print_Service.quotes.A3Quote.getInstance(),
            1L,
            1L);
    client.assignDocument(document0);
    client.assignDocument(document1);
    client.assignDocument(document2);
    client.assignDocument(document3);
    client.requestPrint(document0, 'a');
    client.requestPrint(document1, 'a');
    client.requestPrint(document2, 'a');
    client.requestPrint(document3, 'a');
    flag = printer1.printAll();
    assertEqual(printer1.printingQueue.size(), 0L);
    assertEqual(printer2.printingQueue.size(), 1L);
  }

  private void testFixPrinterProblem() {

    Printer printer = new Printer(SeqUtil.seq(0L, 0L), SeqUtil.seq(0L, 0L, 0L), 'a');
    Object damaged = Print_Service.quotes.DamagedQuote.getInstance();
    Object blackWhiteInk = Print_Service.quotes.BlackInkEmptyQuote.getInstance();
    Object colorInk = Print_Service.quotes.ColorInkEmptyQuote.getInstance();
    Object a3empty = Print_Service.quotes.A3EmptyQuote.getInstance();
    Object a4empty = Print_Service.quotes.A4EmptyQuote.getInstance();
    Object a5empty = Print_Service.quotes.A5EmptyQuote.getInstance();
    Report report1 = new Report(((Object) damaged), printer);
    Report report2 = new Report(((Object) blackWhiteInk), printer);
    Report report3 = new Report(((Object) colorInk), printer);
    Report report4 = new Report(((Object) a3empty), printer);
    Report report5 = new Report(((Object) a4empty), printer);
    Report report6 = new Report(((Object) a5empty), printer);
    Technician technician = new Technician("Joao");
    printer.damaged = true;
    assertTrue(printer.damaged);
    printer.openReports = SeqUtil.seq(report1, report2, report3, report4, report5, report6);
    assertTrue(
        SetUtil.subset(
            SetUtil.set(report1, report2, report3, report4, report5, report6),
            SeqUtil.elems(printer.openReports)));
    technician.fixPrinterProblem(report1);
    assertTrue(report1.closed);
    assertTrue(!(printer.damaged));
    technician.fixPrinterProblem(report2);
    assertTrue(report2.closed);
    assertEqual(((Number) Utils.get(printer.inkQuantities, 1L)), Utils.INK_REFILL_QUANTITY);
    technician.fixPrinterProblem(report3);
    assertTrue(report3.closed);
    assertEqual(((Number) Utils.get(printer.inkQuantities, 2L)), Utils.INK_REFILL_QUANTITY);
    technician.fixPrinterProblem(report4);
    assertTrue(report4.closed);
    assertEqual(((Number) Utils.get(printer.paperQuantities, 1L)), Utils.PAPER_REFILL_QUANTITY);
    technician.fixPrinterProblem(report5);
    assertTrue(report5.closed);
    assertEqual(((Number) Utils.get(printer.paperQuantities, 2L)), Utils.PAPER_REFILL_QUANTITY);
    technician.fixPrinterProblem(report6);
    assertTrue(report6.closed);
    assertEqual(((Number) Utils.get(printer.paperQuantities, 3L)), Utils.PAPER_REFILL_QUANTITY);
  }

  private void testPrintWhileDamaged() {

    Boolean flag = true;
    Boolean whileCond_2 = true;
    while (whileCond_2) {
      whileCond_2 = flag;
      if (!(whileCond_2)) {
        break;
      }

      {
        Client client = new Client("Joao", 10L);
        Document doc1 =
            new Document(
                Print_Service.quotes.BlackWhiteQuote.getInstance(),
                Print_Service.quotes.A3Quote.getInstance(),
                2L,
                5L);
        Document doc2 =
            new Document(
                Print_Service.quotes.BlackWhiteQuote.getInstance(),
                Print_Service.quotes.A3Quote.getInstance(),
                2L,
                5L);
        Printer printer = new Printer(SeqUtil.seq(50L, 50L), SeqUtil.seq(50L, 50L, 50L), 'a');
        PrintingService ps = new PrintingService(SetUtil.set(printer));
        client.assignDocument(doc1);
        client.assignDocument(doc2);
        client.requestPrint(doc1);
        client.requestPrint(doc2);
        flag = printer.printNext();
        if (printer.damaged) {
          flag = printer.printNext();
          assertEqual(printer.damaged, true);
        }
      }
    }
  }

  public static void main() {

    new PrintingServiceTest().testClientMoneyDeposit();
    new PrintingServiceTest().testClientAssignDocuments();
    new PrintingServiceTest().testClientRequestPrints();
    new PrintingServiceTest().testPrinterAddToQueue();
    new PrintingServiceTest().testReduceQueueInHalf();
    new PrintingServiceTest().testRepairDamage();
    new PrintingServiceTest().testRefillInkCartridge();
    new PrintingServiceTest().testRefillPaperSize();
    new PrintingServiceTest().testPrintAll();
    new PrintingServiceTest().testRemoveReport();
    new PrintingServiceTest().testReports();
    new PrintingServiceTest().testRedistributeDocumentQueue();
    new PrintingServiceTest().testPrintWhileDamaged();
    new PrintingServiceTest().testFixPrinterProblem();
  }

  public PrintingServiceTest() {}

  public String toString() {

    return "PrintingServiceTest{}";
  }
}
