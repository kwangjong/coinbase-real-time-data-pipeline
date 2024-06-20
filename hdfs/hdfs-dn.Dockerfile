FROM kwangjong/hdfs

CMD hdfs datanode \
    -D dfs.datanode.data.dir=/var/datanode \
    -fs $HDFS_NAMENODE_URL