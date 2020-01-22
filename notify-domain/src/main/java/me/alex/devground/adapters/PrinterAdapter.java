package me.alex.devground.adapters;

import me.alex.devground.ports.*;

public class PrinterAdapter implements Printer {
    @Override
    public void print(String message) {
        System.out.println(message);
    }
}
