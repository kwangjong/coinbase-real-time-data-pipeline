build:
	docker build java_coinbase_producer/ \
		-f java_coinbase_producer/java_coinbase_producer.Dockerfile \
		-t kwangjong/java-coinbase-producer
	
	docker build spark_stream_processor/ \
		-f spark_stream_processor/spark_stream_processor.Dockerfile \
		-t kwangjong/spark-stream-processor
	
	docker build cassandra/ \
		-f cassandra/cassandra.Dockerfile \
		-t kwangjong/cassandra
	
	docker build grafana/ \
		-f grafana/grafana.Dockerfile \
		-t kwangjong/grafana

	docker build hdfs/ -f hdfs/hdfs.Dockerfile -t kwangjong/hdfs
	docker build hdfs/ -f hdfs/hdfs-nn.Dockerfile -t kwangjong/hdfs-nn
	docker build hdfs/ -f hdfs/hdfs-dn.Dockerfile -t kwangjong/hdfs-dn

	docker build go_kafka_consumer/ -f go_kafka_consumer/go_kafka_consumer.Dockerfile -t kwangjong/go-kafka-consumer

	$(MAKE) push
	
push:
	docker push kwangjong/java-coinbase-producer
	docker push kwangjong/spark-stream-processor
	docker push kwangjong/cassandra
	docker push kwangjong/grafana
	docker push kwangjong/hdfs
	docker push kwangjong/hdfs-nn
	docker push kwangjong/hdfs-dn
	docker push kwangjong/go-kafka-consumer

run: start-minikube apply

start-minikube:
	minikube start

apply:
	kubectl apply -f k8s/cassandra.yaml
	kubectl apply -f k8s/kafka-broker.yaml
	kubectl apply -f k8s/zookeeper.yaml
	kubectl apply -f k8s/spark-stream-processor.yaml
	kubectl apply -f k8s/java-coinbase-producer.yaml
	kubectl apply -f k8s/grafana.yaml
	kubectl apply -f k8s/hdfs.yaml
	kubectl apply -f k8s/go-kafka-consumer.yaml

delete:
	kubectl delete -f k8s/cassandra.yaml
	kubectl delete -f k8s/kafka-broker.yaml
	kubectl delete -f k8s/zookeeper.yaml
	kubectl delete -f k8s/spark-stream-processor.yaml
	kubectl delete -f k8s/java-coinbase-producer.yaml
	kubectl delete -f k8s/grafana.yaml
	kubectl delete -f k8s/hdfs.yaml
	kubectl delete -f k8s/go-kafka-consumer.yaml

shutdown:
	minikube delete
