package MFES_Printing_Service;

import java.util.*;
import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class PrintingService {
  public static VDMMap servicePrinters = MapUtil.map();

  public void cg_init_PrintingService_1(final VDMSet printers) {

    servicePrinters = MapUtil.map();
    for (Iterator iterator_26 = printers.iterator(); iterator_26.hasNext(); ) {
      Printer printer = (Printer) iterator_26.next();
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
    for (Iterator iterator_27 = printersFromLocation.iterator(); iterator_27.hasNext(); ) {
      Printer currPrinter = (Printer) iterator_27.next();
      Boolean forAllExpResult_10 = true;
      VDMSet set_19 = Utils.copy(printersFromLocation);
      for (Iterator iterator_19 = set_19.iterator();
          iterator_19.hasNext() && forAllExpResult_10;
          ) {
        Printer printer = ((Printer) iterator_19.next());
        forAllExpResult_10 = currPrinter.printingQueue.size() <= printer.printingQueue.size();
      }
      if (forAllExpResult_10) {
        currPrinter.addToQueue(document);
        return;
      }
    }
  }

  public static void addToQueue(final Document document) {

    VDMSet allPrinters = MapUtil.dom(Utils.copy(PrintingService.servicePrinters));
    for (Iterator iterator_28 = allPrinters.iterator(); iterator_28.hasNext(); ) {
      Printer currPrinter = (Printer) iterator_28.next();
      Boolean forAllExpResult_12 = true;
      VDMSet set_22 = Utils.copy(allPrinters);
      for (Iterator iterator_22 = set_22.iterator();
          iterator_22.hasNext() && forAllExpResult_12;
          ) {
        Printer printer = ((Printer) iterator_22.next());
        forAllExpResult_12 = currPrinter.printingQueue.size() <= printer.printingQueue.size();
      }
      if (forAllExpResult_12) {
        currPrinter.addToQueue(document);
        return;
      }
    }
  }

  public static void redistributeDocumentQueue(final Printer printer) {

    VDMSet printersFromLocation = getPrintersFromLocation(printer.location);
    for (Iterator iterator_29 = printersFromLocation.iterator(); iterator_29.hasNext(); ) {
      Printer currPrinter = (Printer) iterator_29.next();
      Boolean andResult_53 = false;

      if (currPrinter.printingQueue.size() > 1L) {
        Boolean andResult_54 = false;

        if (!(Utils.equals(currPrinter, printer))) {
          Boolean forAllExpResult_13 = true;
          VDMSet set_23 = Utils.copy(printersFromLocation);
          for (Iterator iterator_23 = set_23.iterator();
              iterator_23.hasNext() && forAllExpResult_13;
              ) {
            Printer printerI = ((Printer) iterator_23.next());
            forAllExpResult_13 = currPrinter.printingQueue.size() >= printerI.printingQueue.size();
          }
          if (forAllExpResult_13) {
            andResult_54 = true;
          }
        }

        if (andResult_54) {
          andResult_53 = true;
        }
      }

      if (andResult_53) {
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

  public static VDMSet getPrintersFromLocation(final Character location) {

    return MapUtil.dom(
        MapUtil.rngResTo(Utils.copy(PrintingService.servicePrinters), SetUtil.set(location)));
  }

  public PrintingService() {}

  public String toString() {

    return "PrintingService{" + "servicePrinters := " + Utils.toString(servicePrinters) + "}";
  }
}
