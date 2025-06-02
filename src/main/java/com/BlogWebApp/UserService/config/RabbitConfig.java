package com.BlogWebApp.UserService.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Bean
    public DirectExchange userExchange(){
        return new DirectExchange(RabbitMQConstants.USER_EXCHANGE);
    }

    @Bean
    public Queue userCreatedQueue(){
        return new Queue(RabbitMQConstants.USER_CREATED_QUEUE, true);
    }

    @Bean
    public Queue userUpdatedQueue(){
        return new Queue(RabbitMQConstants.USER_UPDATED_QUEUE, true);
    }

    @Bean
    public Queue userResetPasswordQueue(){
        return new Queue(RabbitMQConstants.USER_RESET_PASSWORD_QUEUE, true);
    }

    @Bean
    public Queue userDeletedQueue(){
        return new Queue(RabbitMQConstants.USER_DELETED_QUEUE);
    }

    private Binding createBinding(Queue queue, String routingKey){
        return BindingBuilder.bind(queue)
                .to(userExchange())
                .with(routingKey);
    }

    @Bean
    public Binding userCreatedBinding(){
        return createBinding(userCreatedQueue(), RabbitMQConstants.USER_CREATED_ROUTING_KEY);
    }

    @Bean
    public Binding userUpdatedBinding(){
        return  createBinding(userUpdatedQueue(), RabbitMQConstants.USER_UPDATED_ROUTING_KEY);
    }

    @Bean
    public Binding userResetPasswordBinding(){
        return createBinding(userResetPasswordQueue(), RabbitMQConstants.USER_RESET_PASSWORD_ROUTING_KEY);
    }

    @Bean
    public Binding userDeletedBinding(){
        return createBinding(userDeletedQueue(), RabbitMQConstants.USER_DELETED_ROUTING_KEY);
    }
}
