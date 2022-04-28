package com.springcloud.mysql.controller;

import com.github.pagehelper.PageInfo;
import com.springcloud.mysql.common.ServiceResponse;
import com.springcloud.mysql.model.vo.UserVO;
import com.springcloud.mysql.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * (User)表控制层
 *
 * @author chenpeng
 * @since 2022-04-28 16:21:02
 */
@RestController
@RequestMapping("user")
public class UserController {
    /**
     * 服务对象
     */
    @Resource
    private UserService userService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @RequestMapping(value = "/selectOne")
    public ServiceResponse<UserVO> selectOne(Long id) {
        return this.userService.queryById(id);
    }

    /**
     *
     * 127.0.0.1:10804/queryAllByLimit?offset=0&limit=10
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @RequestMapping(value = "/queryAllByLimit")
    public ServiceResponse<PageInfo<UserVO>> queryAllByLimit(int offset, int limit) {
        return this.userService.queryAllByLimit(offset, limit);
    }

}
