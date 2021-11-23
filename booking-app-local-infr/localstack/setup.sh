#!/usr/bin/env bash

export SQS_REGION=eu-west-2
export SQS_ENDPOINT=http://localhost:4566
  
topic_arn=$(awslocal sns create-topic --name booking-app-notification-topic.fifo --endpoint-url $SQS_ENDPOINT --region $SQS_REGION --attributes FifoTopic=true --output text)
echo "Created topic: $topic_arn"

queue_dlq_url=$(awslocal sqs create-queue --queue-name booking-app-notification-dlq-queue.fifo --region $SQS_REGION --endpoint-url $SQS_ENDPOINT --attributes \
                        FifoQueue=true,DelaySeconds=0,MaximumMessageSize=262144,MessageRetentionPeriod=345600,ReceiveMessageWaitTimeSeconds=0,VisibilityTimeout=60 --output text)
echo "Created DLQ queue: $queue_dlq_url"
queue_dlq_arn=$(awslocal sqs get-queue-attributes --endpoint-url $SQS_ENDPOINT --queue-url "${queue_dlq_url}" --query 'Attributes.QueueArn' --output text)

queue_url=$(awslocal sqs create-queue --queue-name booking-app-notification-queue.fifo --region $SQS_REGION --endpoint-url $SQS_ENDPOINT --attributes \
                      FifoQueue=true,DelaySeconds=0,MaximumMessageSize=262144,MessageRetentionPeriod=345600,ReceiveMessageWaitTimeSeconds=0,VisibilityTimeout=60,RedrivePolicy=\"\{\\\"deadLetterTargetArn\\\":\\\""${queue_dlq_arn}"\\\",\\\"maxReceiveCount\\\":\\\"4\\\"\}\" --output text)
echo "Created queue: $queue_url"
queue_arn=$(awslocal sqs get-queue-attributes --endpoint-url $SQS_ENDPOINT --queue-url "${queue_url}" --query 'Attributes.QueueArn' --output text)

subscription_arn=$(  awslocal sns subscribe --topic-arn "${topic_arn}" --protocol sqs --notification-endpoint "${queue_arn}" --endpoint-url $SQS_ENDPOINT --region $SQS_REGION --output text)
echo "Subscription to the topic for queue [$queue_url] created: $subscription_arn"