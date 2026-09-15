package ru.fedorov.Lab1.hello;

import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController 
public class HelloController {

    private ArrayList<String> arrayList;
    private HashMap<Integer, String> hashMap;

    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name",
                                      defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }
    

    @GetMapping("/update-array")
    public String updateArray(@RequestParam(value = "str") String str){
        if (arrayList == null) {
            arrayList = new ArrayList<>();
        }
        arrayList.add(str);
        return ("Added " + str);
    }

    @GetMapping("/show-array")
    public String showArrayList() {
        if (arrayList == null || arrayList.isEmpty()){
            return "ArrayList is null";
        }
        return arrayList.toString();
    }
    

    @GetMapping("/update-map")
    public String updateHashMap(@RequestParam(value = "str") String str) {
        if(hashMap == null) {
            hashMap = new HashMap<>(); 
        }
        int key = hashMap.size()+1;
        hashMap.put(key, str);
        return "Added " + str;
    }

    @GetMapping("/show-map")
    public String showHashMap(){
        if (hashMap == null || hashMap.values().isEmpty()){
            return "HashMap is null";
        }
        return  hashMap.toString();
    }

    @GetMapping("/show-all-lenght")
    public String showAllLenght (){
        int arraySize = (arrayList == null)? 0 : arrayList.size();
        int hashSize = (hashMap == null)? 0 : hashMap.size(); 
        return "ArrayList: " + arraySize +
               ", HashMap: " + hashSize;
    }

}
