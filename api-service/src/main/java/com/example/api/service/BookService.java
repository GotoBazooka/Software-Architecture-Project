package com.example.api.service;

import com.example.api.dto.BookDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper; // Pour convertir un objet Java en JSON

    private static final String TOPIC = "book-topic";

    // Simule une base de données en mémoire (à remplacer par une vraie base plus tard)
    private final Map<String, BookDTO> bookDatabase = new HashMap<>();

    public String addBook(BookDTO bookDTO) {
        // Générer un ID unique pour le livre
        String bookId = UUID.randomUUID().toString();
        bookDTO.setId(bookId);

        // Sauvegarder en base (simulée)
        bookDatabase.put(bookId, bookDTO);

        // Convertir l'objet en JSON pour Kafka
        try {
            String bookJson = objectMapper.writeValueAsString(bookDTO);
            kafkaTemplate.send(TOPIC, bookJson);
            log.info("Book added and event published: {}", bookJson);
        } catch (JsonProcessingException e) {
            log.error("Error serializing book object", e);
            return "Error while processing book data";
        }

        return "Book added successfully with ID: " + bookId;
    }

    public BookDTO getBook(String bookId) {
        // Vérifier si le livre existe
        if (!bookDatabase.containsKey(bookId)) {
            log.warn("Book with ID {} not found", bookId);
            return null; // Le controller gérera la réponse HTTP 404
        }

        // Retourner le livre trouvé
        return bookDatabase.get(bookId);
    }
}
