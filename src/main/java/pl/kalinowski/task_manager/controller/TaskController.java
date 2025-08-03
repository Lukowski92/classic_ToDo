package pl.kalinowski.task_manager.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kalinowski.task_manager.dto.TaskDto;
import pl.kalinowski.task_manager.service.TaskService;

import java.util.List;

@RestController
@Tag(name = "Tasks", description = "Tasks management APIs")
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @Operation(summary = "Get all tasks")
    @GetMapping
    public List<TaskDto> getAll() {
        return taskService.getAllTasks();
    }

    @Operation(summary = "Get task by id")
    @GetMapping("/{id}")
    public TaskDto getById(@PathVariable int id) {
        return taskService.getTaskById(id);
    }

    @Operation(summary = "Add task")
    @PostMapping("/add")
    public ResponseEntity<TaskDto> create(@RequestBody @Valid TaskDto dto) {
        return new ResponseEntity<TaskDto>(TaskDto.fromEntity(taskService.createTask(dto)), HttpStatus.CREATED);
    }

    @Operation(summary = "Update task")
    @PutMapping("/{id}")
    public TaskDto update(@PathVariable int id, @RequestBody @Valid TaskDto dto) {
        return taskService.updateTask(id, dto);
    }

    @Operation(summary = "Delete task")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
