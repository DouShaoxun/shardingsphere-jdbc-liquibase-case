package cn.cruder.dousx.sjlc;


import cn.cruder.dousx.sjlc.entity.OperationRecordEntity;
import cn.cruder.dousx.sjlc.mapper.OperationRecordMapper;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Slf4j
@SpringBootTest
class OperationRecordTests {
    @Autowired
    OperationRecordMapper recordMapper;

    @Test
    void insertTest() {
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            OperationRecordEntity entity = new OperationRecordEntity();
            entity.setOperation(1);
            int year = 2023 + random.nextInt(2);
            int month = random.nextInt(12);
            int day = 1 + random.nextInt(25);
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, day);
            entity.setOperationTime(calendar.getTime());
            recordMapper.insert(entity);
        }
        log.info("done...");
    }

    @Test
    void selectTest() {
        QueryWrapper<OperationRecordEntity> queryWrapper = new QueryWrapper<>();
        Date startTime = DateUtil.parse("2023-09-01").toJdkDate();
        Date endTime = DateUtil.parse("2023-10-28").toJdkDate();
        queryWrapper.between("operation_time", startTime, endTime);
        List<OperationRecordEntity> selectList = recordMapper.selectList(queryWrapper);
        log.info("{}", CollUtil.size(selectList));
    }


}
