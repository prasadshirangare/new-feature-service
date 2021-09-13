package com.feature.collections;

import java.util.*;

/**
 * In a room there are some people. I want to do a survey on which company phones most people use. I have assigned a unique number on brand value.
 * 2 - Brand X
 * 5 - Brand Y
 * 6 - Samsung
 * 8 - Apple
 * Array of people's phone's brand value --> [2, 5, 2, 8, 5, 6, 8, 8]
 * We need the people in the room to stand in a line according to the number of users of the brand of phone which they have.
 * If 2 Brands have same number of users then output the lower brand value first and then the higher one.
 * In this example output should be - [Samsung, Brand X , Brand X ,Brand Y ,Brand Y , Apple, Apple, Apple]
 */
public class BrandName {

    public static void main(String[] args) {

        Map<Integer, String> cellPhoneMap = new HashMap<>();
        cellPhoneMap.put(2, "Brand X");
        cellPhoneMap.put(5, "Brand Y");
        cellPhoneMap.put(6, "Samsung");
        cellPhoneMap.put(8, "Apple");

        List<Integer> peoples = Arrays.asList(2, 5, 2, 8, 5, 6, 8, 8);

        Map<Integer, Integer> counterMap = new HashMap<>();
        for (Integer p : peoples) {
            counterMap.putIfAbsent(p, 0);
            counterMap.put(p, counterMap.get(p) + 1);
        }
        //System.out.println(counterMap);

        Map<String, Integer> finalMap = new HashMap<>();
        counterMap
                .forEach((k, v) -> {
                    finalMap.put(cellPhoneMap.get(k), v);
                });
        //System.out.println(finalMap);

        Set<Map.Entry<String, Integer>> entry = finalMap.entrySet();

        Comparator<Map.Entry<String, Integer>> valueComparator = (o1, o2) -> {
            if (o1.getValue().equals(o2.getValue())) {

                final Integer o1Key = cellPhoneMap.entrySet()
                        .stream().filter(entry1 -> o1.getValue().equals(entry1.getKey()))
                        .map(Map.Entry::getKey)
                        .findFirst()
                        .get();
                final Integer o2Key = cellPhoneMap.entrySet()
                        .stream().filter(entry1 -> o2.getValue().equals(entry1.getKey()))
                        .map(Map.Entry::getKey)
                        .findFirst()
                        .get();
                return o1Key.compareTo(o2Key);

            }
            return o1.getValue().compareTo(o2.getValue());
        };
        ArrayList<Map.Entry<String, Integer>> entries = new ArrayList<>(entry);
        Collections.sort(entries, valueComparator);
      //  System.out.println(entries);

        List<String> phoneList = new ArrayList<>();
        for (Map.Entry<String, Integer> e : entries) {
            for (int i = 1; i <= e.getValue(); i++) {
                phoneList.add(e.getKey());
            }
        }
        System.out.println(phoneList);

    }


}
