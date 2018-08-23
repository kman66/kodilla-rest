package com.crud.tasks.trello.client.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class TrelloConfig {

    @Value("${trello.api.endpoint.prod}")
    private String trelloApiEndpoint;

    @Value("${trello.api.username}")
    private String username;

    @Value("${trello.api.key}")
    private String trelloAppKey;

    @Value("${trello.api.token}")
    private String trelloToken;
}
