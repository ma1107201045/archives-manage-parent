package com.yintu.rixing.test.entity;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author mlf
 * @since 2020-11-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TbTest implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;


}
