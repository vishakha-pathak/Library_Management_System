import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

class Book 
{
    private int id;
    private String title;
    private String author;
    private int quantity;
    private HashMap<String, Integer> takenByStudents;

    public Book(int id, String title, String author, int quantity) 
     {
        this.id = id;
        this.title = title;
        this.author = author;
        this.quantity = quantity;
        this.takenByStudents = new HashMap<>();
    }

    public int getId()
    {
        return id;
    }

    public String getTitle() 
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getAuthor() 
    {
        return author;
    }

    public void setAuthor(String author)  
    {
        this.author = author;
    }

    public int getQuantity() 
   {
        return quantity;
    }

    public void setQuantity(int quantity) 
    {
        this.quantity = quantity;
    }

    public HashMap<String, Integer> getTakenByStudents() 
    {
        return takenByStudents;
    }

    public void addStudent(String studentName, int quantity) 
    {
        this.takenByStudents.put(studentName, quantity);
    }

    public void returnStudentBooks(String studentName, int quantity)
    {
        if (this.takenByStudents.containsKey(studentName)) 
        {
            int currentQuantity = this.takenByStudents.get(studentName);
            if (currentQuantity <= quantity)
             {
                this.takenByStudents.remove(studentName);
            } 
              else 
                {
                this.takenByStudents.put(studentName, currentQuantity - quantity);
            }
        }
    }

    @Override
    public String toString() 
    {
        return "Book ID: " + id + ", Title: " + title + ", Author: " + author + ", Quantity: " + quantity + ", Taken by: " + takenByStudents;
    }
}

class Student 
{
    private int id;
    private String name;
    private HashMap<Integer, Integer> borrowedBooks;

    public Student(int id, String name) 
    {
        this.id = id;
        this.name = name;
        this.borrowedBooks = new HashMap<>();
    }

    public int getId() 
    {
        return id;
    }

    public String getName() 
    {
        return name;
    }

    public HashMap<Integer, Integer> getBorrowedBooks()
    {
        return borrowedBooks;
    }

    public void borrowBook(int bookId, int quantity) 
    {
        borrowedBooks.put(bookId, borrowedBooks.getOrDefault(bookId, 0) + quantity);
    }

    public void returnBook(int bookId, int quantity) 
    {
        if (borrowedBooks.containsKey(bookId)) 
         {
            int currentQuantity = borrowedBooks.get(bookId);
            if (currentQuantity <= quantity) 
             {
                borrowedBooks.remove(bookId);
            } 
              else 
               {
                borrowedBooks.put(bookId, currentQuantity - quantity);
            }
        }
    }

    @Override
    public String toString() 
    {
        return "Student ID: " + id + ", Name: " + name + ", Borrowed Books: " + borrowedBooks;
    }
}

public class LibraryMS 
{
    private static ArrayList<Book> books = new ArrayList<>();
    private static ArrayList<Student> students = new ArrayList<>();
    private static int nextBookId = 1;
    private static int nextStudentId = 1;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String args[]) 
     {
        boolean exit = false;

        while (!exit) 
        {
            System.out.println("\nLibrary Management System");
            System.out.println("1. Add Book");
            System.out.println("2. Update Book");
            System.out.println("3. Delete Book");
            System.out.println("4. View Books");
            System.out.println("5. Manage Students");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();  
            switch (choice) 
            {
                case 1:
                    addBook();
                    break;
                case 2:
                    updateBook();
                    break;
                case 3:
                    deleteBook();
                    break;
                case 4:
                    viewBooks();
                    break;
                case 5:
                    manageStudents();
                    break;
                case 6:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }

    private static void addBook() 
     {
        System.out.print("Enter book title: ");
        String title = scanner.nextLine();
        System.out.print("Enter book author: ");
        String author = scanner.nextLine();
        System.out.print("Enter book quantity: ");
        int quantity = scanner.nextInt();
        scanner.nextLine();  

        Book book = new Book(nextBookId++, title, author, quantity);
        books.add(book);
        System.out.println("Book added successfully!");
    }

    private static void updateBook() 
    {
        System.out.print("Enter book ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine();  

        Book book = findBookById(id);
        if (book != null) 
        {
            System.out.print("Enter new title (or 'no' to keep the current title): ");
            String title = scanner.nextLine();
            if (!title.equalsIgnoreCase("no")) 
            {
                book.setTitle(title);
            }

            System.out.print("Enter new author (or 'no' to keep the current author): ");
            String author = scanner.nextLine();
            if (!author.equalsIgnoreCase("no")) 
            {
                book.setAuthor(author);
            }

            System.out.print("Enter new quantity (or 'no' to keep the current quantity): ");
            String quantityInput = scanner.nextLine();
            if (!quantityInput.equalsIgnoreCase("no")) 
            {
                int quantity = Integer.parseInt(quantityInput);
                book.setQuantity(quantity);
            }

            System.out.println("Book updated successfully!");
        } else {
            System.out.println("Book not found.");
        }
    }

    private static void deleteBook() 
     {
        System.out.println("Delete book by:");
        System.out.println("1. Book ID");
        System.out.println("2. Book Title");
        System.out.println("3. Book Author");
        System.out.print("Choose an option: ");

        int choice = scanner.nextInt();
        scanner.nextLine();  
        switch (choice)
        {
            case 1:
                deleteBookById();
                break;
            case 2:
                deleteBookByTitle();
                break;
            case 3:
                deleteBookByAuthor();
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private static void deleteBookById() 
     {
        System.out.print("Enter book ID to delete: ");
        int id = scanner.nextInt();
        scanner.nextLine();  
        Book book = findBookById(id);
        if (book != null) 
        {
            books.remove(book);
            System.out.println("Book deleted successfully!");
        } 
         else 
          {
            System.out.println("Book not found.");
        }
    }

    private static void deleteBookByTitle() 
     {
        System.out.print("Enter book title to delete: ");
        String title = scanner.nextLine();

        boolean found = false;
        ArrayList<Book> booksToRemove = new ArrayList<>();
        for (Book book : books) 
         {
            if (book.getTitle().equalsIgnoreCase(title))
            {
                booksToRemove.add(book);
                found = true;
            }
        }
        books.removeAll(booksToRemove);
        if (found) 
         {
            System.out.println("Book(s) with title \"" + title + "\" deleted successfully!");
        } 
          else 
           {
            System.out.println("No books found with the title \"" + title + "\".");
        }
    }

    private static void deleteBookByAuthor() 
     {
        System.out.print("Enter book author to delete: ");
        String author = scanner.nextLine();

        boolean found = false;
        ArrayList<Book> booksToRemove = new ArrayList<>();
        for (Book book : books) 
          {
            if (book.getAuthor().equalsIgnoreCase(author)) 
             {
                booksToRemove.add(book);
                found = true;
            }
        }
        books.removeAll(booksToRemove);
        if (found)
          {
            System.out.println("Book(s) with author \"" + author + "\" deleted successfully!");
        } 
          else 
          {
            System.out.println("No books found with the author \"" + author + "\".");
        }
    }

    private static void viewBooks() 
     {
        System.out.println("View books by:");
        System.out.println("1. Book ID");
        System.out.println("2. Book Title");
        System.out.println("3. Book Author");
        System.out.println("4. All Books");
        System.out.print("Choose an option: ");

        int choice = scanner.nextInt();
        scanner.nextLine(); 
        switch (choice) 
        {
            case 1:
                viewBooksById();
                break;
            case 2:
                viewBooksByTitle();
                break;
            case 3:
                viewBooksByAuthor();
                break;
            case 4:
                viewAllBooks();
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private static void viewBooksById() 
     {
        System.out.print("Enter up to 5 book IDs (comma-separated): ");
        String input = scanner.nextLine();
        String[] ids = input.split(",");

        if (ids.length > 5)   
         {
            System.out.println("Please enter a maximum of 5 book IDs.");
            return;
        }

        ArrayList<Integer> idList = new ArrayList<>();
        for (String id : ids) 
         {
            idList.add(Integer.parseInt(id.trim()));
        }

        boolean found = false;
        for (Book book : books)
         {
            if (idList.contains(book.getId())) 
            {
                System.out.println(book);
                found = true;
            }
        }
        if (!found) 
         {
            System.out.println("No books found for the specified ID(s).");
        }
    }

    private static void viewBooksByTitle() 
     {
        System.out.print("Enter up to 5 book titles (comma-separated): ");
        String input = scanner.nextLine();
        String[] titles = input.split(",");

        if (titles.length > 5) 
         {
            System.out.println("Please enter a maximum of 5 book titles.");
            return;
        }

        ArrayList<String> titleList = new ArrayList<>();
        for (String title : titles) 
         {
            titleList.add(title.trim());
        }

        boolean found = false;
        for (Book book : books) 
         {
            if (titleList.contains(book.getTitle())) 
             {
                System.out.println(book);
                found = true;
            }
        }
        if (!found) 
         {
            System.out.println("No books found for the specified title(s).");
        }
    }

    private static void viewBooksByAuthor() 
     {
        System.out.print("Enter up to 5 authors (comma-separated): ");
        String input = scanner.nextLine();
        String[] authors = input.split(",");

        if (authors.length > 5) 
        {
            System.out.println("Please enter a maximum of 5 authors.");
            return;
        }

        ArrayList<String> authorList = new ArrayList<>();
        for (String author : authors) 
        {
            authorList.add(author.trim());
        }

        boolean found = false;
        for (Book book : books) 
         {
            if (authorList.contains(book.getAuthor())) 
            {
                System.out.println(book);
                found = true;
            }
        }
        if (!found) 
         {
            System.out.println("No books found for the specified author(s).");
        }
    }

    private static void viewAllBooks() 
      {
        if (books.isEmpty())
        {
            System.out.println("No books available.");
        } 
         else 
          {
            for (Book book : books)
             {
                System.out.println(book);
            }
        }
    }

    private static Book findBookById(int id)
     {
        for (Book book : books) 
       {
            if (book.getId() == id) 
             {
                return book;
            }
        }
        return null;
    }

    private static void manageStudents() 
     {
        System.out.println("\nManage Students");
        System.out.println("1. Add Student");
        System.out.println("2. View Student");
        System.out.println("3. Return Books");
        System.out.print("Choose an option: ");

        int choice = scanner.nextInt();
        scanner.nextLine();  
        switch (choice) 
         {
            case 1:
                addStudent();
                break;
            case 2:
                viewStudents();
                break;
            case 3:
                returnBooks();
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private static void addStudent() 
     {
        System.out.print("Enter student name: ");
        String name = scanner.nextLine();

        Student student = new Student(nextStudentId++, name);
        students.add(student);

        System.out.print("Enter the number of books to borrow: ");
        int bookCount = scanner.nextInt();
        scanner.nextLine();  

        for (int i = 0; i < bookCount; i++) 
         {
            System.out.print("Enter book ID: ");
            int bookId = scanner.nextInt();
            scanner.nextLine();  
            Book book = findBookById(bookId);
            if (book == null) {
                System.out.println("Book not found.");
                continue;
            }

            System.out.print("Enter quantity: ");
            int quantity = scanner.nextInt();
            scanner.nextLine(); 

            if (book.getQuantity() >= quantity) 
             {
                student.borrowBook(bookId, quantity);
                book.setQuantity(book.getQuantity() - quantity);
                book.addStudent(name, quantity);
                System.out.println("Book borrowed successfully!");
            } 
              else 
              {
                System.out.println("Insufficient quantity available.");
            }
        }
    }

    private static void viewStudents() 
     {
        if (students.isEmpty()) 
        {
            System.out.println("No students available.");
        } 
         else 
          {
            for (Student student : students) 
            {
                System.out.println(student);
            }
        }
    }

    private static void returnBooks() 
     {
        System.out.print("Enter student ID: ");
        int studentId = scanner.nextInt();
        scanner.nextLine();  
        Student student = findStudentById(studentId);
        if (student == null)  
         {
            System.out.println("Student not found.");
            return;
        }

        System.out.print("Enter the number of books to return: ");
        int bookCount = scanner.nextInt();
        scanner.nextLine();  

        for (int i = 0; i < bookCount; i++)
          {
            System.out.print("Enter book ID: ");
            int bookId = scanner.nextInt();
            scanner.nextLine();  
            Book book = findBookById(bookId);
            if (book == null) 
             {
                System.out.println("Book not found.");
                continue;
            }

            System.out.print("Enter quantity: ");
            int quantity = scanner.nextInt();
            scanner.nextLine();  

            student.returnBook(bookId, quantity);
            book.setQuantity(book.getQuantity() + quantity);
            book.returnStudentBooks(student.getName(), quantity);
            System.out.println("Book returned successfully!");
        }
    }

    private static Student findStudentById(int id)
     {
        for (Student student : students) 
         {
            if (student.getId() == id) 
            {
                return student;
            }
        }
        return null;
    }
}
