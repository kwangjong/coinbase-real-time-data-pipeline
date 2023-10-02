FROM cassandra:latest

COPY ./cassandra-setup.cql /cassandra-setup.cql
RUN sed -i "s/rpc_address: localhost/rpc_address: 0.0.0.0/g" /etc/cassandra/cassandra.yaml
RUN sed -i "s/# broadcast_rpc_address: 1.2.3.4/broadcast_rpc_address: 1.2.3.4/g" /etc/cassandra/cassandra.yaml

RUN mkdir /home/cassandra
RUN chown cassandra:users /home/cassandra

USER cassandra
CMD cassandra -f | tee ~/cassandra.log & \
    echo waiting for cassandra to start && \
    tail -n 0 -F ~/cassandra.log | grep -q "Created default superuser role" && \
    echo cassandra is ready && \
    cqlsh 127.0.0.1 -f /cassandra-setup.cql && \
    echo loaded cassandra-setup.cql && \
    tail -f /dev/null