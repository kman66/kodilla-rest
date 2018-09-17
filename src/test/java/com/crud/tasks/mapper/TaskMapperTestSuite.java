package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskMapperTestSuite {
    @Autowired
    private TaskMapper taskMapper;

    @Test
    public void shouldMapToTask() {
        //Given
        TaskDto taskDto = new TaskDto(1L, "test_title", "test_content");
        //When
        Task task = taskMapper.mapToTask(taskDto);
        //Then
        assertNotNull(task);
        assertTrue(task instanceof Task);
        assertEquals(1, task.getId(), 0);
        assertEquals("test_title", task.getTitle());
        assertEquals("test_content", task.getContent());
    }

    @Test
    public void shouldMapToTaskDto() {
        //Given
        Task task = new Task(1L, "test_title", "test_content");
        //When
        TaskDto taskDto = taskMapper.mapToTaskDto(task);
        //Then
        assertNotNull(task);
        assertTrue(taskDto instanceof TaskDto);
        assertEquals(1, taskDto.getId(), 0);
        assertEquals("test_title", taskDto.getTitle());
        assertEquals("test_content", taskDto.getContent());
    }

    @Test
    public void shouldMapToTaskDtoList() {
        //Given
        List<Task> taskList = new ArrayList<>();
        taskList.add(new Task(1L, "test_title1", "test_content1"));
        taskList.add(new Task(2L, "test_title2", "test_content2"));
        //When
        List<TaskDto> taskDtoList = taskMapper.mapToTaskDtoList(taskList);
        //Then
        assertNotNull(taskDtoList);
        assertEquals(2, taskDtoList.size());
        assertTrue(taskDtoList.get(0) instanceof TaskDto);
        assertTrue(taskDtoList.get(1) instanceof TaskDto);
        assertEquals(1L, taskDtoList.get(0).getId(), 0);
        assertEquals(2L, taskDtoList.get(1).getId(), 0);
        assertEquals("test_title1", taskDtoList.get(0).getTitle());
        assertEquals("test_title2", taskDtoList.get(1).getTitle());
        assertEquals("test_content1", taskDtoList.get(0).getContent());
        assertEquals("test_content2", taskDtoList.get(1).getContent());
    }
}