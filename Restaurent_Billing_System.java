package chinni;



	import java.util.*;

	// ---------------------------
	// MenuItem Class
	// ---------------------------
	class MenuItem {
	    private String name;
	    private double price;

	    public MenuItem(String name, double price) {
	        this.name = name;
	        this.price = price;
	    }

	    public String getName() { return name; }
	    public double getPrice() { return price; }

	    @Override
	    public String toString() {
	        return name + " - ₹" + price;
	    }
	}

	// ---------------------------
	// OrderItem Class
	// ---------------------------
	class OrderItem {
	    private MenuItem item;
	    private int quantity;

	    public OrderItem(MenuItem item, int quantity) {
	        this.item = item;
	        this.quantity = quantity;
	    }

	    public MenuItem getItem() { return item; }
	    public int getQuantity() { return quantity; }
	    public double getTotalPrice() { return item.getPrice() * quantity; }
	}

	// ---------------------------
	// RestaurantBillingSystem Class
	// ---------------------------
	class RestaurantBillingSystem {
	    private Map<Integer, MenuItem> menu = new HashMap<>();
	    private List<OrderItem> orderList = new ArrayList<>();
	    private Scanner sc = new Scanner(System.in);
	    private final double GST = 0.18;   // 18% GST

	    // Add default menu items
	    public RestaurantBillingSystem() {
	        menu.put(1, new MenuItem("Pizza", 199));
	        menu.put(2, new MenuItem("Burger", 99));
	        menu.put(3, new MenuItem("Pasta", 149));
	        menu.put(4, new MenuItem("French Fries", 79));
	    }

	    // Display Menu
	    public void showMenu() {
	        System.out.println("\n------ MENU ------");
	        for (Map.Entry<Integer, MenuItem> entry : menu.entrySet()) {
	            System.out.println(entry.getKey() + ". " + entry.getValue());
	        }
	    }

	    // Add Menu Item
	    public void addMenuItem() {
	        System.out.print("Enter item name: ");
	        sc.nextLine(); // clear buffer
	        String name = sc.nextLine();
	        System.out.print("Enter price: ");
	        double price = sc.nextDouble();

	        int newId = menu.size() + 1;
	        menu.put(newId, new MenuItem(name, price));
	        System.out.println("Item added successfully!");
	    }

	    // Remove Menu Item
	    public void removeMenuItem() {
	        showMenu();
	        System.out.print("Enter Item ID to remove: ");
	        int id = sc.nextInt();

	        if (menu.containsKey(id)) {
	            menu.remove(id);
	            System.out.println("Item removed!");
	        } else {
	            System.out.println("Invalid ID!");
	        }
	    }

	    // Take Order
	    public void takeOrder() {
	        showMenu();
	        System.out.print("Enter Item ID: ");
	        int id = sc.nextInt();

	        if (!menu.containsKey(id)) {
	            System.out.println("Invalid Item ID!");
	            return;
	        }

	        System.out.print("Enter Quantity: ");
	        int qty = sc.nextInt();

	        orderList.add(new OrderItem(menu.get(id), qty));
	        System.out.println("Item added to order!");
	    }

	    // Generate Bill
	    public void generateBill() {
	        double subtotal = 0;

	        System.out.println("\n======= ITEMIZED BILL =======");
	        for (OrderItem order : orderList) {
	            double itemTotal = order.getTotalPrice();
	            subtotal += itemTotal;

	            System.out.println(order.getItem().getName() + " (x" + order.getQuantity() + ") = ₹" + itemTotal);
	        }

	        double gstAmount = subtotal * GST;
	        double grandTotal = subtotal + gstAmount;

	        System.out.println("------------------------------");
	        System.out.println("Subtotal: ₹" + subtotal);
	        System.out.println("GST (18%): ₹" + gstAmount);
	        System.out.println("Grand Total: ₹" + grandTotal);
	        System.out.println("==============================");
	    }

	    // Main Menu Loop
	    public void start() {
	        while (true) {
	            System.out.println("\n--------- Restaurant Billing System ---------");
	            System.out.println("1. Show Menu");
	            System.out.println("2. Add Menu Item");
	            System.out.println("3. Remove Menu Item");
	            System.out.println("4. Take Order");
	            System.out.println("5. Generate Bill");
	            System.out.println("6. Exit");
	            System.out.print("Choose an option: ");

	            int choice = sc.nextInt();

	            switch (choice) {
	                case 1: showMenu(); break;
	                case 2: addMenuItem(); break;
	                case 3: removeMenuItem(); break;
	                case 4: takeOrder(); break;
	                case 5: generateBill(); break;
	                case 6: System.out.println("Thank you! Visit again!"); return;
	                default: System.out.println("Invalid option!");
	            }
	        }
	    }
	}

	// ---------------------------
	// Main Class
	// ---------------------------
	public class Restaurent_Billing_System {
	    public static void main(String[] args) {
	        RestaurantBillingSystem system = new RestaurantBillingSystem();
	        system.start();
	    }
	}
