package com.crud.tasks.trello.facade;

import com.crud.tasks.domain.*;
import com.crud.tasks.mapper.TrelloMapper;
import com.crud.tasks.service.TrelloService;
import com.crud.tasks.trello.validator.TrelloValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class TrelloFacadeTest {
    @InjectMocks
    private TrelloFacade trelloFacade;

    @Mock
    private TrelloService trelloService;

    @Mock
    private TrelloValidator trelloValidator;

    @Mock
    private TrelloMapper trelloMapper;

    @Test
    public void shouldFetchEmptyList() {
        //Given
        List<TrelloListDto> trelloLists = new ArrayList<>();
        trelloLists.add(new TrelloListDto("1", "test_list", false));

        List<TrelloBoardDto> trelloBoards = new ArrayList<>();
        trelloBoards.add(new TrelloBoardDto("1", "test", trelloLists));

        List<TrelloList> mappedTrelloLists = new ArrayList<>();
        mappedTrelloLists.add(new TrelloList("1", "test_list", false));

        List<TrelloBoard> mappedTrelloBoards = new ArrayList<>();
        mappedTrelloBoards.add(new TrelloBoard("1", "test", mappedTrelloLists));

        Mockito.when(trelloService.fetchTrelloBoards()).thenReturn(trelloBoards);
        Mockito.when(trelloMapper.mapToBoards(trelloBoards)).thenReturn(mappedTrelloBoards);
        Mockito.when(trelloMapper.mapToBoardsDto(Mockito.anyList())).thenReturn(new ArrayList());
        Mockito.when(trelloValidator.validateTrelloBoards(mappedTrelloBoards)).thenReturn(new ArrayList<>());

        //When
        List<TrelloBoardDto> trelloBoardDtos = trelloFacade.fetchTrelloBoards();

        //Then
        assertNotNull(trelloBoardDtos);
        assertEquals(0, trelloBoardDtos.size());
    }

    @Test
    public void shouldFetchTrelloBoards() {
        //Given
        List<TrelloListDto> trelloLists = new ArrayList<>();
        trelloLists.add(new TrelloListDto("1", "my_list", false));

        List<TrelloBoardDto> trelloBoards = new ArrayList<>();
        trelloBoards.add(new TrelloBoardDto("1", "my_task", trelloLists));

        List<TrelloList> mappedTrelloLists = new ArrayList<>();
        mappedTrelloLists.add(new TrelloList("1", "my_list", false));

        List<TrelloBoard> mappedTrelloBoards = new ArrayList<>();
        mappedTrelloBoards.add(new TrelloBoard("1", "my_task", mappedTrelloLists));

        Mockito.when(trelloService.fetchTrelloBoards()).thenReturn(trelloBoards);
        Mockito.when(trelloMapper.mapToBoards(trelloBoards)).thenReturn(mappedTrelloBoards);
        Mockito.when(trelloMapper.mapToBoardsDto(Mockito.anyList())).thenReturn(trelloBoards);
        Mockito.when(trelloValidator.validateTrelloBoards(mappedTrelloBoards)).thenReturn(mappedTrelloBoards);

        //When
        List<TrelloBoardDto> trelloBoardDtos = trelloFacade.fetchTrelloBoards();

        //Then
        assertNotNull(trelloBoardDtos);
        assertEquals(1, trelloBoardDtos.size());

        trelloBoardDtos.forEach(trelloBoardDto -> {
            assertEquals("1", trelloBoardDto.getId());
            assertEquals("my_task", trelloBoardDto.getName());

            trelloBoardDto.getLists().forEach(trelloListDto -> {
                assertEquals("1", trelloListDto.getId());
                assertEquals("my_list", trelloListDto.getName());
                assertEquals(false, trelloListDto.isClosed());
            });
        });
    }

    @Test
    public void shouldCreateCard() {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("my_card", "test_desc", "1", "1");
        TrelloCard trelloCard = new TrelloCard("my_card", "test_desc", "1", "1");
        TrelloOfAttachment trelloOfAttachment = new TrelloOfAttachment(1, 2);
        AttachmentByTypeOfBadge attachmentByTypeOfBadge = new AttachmentByTypeOfBadge(trelloOfAttachment);
        BadgeOfCard badgeOfCard = new BadgeOfCard(1, attachmentByTypeOfBadge);
        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto("1", "my_card", "test_url", badgeOfCard);

        Mockito.when(trelloMapper.mapToCard(trelloCardDto)).thenReturn(trelloCard);
        Mockito.doNothing().when(trelloValidator).validateCard(trelloCard);
        Mockito.when(trelloMapper.mapToCardDto(trelloCard)).thenReturn(trelloCardDto);
        Mockito.when(trelloService.createTrelloCard(trelloCardDto)).thenReturn(createdTrelloCardDto);

        //When
        CreatedTrelloCardDto testCreatedTrelloCard = trelloFacade.createCard(trelloCardDto);

        //Then
        assertNotNull(testCreatedTrelloCard);
        assertEquals("1", testCreatedTrelloCard.getId());
        assertEquals("my_card", testCreatedTrelloCard.getName());
        assertEquals("test_url", testCreatedTrelloCard.getShortUrl());
        assertNotNull(testCreatedTrelloCard.getBadges());
        assertTrue(testCreatedTrelloCard.getBadges() instanceof BadgeOfCard);
        assertEquals(1, testCreatedTrelloCard.getBadges().getVotes());
        assertNotNull(testCreatedTrelloCard.getBadges().getAttachments());
        assertTrue(testCreatedTrelloCard.getBadges().getAttachments() instanceof AttachmentByTypeOfBadge);
        assertEquals(attachmentByTypeOfBadge, testCreatedTrelloCard.getBadges().getAttachments());
        assertTrue(testCreatedTrelloCard.getBadges().getAttachments().getTrello() instanceof TrelloOfAttachment);
        assertNotNull(testCreatedTrelloCard.getBadges().getAttachments().getTrello());
        assertEquals(1, testCreatedTrelloCard.getBadges().getAttachments().getTrello().getBoard());
        assertEquals(2, testCreatedTrelloCard.getBadges().getAttachments().getTrello().getCard());
    }
}