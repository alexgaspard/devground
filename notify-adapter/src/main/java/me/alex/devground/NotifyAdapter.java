package me.alex.devground;

import me.alex.devground.ports.*;

public class NotifyAdapter implements NotifyService {
    private final Printer printer;

    public NotifyAdapter(Printer printer) {
        this.printer = printer;
    }

    @Override
    public void notify(String message) {
        printer.print(message);
    }
}
