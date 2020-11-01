package com.xfd.common.dynamic.model;

import java.io.Serializable;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;
import lombok.Setter;

/**
 * 抽象DO
 * @author xfd
 *
 * 2020年10月31日
 */
@Getter
@Setter
public abstract class AbstractDO implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	/** 主键, 默认唯一标识 */
	@TableId(value = "id", type = IdType.AUTO)
	private Long id;
	
	/** 删除标识, com.xfd.common.dynamic.model.enums.DelFlagEnum */
	@TableField("del_flag")
	private Integer delFlag;
	
	/** 创建人 */
	@TableField("create_by")
	private Long createBy;
	
	/** 创建时间 */
	@TableField(value = "craete_date", fill = FieldFill.INSERT)
	private Date createDate;
	
	/** 修改人 */
	@TableField("modify_by")
	private Long modifyBy;
	
	/** 修改时间 */
	@TableField(value = "modify_date", fill = FieldFill.INSERT_UPDATE)
	private Date modifyDate;
	
}
