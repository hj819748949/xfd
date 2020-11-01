package com.xfd.system.server.user.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.xfd.common.dynamic.model.AbstractDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户实体对象
 * @author xfd
 *
 * 2020年10月31日
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("xfd_user")
public class UserDO extends AbstractDO
{
	private static final long serialVersionUID = -3545194556152535378L;
	
	
}
