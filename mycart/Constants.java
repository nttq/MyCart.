package edu.hanu.mycart;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Constants {
    public static final ExecutorService executorService = Executors.newFixedThreadPool(4);
}
