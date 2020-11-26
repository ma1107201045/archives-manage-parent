package com.yintu.rixing.demo;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author mlf
 * @since 2020-11-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "SysTest对象", description = "")
public class SysTest implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String createBy;

    private LocalDateTime createDate;

    private String name;

    private Integer age;

    private String email;


}
