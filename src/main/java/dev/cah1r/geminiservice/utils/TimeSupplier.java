package dev.cah1r.geminiservice.utils;

import java.time.LocalDateTime;
import java.util.function.Supplier;

@FunctionalInterface
public interface TimeSupplier extends Supplier<LocalDateTime> {
}
