package Print_Service;

import java.util.*;
import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class Technician {
  private static Number id_count = 1L;
  private Number id;
  private String name;
  private VDMSeq fixedReports = SeqUtil.seq();

  public void cg_init_Technician_1(final String name_) {

    id = Technician.id_count;
    id_count = Technician.id_count.longValue() + 1L;
    name = name_;
    return;
  }

  public Technician(final String name_) {

    cg_init_Technician_1(name_);
  }

  public void fixPrinterProblem(final Report report) {

    Object casesExp_7 = report.malfunction;
    Object quotePattern_16 = casesExp_7;
    Boolean success_7 =
        Utils.equals(quotePattern_16, Print_Service.quotes.DamagedQuote.getInstance());

    if (!(success_7)) {
      Object quotePattern_17 = casesExp_7;
      success_7 =
          Utils.equals(quotePattern_17, Print_Service.quotes.BlackInkEmptyQuote.getInstance());

      if (!(success_7)) {
        Object quotePattern_18 = casesExp_7;
        success_7 =
            Utils.equals(quotePattern_18, Print_Service.quotes.ColorInkEmptyQuote.getInstance());

        if (!(success_7)) {
          Object quotePattern_19 = casesExp_7;
          success_7 =
              Utils.equals(quotePattern_19, Print_Service.quotes.A3EmptyQuote.getInstance());

          if (!(success_7)) {
            Object quotePattern_20 = casesExp_7;
            success_7 =
                Utils.equals(quotePattern_20, Print_Service.quotes.A4EmptyQuote.getInstance());

            if (!(success_7)) {
              Object quotePattern_21 = casesExp_7;
              success_7 =
                  Utils.equals(quotePattern_21, Print_Service.quotes.A5EmptyQuote.getInstance());

              if (success_7) {
                this.fixLackOfPaper(report.printer, Utils.A5_PAPER_IDX);
              }

            } else {
              this.fixLackOfPaper(report.printer, Utils.A4_PAPER_IDX);
            }

          } else {
            this.fixLackOfPaper(report.printer, Utils.A3_PAPER_IDX);
          }

        } else {
          this.fixLackOfInk(report.printer, Utils.COLOR_INK_CARTDG_IDX);
        }

      } else {
        this.fixLackOfInk(report.printer, Utils.BLACK_INK_CARTDG_IDX);
      }

    } else {
      this.fixDamage(report.printer);
    }

    report.close(this);
    fixedReports = SeqUtil.conc(Utils.copy(fixedReports), SeqUtil.seq(report));
  }

  private void fixDamage(final Printer printer) {

    printer.repairDamage();
  }

  private void fixLackOfInk(final Printer printer, final Number inkCartridgeIndex) {

    printer.refillInkCartridge(Utils.INK_REFILL_QUANTITY, inkCartridgeIndex);
  }

  private void fixLackOfPaper(final Printer printer, final Number paperTypeIndex) {

    printer.refillPaperSize(Utils.PAPER_REFILL_QUANTITY, paperTypeIndex);
  }

  public Technician() {}

  public String toString() {

    return "Technician{"
        + "id_count := "
        + Utils.toString(id_count)
        + ", id := "
        + Utils.toString(id)
        + ", name := "
        + Utils.toString(name)
        + ", fixedReports := "
        + Utils.toString(fixedReports)
        + "}";
  }
}
