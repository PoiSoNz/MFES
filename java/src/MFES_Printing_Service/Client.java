package MFES_Printing_Service;

import java.util.*;
import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class Client {
  public static Number id_count = 1L;
  public Number id;
  public String name;
  public Number moneyBalance = 0L;
  public VDMSeq documents = SeqUtil.seq();

  public void cg_init_Client_2(final String name_) {

    id = Client.id_count;
    id_count = Client.id_count.longValue() + 1L;
    name = name_;
    moneyBalance = 0L;
    return;
  }

  public void cg_init_Client_1(final String name_, final Number startingMoneyBalance) {

    id = Client.id_count;
    id_count = Client.id_count.longValue() + 1L;
    name = name_;
    moneyBalance = startingMoneyBalance;
    return;
  }

  public Client(final String name_, final Number startingMoneyBalance) {

    cg_init_Client_1(name_, startingMoneyBalance);
  }

  public Client(final String name_) {

    cg_init_Client_2(name_);
  }

  public void assignDocument(final Document document) {

    documents = SeqUtil.conc(Utils.copy(documents), SeqUtil.seq(document));
    document.setOwner(this);
  }

  public void moneyDeposit(final Number amount) {

    moneyBalance = moneyBalance.doubleValue() + amount.doubleValue();
  }

  public void requestPrint(final Document document, final Character location) {

    PrintingService.addToQueue(document, location);
    document.queued();
    moneyBalance = moneyBalance.doubleValue() - document.price.doubleValue();
  }

  public void requestPrint(final Document document) {

    PrintingService.addToQueue(document);
    document.queued();
    moneyBalance = moneyBalance.doubleValue() - document.price.doubleValue();
  }

  public Client() {}

  public String toString() {

    return "Client{"
        + "id_count := "
        + Utils.toString(id_count)
        + ", id := "
        + Utils.toString(id)
        + ", name := "
        + Utils.toString(name)
        + ", moneyBalance := "
        + Utils.toString(moneyBalance)
        + ", documents := "
        + Utils.toString(documents)
        + "}";
  }
}
