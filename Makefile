build:
	docker build java_coinbase_producer/ -t kwangjong/java-coinbase-producer
	docker build spark_stream_processor/ -t kwangjong/spark-stream-processor
	docker build cassandra/ -t kwangjong/cassandra
	docker build grafana/ -t kwangjong/grafana
	docker push kwangjong/java-coinbase-producer
	docker push kwangjong/spark-stream-processor
	docker push kwangjong/cassandra
	docker push kwangjong/grafana

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

delete:
	kubectl delete -f k8s/cassandra.yaml
	kubectl delete -f k8s/kafka-broker.yaml
	kubectl delete -f k8s/zookeeper.yaml
	kubectl delete -f k8s/spark-stream-processor.yaml
	kubectl delete -f k8s/java-coinbase-producer.yaml
	kubectl delete -f k8s/grafana.yaml


shutdown:
	minikube delete
