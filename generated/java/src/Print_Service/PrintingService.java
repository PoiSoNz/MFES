package Print_Service;

import java.util.*;
import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class PrintingService {
  public static VDMMap servicePrinters = MapUtil.map();

  public void cg_init_PrintingService_1(final VDMSet printers) {

    servicePrinters = MapUtil.map();
    for (Iterator iterator_25 = printers.iterator(); iterator_25.hasNext(); ) {
      Printer printer = (Printer) iterator_25.next();
      servicePrinters =
          MapUtil.munion(
              Utils.copy(PrintingService.servicePrinters),
              MapUtil.map(new Maplet(printer, printer.location)));
    }
    return;
  }

  public PrintingService(final VDMSet printers) {

    cg_init_PrintingService_1(Utils.copy(printers));
  }

  public static void addToQueue(final Document document, final Character location) {

    VDMSet printersFromLocation = getPrintersFromLocation(location);
    for (Iterator iterator_26 = printersFromLocation.iterator(); iterator_26.hasNext(); ) {
      Printer currPrinter = (Printer) iterator_26.next();
      Boolean forAllExpResult_9 = true;
      VDMSet set_18 = Utils.copy(printersFromLocation);
      for (Iterator iterator_18 = set_18.iterator(); iterator_18.hasNext() && forAllExpResult_9; ) {
        Printer printer = ((Printer) iterator_18.next());
        forAllExpResult_9 = currPrinter.printingQueue.size() <= printer.printingQueue.size();
      }
      if (forAllExpResult_9) {
        currPrinter.addToQueue(document);
        return;
      }
    }
  }

  public static void addToQueue(final Document document) {

    VDMSet allPrinters = MapUtil.dom(Utils.copy(PrintingService.servicePrinters));
    for (Iterator iterator_27 = allPrinters.iterator(); iterator_27.hasNext(); ) {
      Printer currPrinter = (Printer) iterator_27.next();
      Boolean forAllExpResult_11 = true;
      VDMSet set_21 = Utils.copy(allPrinters);
      for (Iterator iterator_21 = set_21.iterator();
          iterator_21.hasNext() && forAllExpResult_11;
          ) {
        Printer printer = ((Printer) iterator_21.next());
        forAllExpResult_11 = currPrinter.printingQueue.size() <= printer.printingQueue.size();
      }
      if (forAllExpResult_11) {
        currPrinter.addToQueue(document);
        return;
      }
    }
  }

  public static void redistributeDocumentQueue(final Printer printer) {

    VDMSet printersFromLocation = getPrintersFromLocation(printer.location);
    for (Iterator iterator_28 = printersFromLocation.iterator(); iterator_28.hasNext(); ) {
      Printer currPrinter = (Printer) iterator_28.next();
      Boolean andResult_50 = false;

      if (currPrinter.printingQueue.size() > 1L) {
        Boolean andResult_51 = false;

        if (!(Utils.equals(currPrinter, printer))) {
          Boolean forAllExpResult_12 = true;
          VDMSet set_22 = Utils.copy(printersFromLocation);
          for (Iterator iterator_22 = set_22.iterator();
              iterator_22.hasNext() && forAllExpResult_12;
              ) {
            Printer printerI = ((Printer) iterator_22.next());
            forAllExpResult_12 = currPrinter.printingQueue.size() >= printerI.printingQueue.size();
          }
          if (forAllExpResult_12) {
            andResult_51 = true;
          }
        }

        if (andResult_51) {
          andResult_50 = true;
        }
      }

      if (andResult_50) {
        Number queueSize = currPrinter.printingQueue.size();
        VDMSeq realocatedDocuments =
            SeqUtil.subSeq(
                currPrinter.printingQueue, Utils.div(queueSize.longValue(), 2L) + 1L, queueSize);
        currPrinter.reduceQueueInHalf();
        printer.addToQueue(Utils.copy(realocatedDocuments));
        return;
      }
    }
  }

  private static VDMSet getPrintersFromLocation(final Character location) {

    return MapUtil.dom(
        MapUtil.rngResTo(Utils.copy(PrintingService.servicePrinters), SetUtil.set(location)));
  }

  public PrintingService() {}

  public String toString() {

    return "PrintingService{" + "servicePrinters := " + Utils.toString(servicePrinters) + "}";
  }
}
