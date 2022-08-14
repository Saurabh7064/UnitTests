package bookstoread;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BookShelf {
    private List<String> bookList = new ArrayList<>();

    public List<String> books() {
        return bookList;
    }

    public void add(String... books){
        Arrays.stream(books).forEach(book -> bookList.add(book));
        //with lamda press option+enter to convert 
        Arrays.stream(books).forEach(bookList::add);
    }



}
