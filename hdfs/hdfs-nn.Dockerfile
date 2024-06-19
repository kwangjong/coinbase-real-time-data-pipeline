FROM kwangjong/hdfs

RUN hdfs namenode -format
CMD hdfs namenode \
    -D dfs.namenode.stale.datanode.interval=10000 \
    -D dfs.namenode.heartbeat.recheck-interval=30000 \
    -D dfs.namenode.datanode.registration.ip-hostname-check=false \
    -fs $HDFS_NAMENODE_URL