package pl.kalinowski.task_manager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kalinowski.task_manager.dto.TaskDto;
import pl.kalinowski.task_manager.exception.ResourceNotFoundException;
import pl.kalinowski.task_manager.model.Task;
import pl.kalinowski.task_manager.repository.TaskRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    // Pobieranie wszystkich zadania
    public List<TaskDto> getAllTasks() {
        return taskRepository.findAll().stream()
                .map(TaskDto::fromEntity)
                .collect(Collectors.toList());
    }

    // Pobierz zadanie po ID
    public TaskDto getTaskById(int id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id " + id));
        return TaskDto.fromEntity(task);
    }

    // Stworzenie nowego zadania
    public Task createTask(TaskDto taskDto) {
        Task task = TaskDto.toEntity(taskDto);
        return taskRepository.save(task);
    }

    // Aktualizacja zadania
    public TaskDto updateTask(int id, TaskDto dto) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setDueDate(dto.getDueDate());
        task.setCompleted(dto.isCompleted());

        return TaskDto.fromEntity(taskRepository.save(task));
    }

    // Usuń zadanie po id
    public void deleteTask(int id) {
        if (!taskRepository.existsById(id)) {
            throw new ResourceNotFoundException("Task not found");
        }
        taskRepository.deleteById(id);
    }
}



