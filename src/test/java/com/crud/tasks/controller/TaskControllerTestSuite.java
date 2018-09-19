package com.crud.tasks.controller;

import com.crud.tasks.domain.TaskDto;
import com.google.gson.Gson;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
public class TaskControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskController taskController;

    @Test
    public void shouldFetchTasks() throws Exception {
        //Given
        List<TaskDto> taskDtoList = new ArrayList<>();
        taskDtoList.add(new TaskDto(1L, "Test List", "Test Content"));
        Mockito.when(taskController.getTasks()).thenReturn(taskDtoList);
        //When & Then
        mockMvc.perform(get("/v1/task/getTasks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(jsonPath("$[0].title", Matchers.is("Test List")))
                .andExpect(jsonPath("$[0].content", Matchers.is("Test Content")));
    }

    @Test
    public void shouldFetchTask() throws Exception {
        //Given
        TaskDto taskDto = new TaskDto(1L, "Test Task", "Test Content");
        Mockito.when(taskController.getTask(taskDto.getId())).thenReturn(taskDto);
        //When & Then
        mockMvc.perform(get("/v1/task/getTask")
                .contentType(MediaType.APPLICATION_JSON)
                .param("taskId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(1)))
                .andExpect(jsonPath("$.title", Matchers.is("Test Task")))
                .andExpect(jsonPath("$.content", Matchers.is("Test Content")));
    }

    @Test
    public void shouldDeleteTask() throws Exception {
        //Given
        Mockito.doNothing().when(taskController).deleteTask(1L);
        //When & Then
        mockMvc.perform(delete("/v1/task/deleteTask")
                .contentType(MediaType.APPLICATION_JSON)
                .param("taskId", "1"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldUpdateTask() throws Exception {
        //Given
        TaskDto taskDtoBeforeUpdate = new TaskDto(1L, "Task Before", "Content Before");
        TaskDto taskDtoAfterUpdate = new TaskDto(1L, "Task After", "Content After");
        Mockito.when(taskController.updateTask(Mockito.any(TaskDto.class))).thenReturn(taskDtoAfterUpdate);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDtoBeforeUpdate);
        //When & Then
        mockMvc.perform(put("/v1/task/updateTask")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(1)))
                .andExpect(jsonPath("$.title", Matchers.is("Task After")))
                .andExpect(jsonPath("$.content", Matchers.is("Content After")));
    }

    @Test
    public void shouldCreateTask() throws Exception {
        //Given
        TaskDto taskDto = new TaskDto(1L, "Test Task", "Test Content");
        Mockito.doNothing().when(taskController).createTask(taskDto);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);
        //When & Then
        mockMvc.perform(post("/v1/task/createTask")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk());
    }
}