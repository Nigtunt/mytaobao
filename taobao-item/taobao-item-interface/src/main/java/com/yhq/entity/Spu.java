package com.yhq.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: YHQ
 * @Date: 2020/6/19 14:07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Spu implements Serializable {
    private Long id;
    private String title;
    private String subTitle;
    private Long cid1;
    private Long cid2;
    private Long cid3;
    private Long brandId;
    private boolean saleable;
    private boolean valid;
    @JsonIgnore
    private Date createTime;
    @JsonIgnore
    private Date lastUpdateTime;
    private String cName;
    private String bName;
}
