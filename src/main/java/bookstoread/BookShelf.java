package bookstoread;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BookShelf {
    private List<String> bookList = new ArrayList<>();

    public List<String> books() {
        return bookList;
    }

    public void add(String book){
        bookList.add(book);
    }



}
