package com.yintu.rixing.index;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: mlf
 * @Date: 2021/2/26 10:53:59
 * @Version: 1.0
 */
@RestController
@RequestMapping("/index/inx-local-and-remote-borrow-apply-and-inform")
@Api(tags = "本地借阅申请和远程借阅申请接口")
@ApiSupport(order = 3)
public class InxLocalAndRemoteBorrowApplyAndInformController {
}
