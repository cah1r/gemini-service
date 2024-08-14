package dev.cah1r.geminiservice.transit;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Document("lines")
public record Line(@Id String id, String name) {}
