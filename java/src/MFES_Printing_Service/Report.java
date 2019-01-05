package MFES_Printing_Service;

import java.util.*;
import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class Report {
  public static Number id_count = 1L;
  public Number id;
  public Object malfunction;
  public Printer printer;
  public Technician solverTechnician = null;
  public Boolean closed = false;

  public void cg_init_Report_1(final Object malfunction_, final Printer printer_) {

    id = Report.id_count;
    id_count = Report.id_count.longValue() + 1L;
    malfunction = malfunction_;
    printer = printer_;
    return;
  }

  public Report(final Object malfunction_, final Printer printer_) {

    cg_init_Report_1(malfunction_, printer_);
  }

  public void close(final Technician technician) {

    printer.removeReport(malfunction);
    closed = true;
    solverTechnician = technician;
  }

  public Report() {}

  public String toString() {

    return "Report{"
        + "id_count := "
        + Utils.toString(id_count)
        + ", id := "
        + Utils.toString(id)
        + ", malfunction := "
        + Utils.toString(malfunction)
        + ", printer := "
        + Utils.toString(printer)
        + ", solverTechnician := "
        + Utils.toString(solverTechnician)
        + ", closed := "
        + Utils.toString(closed)
        + "}";
  }
}
