package com.yhq.common.execption;

import com.yhq.common.execption.vo.ExecptionResultVo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

/**
 * @Author: YHQ
 * @Date: 2020/5/30 11:54
 */
@RestControllerAdvice
public class SystemExecptionAdvice {
    @ExceptionHandler
    public ResponseEntity<ExecptionResultVo> systemExecptionAdvice(SystemExecption e){
        ExecptionEnum execptionEnum = e.getExecptionEnum();
        ExecptionResultVo erv = new ExecptionResultVo(execptionEnum.getStatusCode(),execptionEnum.getMsg(),new Date());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erv);
    }
}
