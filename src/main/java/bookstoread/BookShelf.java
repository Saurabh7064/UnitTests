package bookstoread;

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


    public List<Book> arrange() {
        return books.stream().sorted().collect(Collectors.toList());
        //below one will sort the original
//        books.sort(Comparator.naturalOrder());
//        return books;
    }
}
