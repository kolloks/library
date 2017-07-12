package models;

import play.data.binding.As;
import play.data.validation.MaxSize;
import play.data.validation.Required;
import play.db.jpa.Model;
import play.db.jpa.Transactional;

import javax.persistence.*;
import java.util.*;

@Entity
public class Book extends Model {
    @Required
    public String title;

    @Required
    public String author;

    @Required
    public String genre;

    @Required @As("yyyy-MM-dd")
    public Date releaseDate;

    @Required
    public int number;

    @Lob
    @MaxSize(10000)
    public String retelling;

    @ManyToMany(cascade = CascadeType.PERSIST, mappedBy = "readingBooks", targetEntity = User.class)
    public List<User> readers;

    @ManyToMany(cascade = CascadeType.PERSIST, mappedBy = "readedBooks", targetEntity = User.class)
    public Set<User> haveRead;

    /* ----------------------CONSTRUCTORS----------------------------- */

    public Book(String title, String author, String genre, Date releaseDate, int number) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.releaseDate = releaseDate;
        this.number = number;
        this.retelling = "Описание отсутствует";
        this.readers = new ArrayList<>();
        this.haveRead = new HashSet<>();
    }
    public Book(String title, String author, String genre, Date releaseDate, int number, String retelling) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.releaseDate = releaseDate;
        this.number = number;
        this.retelling = retelling;
        this.readers = new ArrayList<>();
        this.haveRead = new HashSet<>();
    }

    /* -------------------------SERVICE--------------------------- */

    @Transactional(readOnly = true)
    public static List<Book> getBooks() {
        return Book.findAll();
    }

    @Transactional(readOnly = true)
    public static List<Book> getBooks(int number) {
        return Book.find("select b from Book b where b.number<?", number).fetch();
    }

    @Transactional(readOnly = true)
    public static List<Book> getStockBooks() {
        return Book.find("select b from Book b where b.number>0", null).fetch();
    }

    /* ------------------------OTHER---------------------------- */

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Book: ");
        sb.append(title).append(", ");
        sb.append(author);
        sb.append(", number = ").append(number);
        sb.append('.');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        if (!super.equals(o)) return false;
        Book book = (Book) o;
        return number == book.number &&
                Objects.equals(title, book.title) &&
                Objects.equals(author, book.author) &&
                Objects.equals(genre, book.genre) &&
                Objects.equals(releaseDate, book.releaseDate) &&
                Objects.equals(retelling, book.retelling) &&
                Objects.equals(readers, book.readers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), title, author, genre, releaseDate, number, retelling, readers);
    }
}
