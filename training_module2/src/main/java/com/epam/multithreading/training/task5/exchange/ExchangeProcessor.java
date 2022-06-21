package com.epam.multithreading.training.task5.exchange;

public class ExchangeProcessor extends Thread {

    @Override
    public void run() {
        while (true) {

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
