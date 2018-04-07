package com.cybersoft.beatstreet.model;


import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ShoppingCart {

    private List<Beat> beats = new ArrayList<>();
    private int totalInCents;
    private int numberOfItems;

    public void addBeat(Beat b) {
        b.setPath(b.getPath().replace("src/main/resources/public", ""));
        this.beats.add(b);
        setTotalInCents();
        numberOfItems++;
    }

    public void removeBeatById(int beatId) {
        for (int i = 0; i < beats.size(); i++) {
            if (beats.get(i).getId() == beatId) {
                beats.remove(i);
            }
        }
        setTotalInCents();
        numberOfItems--;
    }

    public void setTotalInCents() {
        int sum = 0;
        for (Beat b : beats) {
            sum += b.getPriceInCents();
        }

        totalInCents = sum;
    }

    public List<Beat> getBeats() {
        return beats;
    }

    public void setBeats(List<Beat> beats) {
        this.beats = beats;
    }

    public int getTotalInCents() {
        return totalInCents;
    }

    public void setTotalInCents(int totalInCents) {
        this.totalInCents = totalInCents;
    }

    public int getNumberOfItems() {
        return numberOfItems;
    }

    public void setNumberOfItems(int numberOfItems) {
        this.numberOfItems = numberOfItems;
    }
}
