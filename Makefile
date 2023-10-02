build:
	docker build java_coinbase_producer/ -t kwangjong/java-coinbase-producer
	docker build spark_stream_processor/ -t kwangjong/spark-stream-processor
	docker build cassandra/ -t kwangjong/cassandra
	docker build grafana/ -t kwangjong/grafana
	docker push kwangjong/java-coinbase-producer
	docker push kwangjong/spark-stream-processor
	docker push kwangjong/cassandra
	docker push kwangjong/grafana

run: 
	minikube start
	kubectl apply -f cassandra.yaml
	kubectl apply -f kafka-broker.yaml
	kubectl apply -f zookeeper.yaml
	kubectl apply -f spark-stream-processor.yaml
	kubectl apply -f java-coinbase-producer.yaml
	kubectl apply -f grafana.yaml

shutdown:
	minikube delete