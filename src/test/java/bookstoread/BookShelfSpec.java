package bookstoread;

import org.junit.Ignore;
import org.junit.jupiter.api.*;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("A bookshelf")
public class BookShelfSpec {
    private BookShelf shelf;

    @BeforeEach
    void init() throws Exception {
        shelf = new BookShelf();
    }


    @Test
    @DisplayName("is empty when no book is added to it")
        //negative test case
    void shelfEmptyWhenNoBookAdded(TestInfo testInfo) {
        System.out.println("Working on test case " + testInfo.getDisplayName());
        List<String> books = shelf.books();
        assertTrue(books.isEmpty(), () -> "BookShelf should be empty.");
    }

    @Test
    void bookshelfContainsTwoBooksWhenTwoBooksAdded() {
        shelf.add("Effective Java", "Code Complete");
        List<String> books = shelf.books();
        assertEquals(2, books.size(), () -> "BookShelf should have two books.");
    }

    //    One last test case that we can add for this user story is to make sure that the client of
//    BookShelf can’t modify the books collection returned by the books method.
    @Test
    void booksReturnedFromBookShelfIsImmutableForClient() {
        shelf.add("Effective Java", "Code Complete");
        List<String> books = shelf.books();
        try {
            books.add("The Mythical Man-Month");
            fail(() -> "Should not be able to add book to books");
        } catch (Exception e) {
            assertTrue(e instanceof UnsupportedOperationException, () -> "Should " +
                    "throw UnsupportedOperationException.");
        }
    }

    @Test
    public void emptyBookShelfWhenAddIsCalledWithoutBooks() {
        shelf.add();
        List<String> books = shelf.books();
        assertTrue(books.isEmpty(), () -> "BookShelf should be empty.");
    }

    @Test
    void nullAssertionTest() {
        String str = null;
        assertNull(str);
        assertNull(str, "str should be null");
        assertNull(str, () -> "str should be null");
    }

    @Test
    void shouldCheckForEvenNumbers() {
        int number = new Random(10).nextInt();
        assertTrue(() -> number % 2 == 0, number + " is not an even number.");

        BiFunction<Integer, Integer, Boolean> divisible = (x, y) -> x % y == 0;
        Function<Integer, Boolean> multipleOf2 = (x) -> divisible.apply(x, 2);

        assertTrue(() -> multipleOf2.apply(number), () -> " 2 is not factor of " +
                number);

        List<Integer> numbers = Arrays.asList(1, 1, 1, 1, 2);
        assertTrue(() -> numbers.stream().distinct().anyMatch(BookShelfSpec::isEven),
                "Did not find an even number in the list");
    }

    @Test
    @Disabled
    public void shelfToStringShouldPrintBookCountAndTitles() throws Exception {
        List<String> books = shelf.books();
        books.add("The Phoenix Project");
        books.add("Java 8 in Action");
        String shelfStr = books.toString();
//        assertTrue(shelfStr.contains("2 States"), "1st book title missing");
//        assertTrue(shelfStr.contains("No one can hurt me"), "2nd book title missing ");
//        assertTrue(shelfStr.length() == 3);
//above throws just 1 error below is how we write multiple asserts in a single method
        assertAll(() -> assertTrue(shelfStr.contains("2 States"), "1st book title missing"),
                () -> assertTrue(shelfStr.contains("No one can hurt me"), "2nd book title missing "),
                () -> assertTrue(shelfStr.length() == 2));

    }

    static boolean isEven(int number) {
        return number % 2 == 0;
    }


}
