package com.example.todoapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/todos")
public class TodoController {

    @Autowired
    private TodoRepository todoRepository;

    @GetMapping
    public List<Todo> getAllTodos() {
        System.out.println("GET /todos called");
        return todoRepository.findAll();
    }

    @PostMapping
    public Todo createTodo(@RequestBody Todo todo) {
        System.out.println("POST /todos called with title: " + todo.getTitle());
        return todoRepository.save(todo);
    }

    @DeleteMapping("/{id}")
    public void deleteTodo(@PathVariable Long id) {
        System.out.println("DELETE /todos/" + id + " called");
        todoRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public Todo updateTodo(@PathVariable Long id, @RequestBody Todo updatedTodo) {
        System.out.println("PUT /todos/" + id + " called with completed: " + updatedTodo.isCompleted());
        return todoRepository.findById(id).map(todo -> {
            todo.setTitle(updatedTodo.getTitle());
            todo.setCompleted(updatedTodo.isCompleted());
            return todoRepository.save(todo);
        }).orElseThrow(() -> new RuntimeException("Todo not found"));
    }
}
