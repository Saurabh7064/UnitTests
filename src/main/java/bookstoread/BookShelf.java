package bookstoread;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BookShelf {
    private List<String> books = new ArrayList<>();

    public List<String> books() {
        return Collections.unmodifiableList(books);
    }

    public void add(String... booksToBeAdded){
        Arrays.stream(booksToBeAdded).forEach(book -> books.add(book));
        //with lamda press option+enter to convert 
        //Arrays.stream(booksToBeAdded).forEach(books::add);
    }



}
