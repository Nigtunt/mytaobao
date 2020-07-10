package com.yhq.common.execption;

/**
 * @Author: YHQ
 * @Date: 2020/5/30 12:25
 */
public class SystemExecption extends RuntimeException {
    private ExecptionEnum execptionEnum;

    public SystemExecption(ExecptionEnum execptionEnum) {
        this.execptionEnum = execptionEnum;
    }

    public ExecptionEnum getExecptionEnum() {
        return execptionEnum;
    }

    public void setExecptionEnum(ExecptionEnum execptionEnum) {
        this.execptionEnum = execptionEnum;
    }
}
