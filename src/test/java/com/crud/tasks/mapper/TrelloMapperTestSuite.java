package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
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
public class TrelloMapperTestSuite {
    @Autowired
    private TrelloMapper trelloMapper;

    @Test
    public void shouldMapToBoards() {
        //Given
        List<TrelloBoardDto> trelloBoardDtos = new ArrayList<>();
        trelloBoardDtos.add(new TrelloBoardDto("1", "test", returnTrelloListDto()));
        //When
        List<TrelloBoard> trelloBoards = trelloMapper.mapToBoards(trelloBoardDtos);
        //Then
        assertTrue(trelloBoards.get(0) instanceof TrelloBoard);
        assertEquals("1", trelloBoards.get(0).getId());
        assertEquals("test", trelloBoards.get(0).getName());
        assertEquals(3, trelloBoards.get(0).getLists().size());
    }

    @Test
    public void shouldMapToBoardsDto() {
        //Given
        List<TrelloBoard> trelloBoards = new ArrayList<>();
        trelloBoards.add(new TrelloBoard("1", "test", returnTrelloList()));
        //When
        List<TrelloBoardDto> trelloBoardDtos = trelloMapper.mapToBoardsDto(trelloBoards);
        //Then
        assertTrue(trelloBoardDtos.get(0) instanceof  TrelloBoardDto);
        assertEquals("1", trelloBoardDtos.get(0).getId());
        assertEquals("test", trelloBoardDtos.get(0).getName());
        assertEquals(3, trelloBoardDtos.get(0).getLists().size());
    }

    @Test
    public void shouldMapToList() {
        //Given
        List<TrelloListDto> trelloListDtos = returnTrelloListDto();
        //When
        List<TrelloList> trelloLists = trelloMapper.mapToList(trelloListDtos);
        //Then
        assertEquals(3, trelloLists.size());
        assertTrue(trelloLists.get(0) instanceof TrelloList);
        assertTrue(trelloLists.get(1) instanceof TrelloList);
        assertTrue(trelloLists.get(2) instanceof TrelloList);
        assertEquals("1", trelloLists.get(0).getId());
        assertEquals("2", trelloLists.get(1).getId());
        assertEquals("3", trelloLists.get(2).getId());
        assertEquals("test1", trelloLists.get(0).getName());
        assertEquals("test2", trelloLists.get(1).getName());
        assertEquals("test3", trelloLists.get(2).getName());
        assertEquals(false, trelloLists.get(0).isClosed());
        assertEquals(true, trelloLists.get(1).isClosed());
        assertEquals(false, trelloLists.get(2).isClosed());
    }

    @Test
    public void shouldMapToListDto() {
        //Given
        List<TrelloList> trelloLists = returnTrelloList();
        //When
        List<TrelloListDto> trelloListDtos = trelloMapper.mapToListDto(trelloLists);
        //Then
        assertEquals(3, trelloListDtos.size());
        assertTrue(trelloListDtos.get(0) instanceof TrelloListDto);
        assertTrue(trelloListDtos.get(0) instanceof TrelloListDto);
        assertTrue(trelloListDtos.get(1) instanceof TrelloListDto);
        assertTrue(trelloListDtos.get(2) instanceof TrelloListDto);
        assertEquals("1", trelloListDtos.get(0).getId());
        assertEquals("2", trelloListDtos.get(1).getId());
        assertEquals("3", trelloListDtos.get(2).getId());
        assertEquals("test1", trelloListDtos.get(0).getName());
        assertEquals("test2", trelloListDtos.get(1).getName());
        assertEquals("test3", trelloListDtos.get(2).getName());
        assertEquals(false, trelloListDtos.get(0).isClosed());
        assertEquals(false, trelloListDtos.get(1).isClosed());
        assertEquals(true, trelloListDtos.get(2).isClosed());
    }

    @Test
    public void shouldMapToCard() {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("testName", "testDesc", "1", "1");
        //When
        TrelloCard trelloCard = trelloMapper.mapToCard(trelloCardDto);
        //Then
        assertTrue(trelloCard instanceof TrelloCard);
        assertEquals("testName", trelloCard.getName());
        assertEquals("testDesc", trelloCard.getDescription());
        assertEquals("1", trelloCard.getPos());
        assertEquals("1", trelloCard.getListId());
    }

    @Test
    public void shouldMapToCardDto() {
        //Given
        TrelloCard trelloCard = new TrelloCard("testName", "testDesc", "1", "1");
        //When
        TrelloCardDto trelloCardDto = trelloMapper.mapToCardDto(trelloCard);
        //Then
        assertTrue(trelloCardDto instanceof TrelloCardDto);
        assertEquals("testName", trelloCardDto.getName());
        assertEquals("testDesc", trelloCardDto.getDescription());
        assertEquals("1", trelloCardDto.getPos());
        assertEquals("1", trelloCardDto.getListId());
    }

    private List<TrelloListDto> returnTrelloListDto() {
        List<TrelloListDto> trelloListDtos = new ArrayList<>();
        trelloListDtos.add(new TrelloListDto("1", "test1", false));
        trelloListDtos.add(new TrelloListDto("2", "test2", true));
        trelloListDtos.add(new TrelloListDto("3", "test3", false));
        return trelloListDtos;
    }

    private List<TrelloList> returnTrelloList() {
        List<TrelloList> trelloLists = new ArrayList<>();
        trelloLists.add(new TrelloList("1", "test1", false));
        trelloLists.add(new TrelloList("2", "test2", false));
        trelloLists.add(new TrelloList("3", "test3", true));
        return trelloLists;
    }
}