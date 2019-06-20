package com.sen.framework.common.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.sen.framework.common.bean.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author Evan Huang
 * @since 2019-01-09
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("common_dict_detail")
public class DictDetail extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 自增ID
     */
    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    @TableField("DICT_ID")
    private Integer dictId;

    /**
     * 字典Key
     */
    @TableField("DICT_KEY")
    private String dictKey;

    /**
     * 字典值
     */
    @TableField("DICT_VALUE")
    private String dictValue;

    /**
     * 字典中文值
     */
    @TableField("CN_VALUE")
    private String cnValue;

    /**
     * 字典英文值
     */
    @TableField("EN_VALUE")
    private String enValue;


}
