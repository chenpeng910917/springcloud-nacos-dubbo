package com.springcloud.mysql.aviator.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 规则配置详情
 *
 * @author chenpeng
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class RuleConfigDetailDO implements Serializable {
    private static final long serialVersionUID = 451636105250817368L;
    /**
    * 主键
    */
    private Long id;
    /**
    * 业务域类型(业务域:100-网约车、200-出租车(巡改网)、300-趣接单、400-城际、500-代驾、600-定制班线、700-车后)
    */
    private Integer businessType;
    /**
    * 租户id
    */
    private Long tenantId;
    /**
    * 发票规则详情id
    */
    private Long ruleId;
    /**
    * 字段类型(1:字符串、2:日期)
    */
    private Integer fieldType;
    /**
    * 运算符(contains:包含、==:等于、>:大于、<:小于)
    */
    private String operator;
    /**
    * 条件key
    */
    private String conditionKey;
    /**
    * 条件值
    */
    private String conditionValue;
    /**
    * 排序
    */
    private Integer sort;
    /**
    * 创建时间
    */
    private Date gmtCreate;
    /**
    * 修改时间
    */
    private Date gmtModified;
    /**
    * 创建人
    */
    private String createUser;
    /**
    * 创建人id
    */
    private String createUserId;
    /**
    * 修改人
    */
    private String modifiedUser;
    /**
    * 修改人id
    */
    private String modifiedUserId;

}
