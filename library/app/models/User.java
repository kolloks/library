package models;

import play.data.validation.*;
import play.db.jpa.Model;
import play.db.jpa.Transactional;
import play.libs.Codec;

import javax.persistence.*;
import java.util.*;

@Entity
public class User extends Model {
    @Email @Unique @Required
    public String email;

    @MinSize(6) @Required
    public String password;

    @Required
    public String fullname;

    @Transient @Required @Equals("password")
    public String passwordConfirm;

    @ManyToMany(cascade = CascadeType.PERSIST, targetEntity = Book.class)
    @JoinTable(name="USER_READING",
            joinColumns=
            @JoinColumn(name="USER_ID", referencedColumnName="ID"),
            inverseJoinColumns=
            @JoinColumn(name="BOOK_ID", referencedColumnName="ID")
    )
    public List<Book> readingBooks;

    @ManyToMany(cascade = CascadeType.PERSIST, targetEntity = Book.class)
    @JoinTable(name="USER_HAVEREAD",
            joinColumns=
            @JoinColumn(name="USER_ID", referencedColumnName="ID"),
            inverseJoinColumns=
            @JoinColumn(name="BOOK_ID", referencedColumnName="ID")
    )
    public Set<Book> readedBooks;

    public boolean isAdmin;

    @Transient @IsTrue
    public boolean termsOfUse;

    /* ----------------------CONSTRUCTOR----------------------------- */

    public User(String email, String password, String fullname) {
        this.email = email;
        this.password = Codec.hexMD5(password);
        this.fullname = fullname;
        this.readingBooks = new ArrayList<>();
        this.readedBooks = new HashSet<>();
    }

    /* ----------------------   CHECK   ----------------------------- */

    public boolean checkPassword(String password) {
        return password.equals(Codec.hexMD5(password));
    }

    /* ------------------------SERVICE--------------------------- */

    @Transactional(readOnly = true)
    public static User connect(String email, String password) {
        return find("byEmailAndPassword", email, password).first();
    }

    /* ------------------------OTHER---------------------------- */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return isAdmin == user.isAdmin &&
                Objects.equals(email, user.email) &&
                Objects.equals(password, user.password) &&
                Objects.equals(fullname, user.fullname) &&
                Objects.equals(readingBooks, user.readingBooks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), email, password, fullname, readingBooks, isAdmin);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User: ");
        sb.append("email = '").append(email).append('\'');
        sb.append(", full name = '").append(fullname).append('\'');
        sb.append(", is admin = ").append(isAdmin);
        sb.append('.');
        return sb.toString();
    }
}
