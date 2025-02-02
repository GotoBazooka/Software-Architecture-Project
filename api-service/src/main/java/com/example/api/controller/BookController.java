package com.example.api.controller;

import com.example.api.dto.BookDTO;
import com.example.api.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor // Remplace @Autowired et génère un constructeur avec les dépendances
@Slf4j // Pour ajouter des logs
public class BookController {

    private final BookService bookService;

    @PostMapping("/add")
    public ResponseEntity<String> addBook(@RequestBody BookDTO bookDTO) {
        log.info("Adding new book: {}", bookDTO);
        String response = bookService.addBook(bookDTO);
        return ResponseEntity.status(201).body(response); // HTTP 201 Created
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<BookDTO> getBook(@PathVariable String id) {
        log.info("Fetching book with ID: {}", id);
        BookDTO book = bookService.getBook(id);

        if (book == null) {
            return ResponseEntity.notFound().build(); // HTTP 404 Not Found
        }
        return ResponseEntity.ok(book); // HTTP 200 OK
    }
}
