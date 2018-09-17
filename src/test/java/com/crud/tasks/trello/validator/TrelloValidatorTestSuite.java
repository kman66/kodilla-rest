package com.crud.tasks.trello.validator;

import com.crud.tasks.domain.TrelloBoard;
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
public class TrelloValidatorTestSuite {
    @Autowired
    private TrelloValidator trelloValidator;

    @Test
    public void shouldValidateTrelloBoards() {
        //Given
        List<TrelloBoard> trelloBoards = new ArrayList<>();
        trelloBoards.add(new TrelloBoard("1", "test", new ArrayList<>()));
        trelloBoards.add(new TrelloBoard("2", "my_list", new ArrayList<>()));
        //When
        List<TrelloBoard> filteredList = trelloValidator.validateTrelloBoards(trelloBoards);
        //Then
        assertNotNull(filteredList);
        assertEquals(1, filteredList.size());
        assertEquals("2", filteredList.get(0).getId());
        assertEquals("my_list", filteredList.get(0).getName());
        assertEquals(0, filteredList.get(0).getLists().size());
    }
}