package me.dmitriy.sushikhan.email;

import jakarta.mail.internet.InternetAddress;
import me.dmitriy.sushikhan.Ingredient;
import me.dmitriy.sushikhan.Sushi;
import me.dmitriy.sushikhan.data.IngredientRepository;
import org.slf4j.LoggerFactory;
import org.springframework.integration.mail.transformer.AbstractMailMessageTransformer;
import org.springframework.integration.support.AbstractIntegrationMessageBuilder;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;
import org.apache.commons.text.similarity.LevenshteinDistance;
import jakarta.mail.Message;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;

@Component
public class EmailToOrderTransformer extends AbstractMailMessageTransformer<EmailOrder> {

    private static Logger log =
            (Logger) LoggerFactory.getLogger(EmailToOrderTransformer.class);
    private static final String SUBJECT_KEYWORDS = "SUSHI ORDER";
    private final IngredientRepository ingredientRepository;

    public EmailToOrderTransformer(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    protected AbstractIntegrationMessageBuilder<EmailOrder>
            doTransform(Message mailMessage) {
        EmailOrder sushiOrder = null;
        try {
            sushiOrder = processPayload(mailMessage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return MessageBuilder.withPayload(sushiOrder);
    }

    private EmailOrder processPayload(Message mailMessage) throws Exception{
        try{
            String subject = mailMessage.getSubject();
            if(subject.toUpperCase().contains(SUBJECT_KEYWORDS)){
                String email =
                        ((InternetAddress) mailMessage.getFrom()[0]).getAddress();
                String content = mailMessage.getContent().toString();
                return parseEmailToOrder(email, content);
            }
        }catch (MessagingException e){
            log.error("MessagingException: {}", e);
        }catch (IOException e){
            log.error("IOException: {}", e);
        }
        return null;
    }

    private EmailOrder parseEmailToOrder(String email, String content){
        EmailOrder order = new EmailOrder(email);
        String[] lines = content.split("\\r?\\n");
        for(String line : lines){
            if(line.trim().length() > 0 && line.contains(":")){
                String[] lineSplit  = line.split(":");
                String sushiName = lineSplit[0].trim();
                String ingredients  = lineSplit[1].trim();
                String[] ingredientsSplit = ingredients.split(",");
                List<Ingredient> ingredientList = new ArrayList<>();
                for(String ingredientName : ingredientsSplit){
                    Ingredient ingredient = lookupIngredientCode(ingredientName.trim());
                    if(ingredient != null){
                        ingredientList.add(ingredient);
                    }
                }
                Sushi sushi = new Sushi(sushiName);
                sushi.setIngredients(ingredientList);
                order.addSushi(sushi);
            }
        }
        return order;
    }

    private Ingredient lookupIngredientCode(String ingredientName){
        Iterable<Ingredient> allIngredients = ingredientRepository.findAll();
        for(Ingredient ingredient : allIngredients){
            String ucIngredientName = ingredientName.toUpperCase();
            if(LevenshteinDistance.getDefaultInstance()
                    .apply(ucIngredientName, ingredient.getName()) < 3 ||
                    ucIngredientName.contains(ingredient.getName()) ||
                    ingredient.getName().contains(ucIngredientName)){
                return ingredient;
            }
        }
        return null;
    }
}
