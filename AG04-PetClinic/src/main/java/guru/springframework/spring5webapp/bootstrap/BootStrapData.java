package guru.springframework.spring5webapp.bootstrap;

import guru.springframework.spring5webapp.domain.Author;
import guru.springframework.spring5webapp.domain.Book;
import guru.springframework.spring5webapp.domain.Publisher;
import guru.springframework.spring5webapp.repositories.AuthorRepository;
import guru.springframework.spring5webapp.repositories.BookRepository;
import guru.springframework.spring5webapp.repositories.PublisherRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BootStrapData implements CommandLineRunner {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final PublisherRepository publisherRepository;

    public BootStrapData(AuthorRepository authorRepository, BookRepository bookRepository, PublisherRepository publisherRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.publisherRepository = publisherRepository;
    }


    @Override
    public void run(String... args) throws Exception {

        System.out.println("Started in Bootstrap");

        Publisher publisher = new Publisher();
        publisher.setName("Publisher1");
        publisher.setCity("Zagreb");
        publisher.setState("HR");

        publisherRepository.save(publisher);

        System.out.println("Publisher count: " + publisherRepository.count());

        Author author1 = new Author("Paula","Pletes");
        Book book1 = new Book("My book","1234567");
        author1.getBooks().add(book1);
        book1.getAuthors().add(author1);

        book1.setPublisher(publisher);
        publisher.getBooks().add(book1);


        authorRepository.save(author1);
        bookRepository.save(book1);
        publisherRepository.save(publisher);

        Author author2 = new Author("Ivan","Ivic");
        Book book2 = new Book("His book","7654321");
        author2.getBooks().add(book2);
        book2.getAuthors().add(author2);

        book2.setPublisher(publisher);
        publisher.getBooks().add(book2);

        authorRepository.save(author2);
        bookRepository.save(book2);
        publisherRepository.save(publisher);

        System.out.println("Number of books: " + bookRepository.count());
        System.out.println("Publisher number of books: " + publisher.getBooks().size());

    }
}
