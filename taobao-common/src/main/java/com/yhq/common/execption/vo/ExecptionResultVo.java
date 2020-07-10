package com.yhq.common.execption.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author: YHQ
 * @Date: 2020/5/30 12:26
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ExecptionResultVo {
    private int code;
    private String msg;
    private Date date;
}
