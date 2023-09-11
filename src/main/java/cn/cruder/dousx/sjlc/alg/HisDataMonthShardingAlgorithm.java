package cn.cruder.dousx.sjlc.alg;

import cn.hutool.core.date.DateUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.sharding.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.RangeShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.StandardShardingAlgorithm;

import java.text.SimpleDateFormat;
import java.util.*;

@Getter
@Slf4j
public class HisDataMonthShardingAlgorithm implements StandardShardingAlgorithm<Date> {
    private static final String DEF_TABLE_LOWER_DATE = "2017_01";

    private final ThreadLocal<SimpleDateFormat> dateFormatThreadLocal = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy_MM"));

    private Properties props;

    private Date tableLowerDate;

    @Override
    public void init(Properties properties) {
        this.props = properties;
        String autoCreateTableLowerDate = properties.getProperty("auto-create-table-lower");
        try {
            this.tableLowerDate = dateFormatThreadLocal.get().parse(autoCreateTableLowerDate);
        } catch (Exception e) {
            log.error("parse auto-create table lower date failed: {}, use default date {}",
                    e.getMessage(), DEF_TABLE_LOWER_DATE);
            try {
                this.tableLowerDate = dateFormatThreadLocal.get().parse(DEF_TABLE_LOWER_DATE);
            } catch (Exception ignored) {
            }
        }
    }

    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Date> shardingValue) {
        Date value = shardingValue.getValue();
        // 根据精确值获取路由表
        String actuallyTableName = shardingValue.getLogicTableName() + shardingSuffix(value);
        if (availableTargetNames.contains(actuallyTableName)) {
            return actuallyTableName;
        }
        return null;
    }

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, RangeShardingValue<Date> shardingValue) {
        Date rangeLowerDate;
        if (shardingValue.getValueRange().hasLowerBound()) {
            rangeLowerDate = shardingValue.getValueRange().lowerEndpoint();
        } else {
            rangeLowerDate = tableLowerDate;
        }

        Date rangeUpperDate;
        if (shardingValue.getValueRange().hasUpperBound()) {
            rangeUpperDate = shardingValue.getValueRange().upperEndpoint();
        } else {
            rangeUpperDate = DateUtil.offsetMonth(new Date(), 1);
        }
        rangeUpperDate = DateUtil.endOfMonth(rangeUpperDate);
        List<String> tableNames = new ArrayList<>();
        while (rangeLowerDate.before(rangeUpperDate)) {
            String actuallyTableName = shardingValue.getLogicTableName() + shardingSuffix(rangeLowerDate);
            if (availableTargetNames.contains(actuallyTableName)) {
                tableNames.add(actuallyTableName);
            }
            rangeLowerDate = DateUtil.offsetMonth(rangeLowerDate, 1);
        }
        return tableNames;
    }

    private String shardingSuffix(Date shardingValue) {
        return "_" + dateFormatThreadLocal.get().format(shardingValue);
    }


    @Override
    public String getType() {
        return "HIS_DATA_SPI_BASED";
    }
}

