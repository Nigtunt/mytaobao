package com.yhq.search.dao;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yhq.search.bean.Spu;
import org.springframework.stereotype.Repository;

/**
 * @Author: YHQ
 * @Date: 2020/7/8 18:23
 */
@TableName(schema = "tb_spu",value = "tb_spu")

@Repository
public interface SpuMapper extends BaseMapper<Spu> {
}
