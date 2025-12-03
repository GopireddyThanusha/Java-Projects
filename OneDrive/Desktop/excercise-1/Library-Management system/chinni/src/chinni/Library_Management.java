package chinni;

import java.io.*;
import java.util.*;

// ---------------------------
// Book Class
// ---------------------------
class Book implements Serializable {

    private String name;
    private String author;
    private String issuedTo;
    private String issuedOn;
    private boolean isIssued;

    public Book(String name, String author) {
        this.name = name;
        this.author = author;
        this.isIssued = false;
        this.issuedTo = "Not Issued";
        this.issuedOn = "-";
    }

    public String getName() { return name; }
    public String getAuthor() { return author; }
    public boolean isIssued() { return isIssued; }

    public void issueBook(String studentName, String date) {
        this.isIssued = true;
        this.issuedTo = studentName;
        this.issuedOn = date;
    }

    public void returnBook() {
        this.isIssued = false;
        this.issuedTo = "Not Issued";
        this.issuedOn = "-";
    }

    @Override
    public String toString() {
        return "\nBook Name: " + name +
               "\nAuthor: " + author +
               "\nIssued To: " + issuedTo +
               "\nIssued On: " + issuedOn +
               "\nStatus: " + (isIssued ? "Issued" : "Available");
    }
}



// ---------------------------
// Library Management System
// ---------------------------
public class Library_Management {

    private static final String FILE_PATH = "books.dat";

    private List<Book> books;

    public Library_Management
() {
        books = loadBooks();
    }

    // Load Books
    @SuppressWarnings("unchecked")
    private List<Book> loadBooks() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            return (List<Book>) ois.readObject();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    // Save Books
    private void saveBooks() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(books);
        } catch (IOException e) {
            System.out.println("Error saving books!");
        }
    }

    // Add Book
    public void addBook() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Book Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Author Name: ");
        String author = sc.nextLine();

        books.add(new Book(name, author));
        saveBooks();

        System.out.println("Book added successfully!\n");
    }

    // Issue Book
    public void issueBook() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Book Name to Issue: ");
        String name = sc.nextLine();

        for (Book b : books) {
            if (b.getName().equalsIgnoreCase(name)) {

                if (b.isIssued()) {
                    System.out.println("Book is already issued!\n");
                    return;
                }

                System.out.print("Enter Student Name: ");
                String student = sc.nextLine();

                System.out.print("Enter Issue Date (DD/MM/YYYY): ");
                String date = sc.nextLine();

                b.issueBook(student, date);
                saveBooks();

                System.out.println("Book issued successfully!\n");
                return;
            }
        }
        System.out.println("Book not found.\n");
    }

    // Return Book
    public void returnBook() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Book Name to Return: ");
        String name = sc.nextLine();

        for (Book b : books) {
            if (b.getName().equalsIgnoreCase(name)) {

                if (!b.isIssued()) {
                    System.out.println("This book is not issued.\n");
                    return;
                }

                b.returnBook();
                saveBooks();

                System.out.println("Book returned successfully!\n");
                return;
            }
        }

        System.out.println("Book not found.\n");
    }

    // Display All Books
    public void displayBooks() {
        if (books.isEmpty()) {
            System.out.println("No books in library.\n");
            return;
        }

        for (Book b : books) {
            System.out.println(b);
            System.out.println("--------------------------------");
        }
    }

    // Main Menu
    public void start() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== Library Management System =====");
            System.out.println("1. Add Book");
            System.out.println("2. Issue Book");
            System.out.println("3. Return Book");
            System.out.println("4. Display All Books");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> addBook();
                case 2 -> issueBook();
                case 3 -> returnBook();
                case 4 -> displayBooks();
                case 5 -> {
                    System.out.println("Thank you! Exiting...");
                    return;
                }
                default -> System.out.println("Invalid choice! Try again.\n");
            }
        }
    }

    public static void main(String[] args) {
        new Library_Management().start();
    }

}
