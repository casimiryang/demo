package com.designpattern.observer;

import java.util.Observable;
import java.util.Observer;

public class Boy02 implements Observer {

    @Override
    public void update(Observable observable, Object arg) {
        System.out.println("Boy02 点赞！");
    }
}