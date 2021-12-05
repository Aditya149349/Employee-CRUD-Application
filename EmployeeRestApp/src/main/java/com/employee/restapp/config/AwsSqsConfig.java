package com.employee.restapp.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClient;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import com.amazonaws.services.sqs.buffered.AmazonSQSBufferedAsyncClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.config.QueueMessageHandlerFactory;
import org.springframework.cloud.aws.messaging.config.SimpleMessageListenerContainerFactory;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.cloud.aws.messaging.listener.QueueMessageHandler;
import org.springframework.cloud.aws.messaging.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class AwsSqsConfig {

    @Value("${cloud.aws.region.static}")
    private String region;

    @Value("${cloud.aws.credentials.access-key}")
    private String awsAccessKey;

    @Value("${cloud.aws.credentials.secret-key}")
    private String awsSecretKey;

    //@Bean
    /*public QueueMessagingTemplate queMessagingTemplate(){
        return new QueueMessagingTemplate(amazonSqsAsync());

    }*/
    @Bean
    public SimpleMessageListenerContainer messageListenerContainer(AmazonSQSAsync amazonSQSAsync) {
        SimpleMessageListenerContainerFactory factory = new SimpleMessageListenerContainerFactory();
        factory.setAmazonSqs(amazonSQSAsync);
        factory.setMaxNumberOfMessages(10);
        SimpleMessageListenerContainer simpleMessageListenerContainer = factory.createSimpleMessageListenerContainer();
        simpleMessageListenerContainer.setQueueStopTimeout(1000);
        simpleMessageListenerContainer.setMessageHandler(messageHandler(amazonSQSAsync));
        return simpleMessageListenerContainer;
    }

    @Bean
    public QueueMessageHandler messageHandler(AmazonSQSAsync amazonSQSAsync) {
        QueueMessageHandlerFactory queueMessageHandlerFactory = new QueueMessageHandlerFactory();
        queueMessageHandlerFactory.setAmazonSqs(amazonSQSAsync);
        QueueMessageHandler messageHandler = queueMessageHandlerFactory.createQueueMessageHandler();
        return messageHandler;
    }

   /* @Bean
    public AmazonSQSAsync awsSqsAsync() {
        AmazonSQSAsyncClient amazonSQSAsyncClient = new AmazonSQSAsyncClient(new DefaultAWSCredentialsProviderChain());
        amazonSQSAsyncClient.setRegion(Region.getRegion(Regions.fromName(region)));
        return new AmazonSQSBufferedAsyncClient(amazonSQSAsyncClient);
    }*/

    @Bean
    public QueueMessagingTemplate queueMessagingTemplate(AmazonSQSAsync amazonSqs) {
        return new QueueMessagingTemplate(amazonSqs);
    }
    //@Primary
    @Bean
    public AmazonSQSAsync amazonSqsAsync() {
        return AmazonSQSAsyncClientBuilder.standard().withRegion(Regions.US_EAST_1)
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(awsAccessKey,awsSecretKey)))
                .build();
    }

}

