package com.yhq.DO;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @Author: YHQ
 * @Date: 2020/12/19 13:59
 */
@Data
@Accessors(chain = true)
@TableName("Item")
public class ItemDO {
    private int id;
    private int detailId;
    private String title;
    private String thumb;
}
