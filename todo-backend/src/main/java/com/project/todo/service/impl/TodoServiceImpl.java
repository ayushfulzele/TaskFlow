package com.project.todo.service.impl;

import com.project.todo.dto.TodoDto;
import com.project.todo.entity.Todo;
import com.project.todo.exception.ResourceNotFoundException;
import com.project.todo.repository.TodoRepository;
import com.project.todo.service.TodoService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TodoServiceImpl implements TodoService {
    private TodoRepository todoRepository;
    private ModelMapper modelMapper;
    public TodoDto addTodo(TodoDto todoDto){

        //convert todoDto into todo jpa entity
//        Todo todo = new Todo();
//        todo.setTitle(todoDto.getTitle());
//        todo.setDescription(todoDto.getDescription());
//        todo.setCompleted(todoDto.isCompleted());

        Todo todo = modelMapper.map(todoDto, Todo.class);

        Todo savedTodo = todoRepository.save(todo);

        //convert saved todo jpa entity object into todoDto object
//        TodoDto savedTodoDto = new TodoDto();
//        savedTodoDto.setId(savedTodo.getId());
//        savedTodoDto.setTitle(savedTodo.getTitle());
//        savedTodoDto.setDescription(savedTodo.getDescription());
//        savedTodoDto.setCompleted(savedTodo.isCompleted());

        TodoDto savedTodoDto = modelMapper.map(savedTodo, TodoDto.class);
        return savedTodoDto;
    }

    public TodoDto getTodo(Long id){
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Todo not found with id : "+id));
        return modelMapper.map(todo, TodoDto.class);
    }

    public List<TodoDto> getAllTodos(){
        List<Todo> todos = todoRepository.findAll();
        return todos.stream().map((todo) -> modelMapper.map(todo, TodoDto.class)).collect(Collectors.toList());
    }

    public TodoDto updateTodo(TodoDto todoDto, Long id){
       Todo todo = todoRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Todo not found with given id : "+id));
       todo.setTitle(todoDto.getTitle());
       todo.setDescription(todoDto.getDescription());
       todo.setCompleted(todoDto.isCompleted());

       Todo updatedTodo =  todoRepository.save(todo);
       return modelMapper.map(updatedTodo, TodoDto.class);
    }

    public void deleteTodo(Long id){
        Todo todo = todoRepository.findById(id).orElseThrow( () ->
            new ResourceNotFoundException("Todo not exists with given id : "+id));
        todoRepository.deleteById(id);
    }

    public TodoDto completeTodo(Long id){
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Todo not found with id : "+id));
        todo.setCompleted(Boolean.TRUE);

        Todo updatedTodo = todoRepository.save(todo);
        return modelMapper.map(updatedTodo, TodoDto.class);
    }

    public TodoDto inCompleteTodo(Long id){
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Todo not found with id : "+id));
        todo.setCompleted(Boolean.FALSE);

        Todo updatedTodo = todoRepository.save(todo);
        return modelMapper.map(updatedTodo, TodoDto.class);
    }
}
