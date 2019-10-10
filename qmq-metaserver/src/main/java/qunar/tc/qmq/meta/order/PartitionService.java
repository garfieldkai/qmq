package qunar.tc.qmq.meta.order;

import qunar.tc.qmq.PartitionAllocation;
import qunar.tc.qmq.meta.Partition;
import qunar.tc.qmq.meta.PartitionSet;
import qunar.tc.qmq.meta.model.ClientMetaInfo;

import java.util.List;

/**
 * @author zhenwei.liu
 * @since 2019-08-22
 */
public interface PartitionService {

    /**
     * 更新分区信息, 包含如下功能:
     * 1. 关闭旧分区
     * 2. 生化新分区
     *
     * @param subject              主题
     * @param physicalPartitionNum 物理分区数量
     */
    boolean updatePartitions(String subject, int physicalPartitionNum, List<String> brokerGroups);

    PartitionSet getLatestPartitionSet(String subject);

    List<PartitionSet> getLatestPartitionSets();

    List<PartitionSet> getAllPartitionSets();

    List<Partition> getAllPartitions();

    /**
     * 获取正在生效的分区分配列表
     *
     * @return 分区分配列表
     */
    List<PartitionAllocation> getLatestPartitionAllocations();

    /**
     * 为 consumer 分配 partition
     *
     * @param partitionSet       待分配的 partition
     * @param onlineConsumerList online  consumer list
     * @return 分配结果
     */
    PartitionAllocation allocatePartitions(PartitionSet partitionSet, List<String> onlineConsumerList, String consumerGroup);

    /**
     * 更新分区分配信息
     *
     * @param newAllocation 新的分配信息
     * @param baseVersion   分配比对的版本号, 用于做乐观锁
     * @return 更新结果, 若返回 false, 有可能是乐观锁更新失败
     */
    boolean updatePartitionAllocation(PartitionAllocation newAllocation, int baseVersion);

    List<ClientMetaInfo> getOnlineExclusiveConsumers();

    List<ClientMetaInfo> getOnlineExclusiveConsumers(String subject, String consumerGroup);

}