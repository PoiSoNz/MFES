package MFES_Printing_Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

import org.overture.codegen.runtime.MapUtil;
import org.overture.codegen.runtime.SeqUtil;
import org.overture.codegen.runtime.SetUtil;
import org.overture.codegen.runtime.Utils;
import org.overture.codegen.runtime.VDMSeq;
import org.overture.codegen.runtime.VDMSet;

public class Printing_Service_GUI {
	public static Client client = new Client("MFES Demo client", 0.5);
	public static Client hiddenClient = new Client("Hidden client", 50.0);
			
	public static Document doc1 = new Document(MFES_Printing_Service.quotes.BlackWhiteQuote.getInstance(), MFES_Printing_Service.quotes.A3Quote.getInstance(), 10, 10);
	public static Document doc2 = new Document(MFES_Printing_Service.quotes.ColorQuote.getInstance(), MFES_Printing_Service.quotes.A4Quote.getInstance(), 5, 10);
	public static Document doc3 = new Document(MFES_Printing_Service.quotes.BlackWhiteQuote.getInstance(), MFES_Printing_Service.quotes.A3Quote.getInstance(), 10, 10);
	public static Document doc4 = new Document(MFES_Printing_Service.quotes.ColorQuote.getInstance(), MFES_Printing_Service.quotes.A4Quote.getInstance(), 5, 10);
	public static Document doc5 = new Document(MFES_Printing_Service.quotes.BlackWhiteQuote.getInstance(), MFES_Printing_Service.quotes.A5Quote.getInstance(), 3, 3);
	public static Document doc6 = new Document(MFES_Printing_Service.quotes.BlackWhiteQuote.getInstance(), MFES_Printing_Service.quotes.A5Quote.getInstance(), 3, 3);
	public static Document doc7 = new Document(MFES_Printing_Service.quotes.BlackWhiteQuote.getInstance(), MFES_Printing_Service.quotes.A5Quote.getInstance(), 3, 3);
	
	public static Printer printera1 = new Printer(SeqUtil.seq(50L, 50L), SeqUtil.seq(50L, 50L, 50L), 'a');
	public static Printer printera2 = new Printer(SeqUtil.seq(50L, 50L), SeqUtil.seq(50L, 50L, 50L), 'a');
	public static Printer printerb1 = new Printer(SeqUtil.seq(50L, 50L), SeqUtil.seq(50L, 50L, 50L), 'b');
	public static Printer printerb2 = new Printer(SeqUtil.seq(50L, 50L), SeqUtil.seq(50L, 50L, 50L), 'b');
	public static Printer printerb3 = new Printer(SeqUtil.seq(10L, 12L), SeqUtil.seq(10L, 13L, 15L), 'b');
	
	private static final int MAIN_MENU = 0;
	private static final int ADD_BALANCE_MENU = 1;
	private static final int CREATE_DOCUMENT_MENU = 2;
	private static final int OWNED_DOCUMENTS_MENU = 3;
	private static final int SEND_TO_PRINT_QUEUE_MENU = 4;
	private static final int CHECK_PRINTERS_MENU = 5;
	private static final int EXECUTE_PRINTS_MENU = 6;
	
	public static void main(String[] args) {
		//Initialize printing service with printers
		VDMSet printers = SetUtil.set();
		printers.add(printera1);
		printers.add(printera2);
		printers.add(printerb1);
		printers.add(printerb2);
		printers.add(printerb3);	
		PrintingService ps = new PrintingService(printers);
		
		client.assignDocument(doc1);
		hiddenClient.assignDocument(doc2);
		hiddenClient.assignDocument(doc3);
		client.assignDocument(doc4);
		hiddenClient.assignDocument(doc5);
		hiddenClient.assignDocument(doc6);
		hiddenClient.assignDocument(doc7);
		
		hiddenClient.requestPrint(doc2, 'a');
		hiddenClient.requestPrint(doc3, 'a');
		hiddenClient.requestPrint(doc5, 'a');
		
		int menuShown = MAIN_MENU;
		while(true) {
			menuShown = displayMenu(menuShown);
		}
	}
	
	private static int displayMenu(int menuType) {
		switch(menuType) {
		case MAIN_MENU:
			return mainMenu();
		case ADD_BALANCE_MENU:
			return addBalanceMenu();
		case CREATE_DOCUMENT_MENU:
			return createDocumentMenu();
		case OWNED_DOCUMENTS_MENU:
			return showOwnedDocuments();
		case SEND_TO_PRINT_QUEUE_MENU:
			return sendDocumentToPrintQueueMenu();
		case CHECK_PRINTERS_MENU:
			return showPrinters();
		case EXECUTE_PRINTS_MENU:
			return executePrintsMenu();
		}
		return mainMenu();
	}
	
	private static int mainMenu() {
		System.out.println("\nMAIN MENU");
		System.out.println("1. Add money balance (Current Balance: " + Math.round((double) client.moneyBalance * 100.0) / 100.0 + ")");
		System.out.println("2. Create document");
		System.out.println("3. Check owned documents");
		System.out.println("4. Send document to printing queue");
		//meter numa das impressoras documentos doutro cliente
		System.out.println("5. Check printers");
		//printNext ou printAll numa impressora
		System.out.println("6. Execute prints");
		System.out.print("Select an option: ");
		return getSelectedOption(1, 6);
	}
	
	private static int addBalanceMenu() {
		System.out.println("\nADD BALANCE");
		System.out.println("Introduce the value you want to deposit (introduce 0 if you want to cancel): ");
		double value = getIntroducedDouble(0, 9999);
		if(value > 0)
			client.moneyDeposit(value);
		
		return MAIN_MENU;
	}
	
	private static int createDocumentMenu() {
		System.out.println("\nCREATE DOCUMENT");
		
		System.out.println("INK type");
		System.out.println("1. Black & White");
		System.out.println("2. Color");
		System.out.println("3. Cancel and return to main menu");
		System.out.print("Select an option: ");
		int colorT = getSelectedOption(1, 3);
		if(colorT == 3) return MAIN_MENU;
		Object colorType = MFES_Printing_Service.quotes.BlackWhiteQuote.getInstance();
		if(colorT == 2) colorType = MFES_Printing_Service.quotes.ColorQuote.getInstance();
		
		System.out.println("\nIntroduce the new document INK QUANTITY (introduce 0 if you want to cancel) (max 50): ");
		int inkQuantity = getSelectedOption(0, 50);
		if(inkQuantity == 0) return MAIN_MENU;
		
		System.out.println("\nPAPER type");
		System.out.println("1. A3");
		System.out.println("2. A4");
		System.out.println("3. A5");
		System.out.println("4. Cancel and return to main menu");
		System.out.print("Select an option: ");
		int paperT = getSelectedOption(1, 4);
		if(paperT == 4) return MAIN_MENU;
		Object paperType = MFES_Printing_Service.quotes.A3Quote.getInstance();
		if(paperT == 2) paperType = MFES_Printing_Service.quotes.A4Quote.getInstance();
		else if(paperT == 3) paperType = MFES_Printing_Service.quotes.A5Quote.getInstance();
		
		System.out.println("\nIntroduce the new document PAPER QUANTITY (introduce 0 if you want to cancel) (max 50): ");
		int paperQuantity = getSelectedOption(0, 50);
		if(paperQuantity == 0) return MAIN_MENU;
		
		Document newDoc = new Document(colorType, paperType, inkQuantity, paperQuantity);
		client.assignDocument(newDoc);
			    
		System.out.println("Created document info");
		System.out.println("Client owner: " + newDoc.owner.name);
		System.out.println("Document ID: " + newDoc.id);
		System.out.println("Color type: " + newDoc.color);
		System.out.println("Ink quantity: " + newDoc.inkQuantity);
		System.out.println("Paper type: " + newDoc.size);
		System.out.println("Paper quantity: " + newDoc.paperQuantity);
		System.out.println("Print cost: " + Math.round((double) newDoc.price * 100.0) / 100.0);
		System.out.println("Print status: " + newDoc.status);
		
		return MAIN_MENU;
	}
	
	private static int showOwnedDocuments() {
		System.out.println("\nOWNED DOCUMENTS");
		System.out.println(client.name + "'s documents");
		
		VDMSet clientDocs = SeqUtil.elems(Utils.copy(client.documents));
	    for (Iterator it = clientDocs.iterator(); it.hasNext();) {
	    	Document doc = ((Document) it.next());
	      	System.out.println("Document ID: " + doc.id);
			System.out.println("Color type: " + doc.color);
			System.out.println("Ink quantity: " + doc.inkQuantity);
			System.out.println("Paper type: " + doc.size);
			System.out.println("Paper quantity: " + doc.paperQuantity);
			System.out.println("Print cost: " + Math.round((double) doc.price * 100.0) / 100.0);
			System.out.println("Print status: " + doc.status + "\n");
	    }
	    return MAIN_MENU;
	}
	
	private static int sendDocumentToPrintQueueMenu() {
		System.out.println("\nSEND DOCUMENT TO PRINTING QUEUE");
		System.out.println(client.name + "'s documents eligible to be added to a printing queue");
		
		ArrayList<Integer> documentIDs = new ArrayList<Integer>();
		Map<Integer, Document> docToId = new HashMap<Integer, Document>();
		
		VDMSet clientDocs = SeqUtil.elems(Utils.copy(client.documents));
	    for (Iterator it = clientDocs.iterator(); it.hasNext();) {
	    	Document doc = ((Document) it.next());
	    	
	    	if(doc.status != MFES_Printing_Service.quotes.NotPrintedQuote.getInstance())
	    		continue;
	    	
	    	documentIDs.add(Long.valueOf((long)doc.id).intValue());
	    	docToId.put(Long.valueOf((long)doc.id).intValue(), doc);
	    	
	      	System.out.println("Document ID: " + doc.id);
			System.out.println("Color type: " + doc.color);
			System.out.println("Ink quantity: " + doc.inkQuantity);
			System.out.println("Paper type: " + doc.size);
			System.out.println("Paper quantity: " + doc.paperQuantity);
			System.out.println("Print cost: " + Math.round((double) doc.price * 100.0) / 100.0);
			System.out.println("\n");
	    }
	    if(documentIDs.size() == 0) {
	    	System.out.println("You have no documents eligible to be added to a printing queue");
	    	return MAIN_MENU;
	    }
	    
	    documentIDs.add(0);
	    System.out.println("\nIntroduce the ID of the document you wish to send to the printing queue (introduce 0 if you want to cancel): ");
		int selectedDocID = getIntroducedAmongst(documentIDs);
		if(selectedDocID == 0) return MAIN_MENU;
		
		Document selectedDoc = docToId.get(selectedDocID);
		
		if(client.moneyBalance.doubleValue() < selectedDoc.price.doubleValue()) {
			System.out.println("You don't have enough money balance to add this document to printing queue");
			return MAIN_MENU;
		}
		
		System.out.println("What location do you want the printer to be in");
		System.out.println("1. Section a");
		System.out.println("2. Section b");
		System.out.println("3. Anywhere");
		System.out.println("4. Cancel and return to main menu");
		System.out.print("Select an option: ");
		int input = getSelectedOption(1, 4);
		if(input == 4) return MAIN_MENU;
		
		if(input == 1)
			client.requestPrint(selectedDoc, 'a');
		else if(input == 2)
			client.requestPrint(selectedDoc, 'b');
		else
			client.requestPrint(selectedDoc);
		
	    return MAIN_MENU;
	}
	
	private static int showPrinters() {
		System.out.println("\nPRINTERS");
		showPrintersFromLocation('a');
		showPrintersFromLocation('b');
	    return MAIN_MENU;
	}
	
	private static void showPrintersFromLocation(char loc) {
		System.out.println("Section " + loc + " printers");
		
		VDMSet printers = PrintingService.getPrintersFromLocation(loc);
	    for (Iterator it = printers.iterator(); it.hasNext();) {
	    	Printer printer = ((Printer) it.next());
	    	  
	      	System.out.println("\tPrinter ID: " + printer.id);
	      	System.out.println("\tInk quantities: ");
		      	System.out.println("\t\tBlack & White: " + printer.inkQuantities.get(0));
				System.out.println("\t\tColor: " + printer.inkQuantities.get(1));
			System.out.println("\tPaper Quantities");
				System.out.println("\t\tA3: " + printer.paperQuantities.get(0));
				System.out.println("\t\tA4: " + printer.paperQuantities.get(1));
				System.out.println("\t\tA5: " + printer.paperQuantities.get(2));
			System.out.println("\tMalfunctions: ");
				VDMSet printerReports = SeqUtil.elems(Utils.copy(printer.openReports));
				if(printerReports.size() == 0) System.out.println("\t\tThere are no malfunctions");
			    for (Iterator itRep = printerReports.iterator(); itRep.hasNext();) {
			    	Report report = ((Report) itRep.next());
			      	System.out.println("\t\tReport ID: " + report.id + ", Malfunction: " + report.malfunction);
			    }
			System.out.println("\tDocuments in printing queue: ");
				VDMSet printerQueue = SeqUtil.elems(Utils.copy(printer.printingQueue));
				if(printerQueue.size() == 0) System.out.println("\t\tThere are no documents in queue");
			    for (Iterator itQu = printerQueue.iterator(); itQu.hasNext();) {
			    	Document doc = ((Document) itQu.next());
			      	System.out.println("\t\tID: " + doc.id + ", Ink type: " + doc.color + ", Ink quantity: " + doc.inkQuantity + ", Paper type: " + doc.size + ", Paper quantity: " + doc.paperQuantity);
			    }
			System.out.println("\n");
	    }
	}
	
	private static int executePrintsMenu() {
		System.out.println("\nEXECUTE PRINTS");
		
		System.out.println("\nIntroduce the ID of the printer you wish to execute the print on (introduce 0 if you want to cancel): ");
		int printerID = getSelectedOption(0, 5);
		if(printerID == 0) return MAIN_MENU;
		
		System.out.println("\nPRINT operation");
		System.out.println("1. Print next document");
		System.out.println("2. Print all documents in queue");
		System.out.println("3. Cancel and return to main menu");
		System.out.print("Select an option: ");
		int printOperation = getSelectedOption(1, 3);
		if(printOperation == 3) return MAIN_MENU;
		
		performPrintOperation(printerID, printOperation);
		
	    return MAIN_MENU;
	}
	
	private static void performPrintOperation(int printerID, int operation) {
		VDMSet printers = MapUtil.dom(Utils.copy(PrintingService.servicePrinters));
	    for (Iterator it = printers.iterator(); it.hasNext();) {
	    	Printer printer = ((Printer) it.next());
	    	
	    	if(printer.id.intValue() == printerID && operation == 1) {
	    		printer.printNext();
	    		return;
	    	} else if (printer.id.intValue() == printerID && operation == 2) {
	    		printer.printAll();
	    		return;
	    	}
	    }
	}
	
	private static int getSelectedOption(int min, int max) {
		Scanner scan = new Scanner(System.in);
		int input = scan.nextInt();
		if(input < min)
			return min;
		if(input > max)
			return max;
		return input;
	}
	
	private static double getIntroducedDouble(int min, int max) {
		Scanner scan = new Scanner(System.in);
		double input = scan.nextDouble();
		if(input < min)
			return min;
		if(input > max)
			return max;
		return input;
	}
	
	private static int getIntroducedAmongst(ArrayList<Integer> possibilities) {
		while(true){
			Scanner scan = new Scanner(System.in);
			int input = scan.nextInt();
			if(possibilities.contains(input))
				return input;
			System.out.println("Invalid value! Try again: ");
		}
	}
}
