package bookstoread;

import java.time.Year;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

public class BookShelf {
    private List<Book> books = new ArrayList<>();

    public List<Book> books() {
        return Collections.unmodifiableList(books);
    }

    public void add(Book... booksToBeAdded){
        Arrays.stream(booksToBeAdded).forEach(book -> books.add(book));
        //with lamda press option+enter to convert 
        //Arrays.stream(booksToBeAdded).forEach(books::add);
    }


//    public List<Book> arrange() {
//        return books.stream().sorted().collect(Collectors.toList());
//        //below one will sort the original
////        books.sort(Comparator.naturalOrder());
////        return books;
//    }

    public List<Book> arrange() {
        return arrange(Comparator.naturalOrder());
    }

    //Generic Sorting Criteria
    public List<Book> arrange(Comparator<Book> criteria) {
        return books.stream().sorted(criteria).collect(Collectors.toList());
    }

    public Map<Year, List<Book>> groupByPublicationYear() {
//        return books
//                .stream()
//                .collect(groupingBy(book -> Year.of(book.
//                        getPublishedOn().getYear())));
        //More generic
        return groupBy(book -> Year.of(book.getPublishedOn().getYear()));
    }

    public <K> Map<K, List<Book>> groupBy(Function<Book, K> fx) {
        return books
                .stream()
                .collect(groupingBy(fx));
    }

    public Progress progress() {
        int booksRead = Long.valueOf(books.stream().filter(Book::isRead).
                count()).intValue();
        int booksToRead = books.size() - booksRead;
        int percentageCompleted = booksRead * 100 / books.size();
        int percentageToRead = booksToRead * 100 / books.size();
        return new Progress(percentageCompleted, percentageToRead, 0);
    }

    public List<Book> findBooksByTitle(String title) {
        return findBooksByTitle(title, b -> true);
    }
//    public List<Book> findBooksByTitle(String title, Predicate<Book> filter) {
//        return books.stream()
//                .filter(b -> b.getTitle().toLowerCase().contains(title))
//                .filter(filter)
//                .collect(Collectors.toList());
//    }
    public List<Book> findBooksByTitle(String title, BookFilter filter) {
        return books.stream()
                .filter(b -> b.getTitle().toLowerCase().contains(title))
                .filter(b -> filter.apply(b))
                .collect(Collectors.toList());
    }
}
