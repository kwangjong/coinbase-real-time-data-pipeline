build: kafka-test-build

kafka-test-build: start-minikube
	docker build test/test-consumer -t kwangjong/consumer
	docker build test/test-producer -t kwangjong/producer
	minikube image load kwangjong/consumer
	minikube image load kwangjong/producer

deploy: load-image
	kubectl apply -f k8s/producer.yaml
	kubectl apply -f k8s/consumer.yaml
	kubectl apply -f k8s/kafka-broker.yaml
	kubectl apply -f k8s/zookeeper.yaml

load-image: start-minikube
	@if [ "$$(minikube image ls | grep kwangjong/consumer -c)" != "1" ]; then\
                minikube image load kwangjong/consumer;\
        	minikube image load kwangjong/producer;\
        fi

start-minikube:
	@if [ "$$(minikube status | grep host:\ Running -c)" != "1" ]; then\
		minikube start;\
	fi

delete:
	minikube delete --all
