package com.crud.tasks.scheduler;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.repository.TaskRepository;
import com.crud.tasks.service.MailCreatorService;
import com.crud.tasks.service.SimpleEmailService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class EmailSchedulerTest {

    @InjectMocks
    private EmailScheduler emailScheduler;

    @Mock
    private SimpleEmailService simpleEmailService;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private MailCreatorService mailCreatorService;

    @Mock
    private AdminConfig adminConfig;

    @Test
    public void shouldSendInformationEmail() {
        //Given
        ArgumentCaptor<Mail> captorMail = ArgumentCaptor.forClass(Mail.class);
        ArgumentCaptor<String> captorMailType = ArgumentCaptor.forClass(String.class);
        Mockito.when(taskRepository.count()).thenReturn(1L);

        //When
        emailScheduler.sendInformationEmail();
        Mockito.verify(simpleEmailService, Mockito.times(1)).send(captorMail.capture(), captorMailType.capture());
        Mail mail = captorMail.getValue();
        String mailType = captorMailType.getValue();

        //Then
        Assert.assertTrue(mail.getMessage().contains("task"));
        Assert.assertFalse(mail.getMessage().contains("tasks"));
        Assert.assertTrue(mailType.equals(MailCreatorService.INFO_NO_OF_TASKS_MAIL));
    }
}