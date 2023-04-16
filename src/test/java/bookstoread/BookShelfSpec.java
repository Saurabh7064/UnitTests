package bookstoread;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import static java.util.Collections.singletonList;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("A bookshelf")
@ExtendWith(BooksParameterResolver.class)
public class BookShelfSpec {
    private BookShelf shelf;
    private Book effectiveJava;
    private Book codeComplete;
    private Book mythicalManMonth;
    private Book cleanCode;


    @BeforeEach
    void init(Map<String, Book> books) {
        shelf = new BookShelf();
        this.effectiveJava = books.get("Effective Java");
        this.codeComplete = books.get("Code Complete");
        this.mythicalManMonth = books.get("The Mythical Man-Month");
        this.cleanCode = books.get("Clean Code");
    }

    @Nested
    @DisplayName("is empty")
    class IsEmpty {
        @Test
        //@Disabled
        public void shelfEmptyWhenNoBookAdded() {
            List<Book> books = shelf.books();
            assertTrue(books.isEmpty(), () -> "BookShelf should be empty.");
        }

        @Test
        public void emptyBookShelfWhenAddIsCalledWithoutBooks() {
            shelf.add();
            List<Book> books = shelf.books();
            assertTrue(books.isEmpty(), () -> "BookShelf should be empty.");
        }
    }

    @Nested
    @DisplayName("after adding books")
    class BooksAreAdded {
        @Test
        public void bookshelfContainsTwoBooksWhenTwoBooksAdded() {
            shelf.add(effectiveJava, codeComplete);
            List<Book> books = shelf.books();
            assertEquals(2, books.size(), () -> "BookShelf should have two books.");
        }


        @Test
        public void booksReturnedFromBookShelfIsImmutableForClient() {
            shelf.add(effectiveJava, codeComplete);
            List<Book> books = shelf.books();
            try {
                books.add(mythicalManMonth);
                //if we are able to add then this test will fail
                fail(() -> "Should not be able to add book to books");
            } catch (Exception e) {
                assertTrue(e instanceof UnsupportedOperationException, () ->
                        "Should throw UnsupportedOperationException.");
            }
        }
    }

    @Nested
    @DisplayName("Are Books Arranged")
    class BooksAreArranged {
        @Test
        void bookshelfArrangedByBookTitle() {
            shelf.add(effectiveJava, codeComplete, mythicalManMonth);
            List<Book> books = shelf.arrange();
            assertEquals(Arrays.asList(codeComplete, effectiveJava,
                    mythicalManMonth), books, () -> "Books in a bookshelf should be " +
                    "arranged lexicographically by book title");
        }

        @Test
        void booksInBookShelfAreInInsertionOrderAfterCallingArrange() {
            shelf.add(effectiveJava, codeComplete, mythicalManMonth);
            shelf.arrange();
            List<Book> books = shelf.books();
            assertEquals(Arrays.asList(effectiveJava, codeComplete, mythicalManMonth),
                    books, () -> "Books in bookshelf are in insertion order");
        }

        @Test
        void bookshelfArrangedByUserProvidedCriteria() {
            shelf.add(effectiveJava, codeComplete, mythicalManMonth);
            //List<Book> books = shelf.arrange(); it will fail because of the order
            Comparator<Book> reversed = Comparator.<Book>naturalOrder().reversed();
            List<Book> books = shelf.arrange(reversed);
            assertEquals(Arrays.asList(mythicalManMonth, effectiveJava, codeComplete),
                    books, () -> "Books in a bookshelf are arranged in descending order of book title");
        }

        @Test
        void bookshelfArrangedByUserProvidedCriteriaAssetJ() {
            shelf.add(effectiveJava, codeComplete, mythicalManMonth);
            Comparator<Book> reversed = Comparator.<Book>naturalOrder().reversed();
            List<Book> books = shelf.arrange(reversed);
            assertThat(books).isSortedAccordingTo(reversed);
        }

        @Test
        @DisplayName("books inside bookshelf are grouped by publication year")
        void groupBooksInsideBookShelfByPublicationYear() {
            shelf.add(effectiveJava, codeComplete, mythicalManMonth, cleanCode);
            Map<Year, List<Book>> booksByPublicationYear = shelf.groupByPublicationYear();
            assertThat(booksByPublicationYear)
                    .containsKey(Year.of(2008))
                    .containsValues(Arrays.asList(effectiveJava, cleanCode));
            assertThat(booksByPublicationYear)
                    .containsKey(Year.of(2004))
                    .containsValues(singletonList(codeComplete));
            assertThat(booksByPublicationYear)
                    .containsKey(Year.of(1975))
                    .containsValues(singletonList(mythicalManMonth));
        }


        @Test
        @DisplayName("books inside bookshelf are grouped according to user provided criteria(group by author name)")
        void groupBooksByUserProvidedCriteria() {
            shelf.add(effectiveJava, codeComplete, mythicalManMonth, cleanCode);
            Map<String, List<Book>> booksByAuthor = shelf.groupBy(Book::getAuthor);
            assertThat(booksByAuthor)
                    .containsKey("Joshua Bloch")
                    .containsValues(singletonList(effectiveJava));
            assertThat(booksByAuthor)
                    .containsKey("Steve McConnel")
                    .containsValues(singletonList(codeComplete));
            assertThat(booksByAuthor)
                    .containsKey("Frederick Phillips Brooks")
                    .containsValues(singletonList(mythicalManMonth));
            assertThat(booksByAuthor)
                    .containsKey("Robert C. Martin")
                    .containsValues(singletonList(cleanCode));
        }
    }



}
