package com.yintu.rixing.system;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yintu.rixing.BaseEntity;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author mlf
 * @since 2020-12-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_message")
@ApiModel(value="SysMessage对象", description="信息表")
public class SysMessage extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "年度")
    @TableField("niandu")
    private String niandu;

    @ApiModelProperty(value = "父id")
    @TableField("pid")
    private String pid;

    @ApiModelProperty(value = "状态")
    @TableField("status")
    private String status;

    @ApiModelProperty(value = "分类号")
    @TableField("feileihao")
    private String feileihao;

    @ApiModelProperty(value = "实体分类号")
    @TableField("entityflh")
    private String entityflh;

    @ApiModelProperty(value = "保管期限")
    @TableField("storagelife")
    private String storagelife;

    @ApiModelProperty(value = "案卷号")
    @TableField("anjuanhao")
    private Integer anjuanhao;

    @ApiModelProperty(value = "档号")
    @TableField("danghao")
    private String danghao;

    @ApiModelProperty(value = "题名")
    @TableField("timing")
    private String timing;

    @ApiModelProperty(value = "输入员")
    @TableField("shuruyuan")
    private String shuruyuan;

    @ApiModelProperty(value = "责任者")
    @TableField("zerenzhe")
    private String zerenzhe;

    @ApiModelProperty(value = "归档单位")
    @TableField("gddw")
    private String gddw;

    @ApiModelProperty(value = "开起日期")
    @TableField("starttime")
    private String starttime;

    @ApiModelProperty(value = "截止日期")
    @TableField("stoptime")
    private String stoptime;

    @ApiModelProperty(value = "归档标志")
    @TableField("Archivelogo")
    private String archivelogo;

    @ApiModelProperty(value = "副题名")
    @TableField("futiming")
    private String futiming;

    @ApiModelProperty(value = "更新时间")
    @TableField("updatetime")
    private Date updatetime;

    @ApiModelProperty(value = "总件数")
    @TableField("totaljnumber")
    private Integer totaljnumber;

    @ApiModelProperty(value = "总页数")
    @TableField("totalyenumber")
    private Integer totalyenumber;

    @ApiModelProperty(value = "存址")
    @TableField("cunzhi")
    private String cunzhi;

    @ApiModelProperty(value = "载体类型")
    @TableField("zaitileixing")
    private String zaitileixing;

    @ApiModelProperty(value = "立卷时间")
    @TableField("lijuantime")
    private Date lijuantime;

    @ApiModelProperty(value = "文种")
    @TableField("wenzhong")
    private String wenzhong;

    @ApiModelProperty(value = "备注")
    @TableField("beizhu")
    private String beizhu;

    @ApiModelProperty(value = "接收人")
    @TableField("jieshouren")
    private String jieshouren;

    @ApiModelProperty(value = "接收时间")
    @TableField("jieshoutime")
    private String jieshoutime;


}
