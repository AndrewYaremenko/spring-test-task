package com.ua.TestTask.Controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import com.ua.TestTask.Requests.NumberRequest;

@RestController
@RequestMapping("/numbers")
public class NumberController {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public NumberController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostMapping("/saveNumber")
    public ResponseEntity<String> saveNumber(@RequestBody NumberRequest request) {
        int number = request.getNumber();
        
        String insertQuery = "INSERT INTO numbers (number) VALUES (?)";
        jdbcTemplate.update(insertQuery, number);
        
        return ResponseEntity.ok("Number saved successfully.");
    }

    @GetMapping("/getNumbers")
    public ResponseEntity<List<Long>> getNumbers() {
        String selectQuery = "SELECT number FROM numbers ORDER BY id";
        
        List<Long> numbersList = jdbcTemplate.query(selectQuery, (resultSet, rowNum) -> resultSet.getLong("number"));
        
        return ResponseEntity.ok(numbersList);
    }
}