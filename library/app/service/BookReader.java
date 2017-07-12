package service;

import models.Book;
import models.User;

public class BookReader {
    public static boolean addReader(User user, Book book) {
        if (book.number>0) {
            book.readers.add(user);
            book.number--;
            user.readingBooks.add(book);
            book.save();
            user.save();
            return true;
        }
        return false;
    }

    public static boolean returnReader(User user, Book book) {
        user.readingBooks.remove(book);
        user.readedBooks.add(book);
        book.readers.remove(user);
        book.haveRead.add(user);
        book.number++;
        user.save();
        book.save();
        return true;
    }
}
