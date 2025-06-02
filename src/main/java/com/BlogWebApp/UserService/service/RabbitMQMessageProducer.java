package com.BlogWebApp.UserService.service;

import com.BlogWebApp.UserService.config.RabbitMQConstants;
import com.BlogWebApp.UserService.events.ResetPasswordEvent;
import com.BlogWebApp.UserService.events.UserCreatedEvent;
import com.BlogWebApp.UserService.events.UserDeletedEvent;
import com.BlogWebApp.UserService.events.UserUpdatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RabbitMQMessageProducer {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange.name}")
    private String exchange;


    public void sendUserCreatedNotification(UserCreatedEvent event){
        sendMessage(RabbitMQConstants.USER_CREATED_ROUTING_KEY, event, "Kullanıcı Oluşturuldu");
    }
    public void sendUserUpdatedNotification(UserUpdatedEvent event){
        sendMessage(RabbitMQConstants.USER_UPDATED_ROUTING_KEY, event, "Kullanıcı Güncellendi");
    }
    public void sendUserDeletedNotification(UserDeletedEvent event){
        sendMessage(RabbitMQConstants.USER_DELETED_ROUTING_KEY, event, "Kullanıcı Silindi");
    }
    public void sendUserResetPasswordNotification(ResetPasswordEvent event){
        sendMessage(RabbitMQConstants.USER_RESET_PASSWORD_ROUTING_KEY, event, "Şifre Sıfırlandı");
    }
    private void sendMessage(String routingKey,Object event,String action){
        rabbitTemplate.convertAndSend(exchange,routingKey,event);
        log.info("Blog {}: ID = {}",action,getEventId(event));
    }

    private Long getEventId(Object event){
        try {
            return (Long) event.getClass().getMethod("getUserId").invoke(event);
        }catch (Exception e) {
            log.error("Event ID alınırken hata oluştu ", e);
            return null;
        }
    }

}
