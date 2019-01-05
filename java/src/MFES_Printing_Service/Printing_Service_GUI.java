package MFES_Printing_Service;

import java.util.Scanner;

import org.overture.codegen.runtime.SeqUtil;

public class Printing_Service_GUI {
	public static Client client = new Client("MFES Demo client", 10L);
	public static Printer printera1 = new Printer(SeqUtil.seq(50L, 50L), SeqUtil.seq(50L, 50L, 50L), 'a');
	public static Printer printera2 = new Printer(SeqUtil.seq(50L, 50L), SeqUtil.seq(50L, 50L, 50L), 'a');
	public static Printer printerb1 = new Printer(SeqUtil.seq(50L, 50L), SeqUtil.seq(50L, 50L, 50L), 'b');
	public static Printer printerb2 = new Printer(SeqUtil.seq(50L, 50L), SeqUtil.seq(50L, 50L, 50L), 'b');
	public static Printer printerb3 = new Printer(SeqUtil.seq(10L, 10L), SeqUtil.seq(10L, 10L, 10L), 'b');
	
	private static final int MAIN_MENU = 0;
	private static final int ADD_BALANCE_MENU = 1;
	private static final int CREATE_DOCUMENT_MENU = 2;
	private static final int OWNED_DOCUMENTS_MENU = 3;
	private static final int SEND_TO_PRINT_QUEUE_MENU = 4;
	private static final int CHECK_PRINTERS_MENU = 5;
	private static final int EXECUTE_PRINTS_MENU = 6;
	
	public static void main(String[] args) {
		/* read input:
			Scanner scan = new Scanner(System.in);
			String s = scan.next();
			int i = scan.nextInt();
		*/
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
		//case OWNED_DOCUMENTS_MENU:
		//case SEND_TO_PRINT_QUEUE_MENU:
		//case CHECK_PRINTERS_MENU:
		//case EXECUTE_PRINTS_MENU:
		}
		return mainMenu();
	}
	
	private static int mainMenu() {
		System.out.println("\nMAIN MENU");
		System.out.println("1. Add money balance (Current Balance: " + client.moneyBalance + ")");
		System.out.println("2. Create document");
		System.out.println("3. Check owned documents");
		System.out.println("4. Send document to printing queue");
		//meter numa das impressoras documentos doutro cliente
		System.out.println("5. Check printers");
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
}
