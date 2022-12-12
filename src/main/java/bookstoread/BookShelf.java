package bookstoread;

import java.time.Year;
import java.util.*;
import java.util.stream.Collectors;

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
    public List<Book> arrange(Comparator<Book> criteria) {
        return books.stream().sorted(criteria).collect(Collectors.toList());
    }

    public Map<Year, List<Book>> groupByPublicationYear() {
        return books
                .stream()
                .collect(Collectors.groupingBy(book -> Year.of(book.
                        getPublishedOn().getYear())));
    }


}
