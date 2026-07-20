package me.dmitriy.sushikhan.email;


import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import me.dmitriy.sushikhan.Ingredient;
import me.dmitriy.sushikhan.data.IngredientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.messaging.MessageHeaders;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Optional;
import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest()
public class SushiOrderEmailIntegrationTest {

    @Autowired
    private GreenMail mail;

    @Autowired
    private EmailProperties emailProperties;

    @MockitoBean
    private IngredientRepository ingredientRepository;

    @MockitoBean
    private OrderSubmitMessageHandler orderSubmitMessageHandler;

    @BeforeEach
    void setUp() {
        when(ingredientRepository.findById(any())).thenAnswer(invocation -> {
            String id = invocation.getArgument(0);
            return Optional.of(new Ingredient(id, "Ingredient "+id, Ingredient.Type.CHEESE));
        });
    }

    @Test
    void testEmailIntegration() {
        ServerSetup setup = new ServerSetup(0, "127.0.0.1", ServerSetup.PROTOCOL_IMAP);
        GreenMail greenMail = new GreenMail(setup);
        greenMail.start();

        int actualPort = greenMail.getImap().getPort();
    }

    @Test
    void shouldReceiveEmailTransformToOrderAndHandleIt() throws MessagingException {
        String senderEmail = "customer@example.com";
        String emailContent = "Philadelphia: salmon, cream cheese, sushi rice\n"+
                                "California: crab, avocado, rice";

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("127.0.0.1");
        mailSender.setPort(3143);

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        helper.setFrom(senderEmail);
        helper.setTo(emailProperties.getUsername() + "@sushikhan.com");
        helper.setSubject("New sushi order");
        helper.setText(emailContent, true);

        mail.getManagers().getUserManager()
                .getUser(emailProperties.getUsername())
                .deliver(mimeMessage);

        await().atMost(Duration.ofSeconds(10))
                .untilAsserted(() -> {
                    ArgumentCaptor<EmailOrder> orderCaptor = ArgumentCaptor.forClass(EmailOrder.class);
                    verify(orderSubmitMessageHandler).handle(orderCaptor.capture(), any(MessageHeaders.class));

                    EmailOrder order = orderCaptor.getValue();
                    assertThat(order.getEmail()).isEqualTo(senderEmail);
                    assertThat(order.getSushiList()).hasSize(2);
                    assertThat(order.getSushiList().getFirst().getName()).isEqualTo("Philadelphia");
                });
    }
}