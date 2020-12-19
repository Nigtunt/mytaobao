package com.yhq.DO;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author: YHQ
 * @Date: 2020/12/19 14:17
 */
@Data
@Accessors(chain = true)
@TableName("detail")
public class DetailDO {
    private int id;
    private String content;
}
