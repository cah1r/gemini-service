package dev.cah1r.geminiservice.utils;

import java.time.LocalDateTime;

public class CurrrentTime implements TimeSupplier {
    @Override
    public LocalDateTime get() {
        return LocalDateTime.now();
    }
}
