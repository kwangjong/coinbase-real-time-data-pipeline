build:
	docker build java_coinbase_producer/ -t producer
	docker build spark_stream_processor/ -t spark
	docker build cassandra/ -t cassandra
	docker build grafana/ -t grafana
	docker pull bitnami/kafka
	docker pull zookeeper
	docker network create data-pipeline

run:
	docker run --rm -d --name zookeeper --hostname zookeeper --network data-pipeline zookeeper

	docker run --rm -d --name kafka --hostname kafka --network data-pipeline\
		-e KAFKA_ZOOKEEPER_CONNECT=zookeeper:2181\
		-e KAFKA_LISTENERS=LISTENER_INTERNAL://kafka:9092,LISTENER_EXTERNAL://localhost:9093\
		-e KAFKA_LISTENER_SECURITY_PROTOCOL_MAP=LISTENER_INTERNAL:PLAINTEXT,LISTENER_EXTERNAL:PLAINTEXT\
		-e KAFKA_INTER_BROKER_LISTENER_NAME=LISTENER_INTERNAL\
		bitnami/kafka

	docker run --rm -d --name cassandra --hostname cassandra --network data-pipeline cassandra

	echo "waiting for cassandra to boot"
	sleep 60
	cassandra/./cassandra_load

	docker run --rm -d --name producer --hostname producer --network data-pipeline producer

	docker run --rm -d --name spark --hostname spark --network data-pipeline spark

	docker run --rm -d --name grafana --hostname grafana --network data-pipeline  grafana
