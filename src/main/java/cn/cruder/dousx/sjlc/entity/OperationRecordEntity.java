package cn.cruder.dousx.sjlc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Accessors(chain = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_operation_record")
public class OperationRecordEntity {


    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    @TableField
    private Integer operation;


    @TableField
    private Date operationTime;


}
