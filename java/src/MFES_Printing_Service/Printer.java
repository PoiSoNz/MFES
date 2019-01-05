package MFES_Printing_Service;

import java.util.*;
import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class Printer {
  public static Number id_count = 1L;
  public Number id;
  public VDMSeq inkQuantities = SeqUtil.seq(0L, 0L);
  public VDMSeq paperQuantities = SeqUtil.seq(0L, 0L, 0L);
  public VDMSeq openReports = SeqUtil.seq();
  public Boolean damaged = false;
  public Character location;
  public VDMSeq printingQueue = SeqUtil.seq();

  public void cg_init_Printer_1(
      final VDMSeq inkQuantities_, final VDMSeq paperQuantities_, final Character location_) {

    id = Printer.id_count;
    id_count = Printer.id_count.longValue() + 1L;
    inkQuantities = Utils.copy(inkQuantities_);
    paperQuantities = Utils.copy(paperQuantities_);
    location = location_;
    return;
  }

  public Printer(
      final VDMSeq inkQuantities_, final VDMSeq paperQuantities_, final Character location_) {

    cg_init_Printer_1(Utils.copy(inkQuantities_), Utils.copy(paperQuantities_), location_);
  }

  public void addToQueue(final Document document) {

    printingQueue = SeqUtil.conc(Utils.copy(printingQueue), SeqUtil.seq(document));
  }

  public void addToQueue(final VDMSeq documents) {

    printingQueue = SeqUtil.conc(Utils.copy(printingQueue), Utils.copy(documents));
  }

  public void reduceQueueInHalf() {

    Number queueSize = printingQueue.size();
    printingQueue =
        SeqUtil.subSeq(Utils.copy(printingQueue), 1L, Utils.div(queueSize.longValue(), 2L));
  }

  public void repairDamage() {

    damaged = false;
  }

  public void refillInkCartridge(final Number inkQuantity, final Number inkCartridgeIndex) {

    Utils.mapSeqUpdate(
        inkQuantities,
        inkCartridgeIndex,
        ((Number) Utils.get(inkQuantities, inkCartridgeIndex)).longValue()
            + inkQuantity.longValue());
  }

  public void refillPaperSize(final Number paperQuantity, final Number paperSizeIndex) {

    Utils.mapSeqUpdate(
        paperQuantities,
        paperSizeIndex,
        ((Number) Utils.get(paperQuantities, paperSizeIndex)).longValue()
            + paperQuantity.longValue());
  }

  private Number generateRandomDamage() {

    Number randNum = MATH.rand(99L).longValue() + 1L;
    if (randNum.longValue() <= cg_Utils.DAMAGE_PROBABILITY.longValue()) {
      damaged = true;
      this.createReport(MFES_Printing_Service.quotes.DamagedQuote.getInstance());
    }

    return randNum;
  }

  private Boolean consumePrintingResources(final Document document) {

    Number inkCartridgeIndex = getInkCartridgeIndex(((Object) document.color));
    Number paperSizeIndex = getPaperSizeIndex(((Object) document.size));
    Boolean andResult_38 = false;

    if (((Number) Utils.get(inkQuantities, inkCartridgeIndex)).longValue()
        >= document.inkQuantity.longValue()) {
      if (((Number) Utils.get(paperQuantities, paperSizeIndex)).longValue()
          >= document.paperQuantity.longValue()) {
        andResult_38 = true;
      }
    }

    if (andResult_38) {
      Utils.mapSeqUpdate(
          inkQuantities,
          inkCartridgeIndex,
          ((Number) Utils.get(inkQuantities, inkCartridgeIndex)).longValue()
              - document.inkQuantity.longValue());
      Utils.mapSeqUpdate(
          paperQuantities,
          paperSizeIndex,
          ((Number) Utils.get(paperQuantities, paperSizeIndex)).longValue()
              - document.paperQuantity.longValue());
      return true;

    } else {
      Object inkCartridgeReportType = getInkCartridgeReportType(((Object) document.color));
      Object paperSizeReportType = getPaperSizeReportType(((Object) document.size));
      if (((Number) Utils.get(inkQuantities, inkCartridgeIndex)).longValue()
          < document.inkQuantity.longValue()) {
        createReport(((Object) inkCartridgeReportType));
      }

      if (((Number) Utils.get(paperQuantities, paperSizeIndex)).longValue()
          < document.paperQuantity.longValue()) {
        createReport(((Object) paperSizeReportType));
      }

      return false;
    }
  }

  public Boolean printNext() {

    Boolean canBePrinted = false;
    if (damaged) {
      IO.print("Cannot print, printer is damaged");
      return false;

    } else if (Utils.equals(printingQueue.size(), 0L)) {
      IO.print("There are no documents to print");
      return false;
    }

    canBePrinted = this.consumePrintingResources(((Document) printingQueue.get(0)));
    if (canBePrinted) {
      Number generatedNumber = this.generateRandomDamage();
      Document printingDocument = ((Document) printingQueue.get(0));
      printingDocument.printed();
      printingQueue = SeqUtil.tail(Utils.copy(printingQueue));
      if (Utils.equals(printingQueue.size(), 0L)) {
        PrintingService.redistributeDocumentQueue(this);
      }
    }

    return canBePrinted;
  }

  public Boolean printAll() {

    Boolean continue_ = true;
    Boolean whileCond_1 = true;
    while (whileCond_1) {
      whileCond_1 = continue_;
      if (!(whileCond_1)) {
        break;
      }

      {
        continue_ = this.printNext();
        if (Utils.equals(continue_, false)) {
          if (Utils.equals(printingQueue.size(), 0L)) {
            IO.print("Printer finished!");
            return true;

          } else {
            IO.print("An error occurred. Check the reports for details!");
            return false;
          }
        }
      }
    }

    return true;
  }

  private void createReport(final Object malfunction) {

    if (!(existsReport(((Object) malfunction)))) {
      Report newReport = new Report(((Object) malfunction), this);
      openReports = SeqUtil.conc(Utils.copy(openReports), SeqUtil.seq(newReport));
    }
  }

  private Boolean existsReport(final Object reportType) {

    Boolean existsExpResult_4 = false;
    VDMSet set_13 = SeqUtil.elems(Utils.copy(openReports));
    for (Iterator iterator_13 = set_13.iterator();
        iterator_13.hasNext() && !(existsExpResult_4);
        ) {
      Report report = ((Report) iterator_13.next());
      existsExpResult_4 = Utils.equals(report.malfunction, reportType);
    }
    return existsExpResult_4;
  }

  public void removeReport(final Object malfunction) {

    for (Iterator iterator_25 = SeqUtil.inds(openReports).iterator(); iterator_25.hasNext(); ) {
      Number i = (Number) iterator_25.next();
      if (Utils.equals(((Report) Utils.get(openReports, i)).malfunction, malfunction)) {
        openReports =
            SeqUtil.conc(
                SeqUtil.subSeq(Utils.copy(openReports), 1L, i.longValue() - 1L),
                SeqUtil.subSeq(Utils.copy(openReports), i.longValue() + 1L, openReports.size()));
        return;
      }
    }
  }

  public Printer() {}

  private static Number getInkCartridgeIndex(final Object color) {

    Number casesExpResult_3 = null;

    Object quotePattern_6 = color;
    Boolean success_3 =
        Utils.equals(quotePattern_6, MFES_Printing_Service.quotes.BlackWhiteQuote.getInstance());

    if (!(success_3)) {
      Object quotePattern_7 = color;
      success_3 =
          Utils.equals(quotePattern_7, MFES_Printing_Service.quotes.ColorQuote.getInstance());

      if (success_3) {
        casesExpResult_3 = cg_Utils.COLOR_INK_CARTDG_IDX;
      }

    } else {
      casesExpResult_3 = cg_Utils.BLACK_INK_CARTDG_IDX;
    }

    return casesExpResult_3;
  }

  private static Number getPaperSizeIndex(final Object paperSize) {

    Number casesExpResult_4 = null;

    Object quotePattern_8 = paperSize;
    Boolean success_4 =
        Utils.equals(quotePattern_8, MFES_Printing_Service.quotes.A3Quote.getInstance());

    if (!(success_4)) {
      Object quotePattern_9 = paperSize;
      success_4 = Utils.equals(quotePattern_9, MFES_Printing_Service.quotes.A4Quote.getInstance());

      if (!(success_4)) {
        Object quotePattern_10 = paperSize;
        success_4 =
            Utils.equals(quotePattern_10, MFES_Printing_Service.quotes.A5Quote.getInstance());

        if (success_4) {
          casesExpResult_4 = cg_Utils.A5_PAPER_IDX;
        }

      } else {
        casesExpResult_4 = cg_Utils.A4_PAPER_IDX;
      }

    } else {
      casesExpResult_4 = cg_Utils.A3_PAPER_IDX;
    }

    return casesExpResult_4;
  }

  private static Object getInkCartridgeReportType(final Object color) {

    Object casesExpResult_5 = null;

    Object quotePattern_11 = color;
    Boolean success_5 =
        Utils.equals(quotePattern_11, MFES_Printing_Service.quotes.BlackWhiteQuote.getInstance());

    if (!(success_5)) {
      Object quotePattern_12 = color;
      success_5 =
          Utils.equals(quotePattern_12, MFES_Printing_Service.quotes.ColorQuote.getInstance());

      if (success_5) {
        casesExpResult_5 = MFES_Printing_Service.quotes.ColorInkEmptyQuote.getInstance();
      }

    } else {
      casesExpResult_5 = MFES_Printing_Service.quotes.BlackInkEmptyQuote.getInstance();
    }

    return casesExpResult_5;
  }

  private static Object getPaperSizeReportType(final Object paperSize) {

    Object casesExpResult_6 = null;

    Object quotePattern_13 = paperSize;
    Boolean success_6 =
        Utils.equals(quotePattern_13, MFES_Printing_Service.quotes.A3Quote.getInstance());

    if (!(success_6)) {
      Object quotePattern_14 = paperSize;
      success_6 = Utils.equals(quotePattern_14, MFES_Printing_Service.quotes.A4Quote.getInstance());

      if (!(success_6)) {
        Object quotePattern_15 = paperSize;
        success_6 =
            Utils.equals(quotePattern_15, MFES_Printing_Service.quotes.A5Quote.getInstance());

        if (success_6) {
          casesExpResult_6 = MFES_Printing_Service.quotes.A5EmptyQuote.getInstance();
        }

      } else {
        casesExpResult_6 = MFES_Printing_Service.quotes.A4EmptyQuote.getInstance();
      }

    } else {
      casesExpResult_6 = MFES_Printing_Service.quotes.A3EmptyQuote.getInstance();
    }

    return casesExpResult_6;
  }

  public String toString() {

    return "Printer{"
        + "id_count := "
        + Utils.toString(id_count)
        + ", id := "
        + Utils.toString(id)
        + ", inkQuantities := "
        + Utils.toString(inkQuantities)
        + ", paperQuantities := "
        + Utils.toString(paperQuantities)
        + ", openReports := "
        + Utils.toString(openReports)
        + ", damaged := "
        + Utils.toString(damaged)
        + ", location := "
        + Utils.toString(location)
        + ", printingQueue := "
        + Utils.toString(printingQueue)
        + "}";
  }
}
