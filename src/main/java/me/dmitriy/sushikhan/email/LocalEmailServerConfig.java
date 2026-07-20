package me.dmitriy.sushikhan.email;


import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(EmailProperties.class)
public class LocalEmailServerConfig {

    private EmailProperties emailProperties;
    private GreenMail greenMail;

    public LocalEmailServerConfig(EmailProperties emailProperties) {
        this.emailProperties = emailProperties;
    }

    @PostConstruct
    public void startMailServer() {
        ServerSetup setup = new ServerSetup(3143, "127.0.0.1", ServerSetup.PROTOCOL_IMAP);
        greenMail = new GreenMail(setup);
        String email = emailProperties.getUsername() + "@sushikhan.com";
        greenMail.setUser(email, emailProperties.getUsername(), emailProperties.getPassword());
        greenMail.start();
    }

    @PreDestroy
    public void stopMailServer() {
        if (greenMail != null) {
            greenMail.stop();
        }
    }

}
