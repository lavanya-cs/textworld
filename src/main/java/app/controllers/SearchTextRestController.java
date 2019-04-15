package app.controllers;

import app.business.SearchTextBusinessI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class SearchTextRestController {
    @Autowired
    public SearchTextBusinessI business;

    /**
     * this method intern invokes the business layer to get the required search functionality
     * @param requestMap
     * @return
     */
    @RequestMapping(value = "/search")
    public @ResponseBody
    Map<String, Integer> searchText(@RequestBody Map<String, List<String>> requestMap) {

        return business.searchText(requestMap.get("searchText"));
    }

    /**
     * this method intern invokes the business layer to get the required count functionality
     * @param count
     * @return
     */
    @RequestMapping(value = "/top/{count}")
    public @ResponseBody
    Map<String, Integer> countText(@PathVariable("count") int count) {
        return business.countText(count);
    }
}
