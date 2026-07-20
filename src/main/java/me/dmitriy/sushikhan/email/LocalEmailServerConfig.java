package me.dmitriy.sushikhan.email;


import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(EmailProperties.class)
public class LocalEmailServerConfig {

    @Bean(destroyMethod = "stop")
    public GreenMail greenMail(EmailProperties emailProperties) {
        ServerSetup setup = new ServerSetup(3143, "127.0.0.1", ServerSetup.PROTOCOL_IMAP);
        GreenMail greenMail = new GreenMail(setup);
        greenMail.setUser(
                emailProperties.getUsername() + "@sushikhan.com",
                emailProperties.getUsername(),
                emailProperties.getPassword()
        );
        greenMail.start();
        return greenMail;
    }

}
