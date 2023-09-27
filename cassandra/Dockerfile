FROM cassandra:latest

COPY ./cassandra-setup.cql /cassandra-setup.cql

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