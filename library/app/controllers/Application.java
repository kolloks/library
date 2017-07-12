package controllers;

import models.Book;

import java.util.List;
import java.util.Random;

public class Application extends Controller {

    public static void index() {
        List<Book> books = Book.getStockBooks();
        render(books);
    }

    public static void book(Long bookId) {
        if (bookId==null) {
            List<Book> books = Book.getStockBooks();
            try {
                Book book = books.get(new Random().nextInt(books.size()));
                render(book);
            } catch (IllegalArgumentException e) {render();}
        } else {
            Book book = Book.findById(bookId);
            render(book);
        }
    }

    public static void books() {
        List<Book> books = Book.getBooks();
        render(books);
    }
}