package com.xfd.comon.statics.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 抽象VO
 * @author xfd
 *
 * 2020年10月31日
 */
@Data
public abstract class AbstractVO implements Serializable
{
	private static final long serialVersionUID = 2270016887008897165L;
	
	/** 主键 */
	private Long id;
	
	
}
