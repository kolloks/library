package controllers;

import models.Book;
import models.User;
import play.mvc.With;
import service.BookReader;

import java.util.List;

@With(Secure.class)
@Check(value = "user")
public class Library extends Controller {

    public static void takeBook(Long bookId) {
        if (bookId==null) {
            List<Book> books = Book.getStockBooks();
            render(books);
        } else {
            User user = User.find("byEmail", Security.connected()).first();
            Book book = Book.findById(bookId);
            BookReader.addReader(user, book);
            redirect("Library.takeBook");
        }
    }

    public static void returnBook(Long bookId) {
        User user = User.find("byEmail", Security.connected()).first();
        if (bookId==null) {
            List<Book> books = user.readingBooks;
            render(books);
        } else {
            Book book = Book.findById(bookId);
            BookReader.returnReader(user, book);
            redirect("Library.returnBook");
        }
    }

}
