package com.example.processor.service;

import com.example.processor.model.Book;
import com.example.processor.repository.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumer {

    private final BookRepository bookRepository;
    private final ObjectMapper objectMapper; // Pour convertir JSON en objet Java

    @KafkaListener(topics = "book-topic", groupId = "book-group")
    public void consume(ConsumerRecord<String, String> record) {
        try {
            // Convertir le JSON reçu en objet Book
            Book book = objectMapper.readValue(record.value(), Book.class);
            log.info("Received new book from Kafka: {}", book);

            // Sauvegarder dans la base de données
            bookRepository.save(book);
            log.info("Book saved successfully in database");

        } catch (Exception e) {
            log.error("Error processing book message", e);
        }
    }
}
