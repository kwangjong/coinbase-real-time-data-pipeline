FROM grafana/grafana:latest
# Install required plugin for cassandra
RUN grafana-cli plugins install hadesarchitect-cassandra-datasource

# Copy custom configuration files
COPY grafana.ini /etc/grafana/grafana.ini
COPY configs/cassandra.yaml /etc/grafana/provisioning/datasources/cassandra.yaml
COPY configs/dashboard.yaml /etc/grafana/provisioning/dashboards/dashboard.yaml
COPY dashboards /var/lib/grafana/dashboards
