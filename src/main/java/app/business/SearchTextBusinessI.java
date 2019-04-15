package app.business;

import java.util.List;
import java.util.Map;

public interface SearchTextBusinessI {
    Map<String,Integer> searchText(List<String> requestList);
    Map<String,Integer> countText(int count);
}
