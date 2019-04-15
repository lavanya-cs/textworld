package app.business;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class SearchTextBusiness implements SearchTextBusinessI {

    /**
     * Search for the text from the request and returns the map with the count
     * @param requestList
     * @return
     */
    public Map<String, Integer> searchText(List<String> requestList) {
        if(requestList==null || requestList.isEmpty()){
            return new HashMap<String,Integer>();
        }

        List<String> list = new ArrayList<>();

        try (BufferedReader br = Files.newBufferedReader(Paths.get(this.getClass().getResource("/Text.txt").toURI()))) {

            //br returns as stream and convert it into a List
            list = br.lines().collect(Collectors.toList());

        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, Integer> responseMap = new HashMap<>();

        for (String str : list) {
            for (String reqString : requestList) {
                if (str.contains(reqString)) {
                    int occurence = StringUtils.countOccurrencesOf(str, reqString);
                    if (responseMap.containsKey(reqString)) {
                        responseMap.put(reqString, responseMap.get(reqString) + occurence);
                    } else {
                        responseMap.put(reqString, occurence);
                    }
                } else {
                    if (!responseMap.containsKey(reqString)) {
                        responseMap.put(reqString, 0);
                    }
                }
            }

        }
        return responseMap;
    }

    /**
     * get the words with most occurences based on the input
     * @param count
     * @return
     */
    public Map<String, Integer> countText(int count) {
        HashMap<String, Integer> sortedHashMap = new HashMap<>();

        try (BufferedReader bufferedReader = Files.newBufferedReader(Paths.get(this.getClass().getResource("/Text.txt").toURI()))) {
            Stream<String> str = bufferedReader.lines();

            List<String> lst = str.collect(Collectors.toList());
            Map<String, Integer> map = new HashMap<>();
            for (String lstStr : lst) {
                String[] str1 = lstStr.split(" ");
                for (String wordsStr : str1) {
                    if (!map.containsKey(wordsStr)) {
                        map.put(wordsStr, 1);
                    } else {
                        map.put(wordsStr, map.get(wordsStr) + 1);
                    }
                }

            }
            sortedHashMap = map.entrySet().stream()
                    .sorted(Collections.reverseOrder(Map.Entry.comparingByValue())).limit(count)
                    .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue(), (e1, e2) -> e2, LinkedHashMap::new));


        } catch (Exception e) {

        }


        return sortedHashMap;
    }
}
