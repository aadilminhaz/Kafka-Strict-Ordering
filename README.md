## Kafka Strict Message Ordering

### Basic setup

- Resource : 
  - To install kafka via docker - https://medium.com/@akshat.available/kafka-with-spring-boot-using-docker-compose-1552cccaec8e
  - Kafka partition and transaction control - https://docs.spring.io/spring-kafka/reference/kafka/receiving-messages/listener-annotation.html#record-listener

### Install Kafka via docker
- create docker-compose.yaml at the root of the project. (Using Confluent kafka for docker image)
- run > docker compose up -d
- Kafka UI can be accessed on - http://localhost:8090/ui/clusters/local/all-topics?perPage=25
- For application, bootstrap server - localhost:29092

### Manual Kafka setup - less resource intensive

- Download - https://kafka.apache.org/downloads
- unzip (.tgz) go to the directory of downloaded kafka
- Start zookeper and then kafka
  - bin/zookeeper-server-start.sh config/zookeeper.properties
  - bin/kafka-server-start.sh config/server.properties
- To see the topic  - first time the topic would be created automatically - 1 replication 1 -partition
  - sh bin/kakfa-topic.sh --bootstrap-server localhost:9092 --describe --topic <topicname>
- To create a new topic with custom config
  - sh bin/kafka-topics.sh --bootstrap-server localhost:9092 --create --topic transactions2 --partition 3 --replication-factor 1