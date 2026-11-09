import java.util.ArrayList;
import java.util.Scanner;

class Stock {
    String name;
    String symbol;
    double price;

    Stock(String name, String symbol, double price) {
        this.name = name;
        this.symbol = symbol;
        this.price = price;
    }
}

class Holding {
    Stock stock;
    int quantity;
    double averagePrice;

    Holding(Stock stock, int quantity) {
        this.stock = stock;
        this.quantity = quantity;
        this.averagePrice = stock.price;
    }
}

public class StockTradingPlatform {

    static Scanner sc = new Scanner(System.in);

    static double balance = 100000;

    static ArrayList<Stock> stocks = new ArrayList<>();
    static ArrayList<Holding> portfolio = new ArrayList<>();
    static ArrayList<String> transactions = new ArrayList<>();

    public static void main(String[] args) {

        stocks.add(new Stock("Reliance", "RELIANCE", 1200));
        stocks.add(new Stock("TCS", "TCS", 3500));
        stocks.add(new Stock("Infosys", "INFY", 1800));
        stocks.add(new Stock("HDFC Bank", "HDFC", 1600));
        stocks.add(new Stock("Tata Motors", "TATAMOTORS", 900));

        int choice;

        do {
            System.out.println("\n===== STOCK TRADING PLATFORM =====");
            System.out.println("1. View Market Data");
            System.out.println("2. Buy Stocks");
            System.out.println("3. Sell Stocks");
            System.out.println("4. View Portfolio");
            System.out.println("5. Portfolio Performance");
            System.out.println("6. Transaction History");
            System.out.println("7. Check Balance");
            System.out.println("8. Exit");

            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    displayMarket();
                    break;

                case 2:
                    buyStock();
                    break;

                case 3:
                    sellStock();
                    break;

                case 4:
                    displayPortfolio();
                    break;

                case 5:
                    performance();
                    break;

                case 6:
                    history();
                    break;

                case 7:
                    System.out.println("Available Balance: Rs." + balance);
                    break;

                case 8:
                    System.out.println("Thank you for using Stock Trading Platform!");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 8);

        sc.close();
    }

    static void displayMarket() {

        System.out.println("\n----- MARKET DATA -----");

        for (Stock s : stocks) {
            System.out.println(s.symbol + " | " +
                    s.name + " | Rs." + s.price);
        }
    }

    static void buyStock() {

        displayMarket();

        System.out.print("\nEnter stock symbol: ");
        String symbol = sc.next().toUpperCase();

        Stock selected = findStock(symbol);

        if (selected == null) {
            System.out.println("Stock not found!");
            return;
        }

        System.out.print("Enter quantity: ");
        int qty = sc.nextInt();

        if (qty <= 0) {
            System.out.println("Invalid quantity!");
            return;
        }

        double cost = selected.price * qty;

        if (cost > balance) {
            System.out.println("Insufficient balance!");
            return;
        }

        balance -= cost;

        Holding holding = findHolding(selected);

        if (holding == null) {

            portfolio.add(new Holding(selected, qty));

        } else {

            double totalCost =
                    holding.averagePrice * holding.quantity + cost;

            holding.quantity += qty;

            holding.averagePrice =
                    totalCost / holding.quantity;
        }

        transactions.add("BUY " + qty + " shares of " + symbol);

        System.out.println("Stock purchased successfully!");
        System.out.println("Total Cost: Rs." + cost);
        System.out.println("Remaining Balance: Rs." + balance);
    }

    static void sellStock() {

        displayPortfolio();

        System.out.print("\nEnter stock symbol: ");
        String symbol = sc.next().toUpperCase();

        Stock selected = findStock(symbol);

        if (selected == null) {
            System.out.println("Stock not found!");
            return;
        }

        Holding holding = findHolding(selected);

        if (holding == null) {
            System.out.println("You do not own this stock!");
            return;
        }

        System.out.print("Enter quantity to sell: ");
        int qty = sc.nextInt();

        if (qty <= 0 || qty > holding.quantity) {
            System.out.println("Invalid quantity!");
            return;
        }

        double amount = selected.price * qty;

        balance += amount;

        holding.quantity -= qty;

        if (holding.quantity == 0) {
            portfolio.remove(holding);
        }

        transactions.add("SELL " + qty + " shares of " + symbol);

        System.out.println("Stock sold successfully!");
        System.out.println("Received Amount: Rs." + amount);
    }

    static void displayPortfolio() {

        System.out.println("\n----- YOUR PORTFOLIO -----");

        if (portfolio.isEmpty()) {
            System.out.println("No stocks purchased yet.");
            return;
        }

        for (Holding h : portfolio) {

            System.out.println(
                    h.stock.symbol +
                    " | Quantity: " + h.quantity +
                    " | Current Price: Rs." + h.stock.price +
                    " | Average Buy Price: Rs." + h.averagePrice
            );
        }
    }

    static void performance() {

        double invested = 0;
        double currentValue = 0;

        for (Holding h : portfolio) {

            invested += h.averagePrice * h.quantity;

            currentValue += h.stock.price * h.quantity;
        }

        double profitLoss = currentValue - invested;

        System.out.println("\n----- PORTFOLIO PERFORMANCE -----");

        System.out.println("Invested Amount: Rs." + invested);
        System.out.println("Current Value: Rs." + currentValue);
        System.out.println("Profit/Loss: Rs." + profitLoss);

        if (profitLoss > 0) {
            System.out.println("Status: PROFIT");
        } else if (profitLoss < 0) {
            System.out.println("Status: LOSS");
        } else {
            System.out.println("Status: NO PROFIT NO LOSS");
        }
    }

    static void history() {

        System.out.println("\n----- TRANSACTION HISTORY -----");

        if (transactions.isEmpty()) {
            System.out.println("No transactions available.");
            return;
        }

        for (String t : transactions) {
            System.out.println(t);
        }
    }

    static Stock findStock(String symbol) {

        for (Stock s : stocks) {

            if (s.symbol.equalsIgnoreCase(symbol)) {
                return s;
            }
        }

        return null;
    }

    static Holding findHolding(Stock stock) {

        for (Holding h : portfolio) {

            if (h.stock == stock) {
                return h;
            }
        }

        return null;
    }
}