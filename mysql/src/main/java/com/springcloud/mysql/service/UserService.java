package com.springcloud.mysql.service;

import com.github.pagehelper.PageInfo;
import com.springcloud.mysql.common.ServiceResponse;
import com.springcloud.mysql.model.param.UserParam;
import com.springcloud.mysql.model.vo.UserVO;

/**
 * (User)表服务接口
 *
 * @author chenpeng
 * @since 2022-04-28 16:21:02
 */
public interface UserService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ServiceResponse<UserVO> queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    ServiceResponse<PageInfo<UserVO>> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    ServiceResponse<Integer> insert(UserParam user);

    /**
     * 修改数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    ServiceResponse<Integer> update(UserParam user);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    ServiceResponse<Boolean> deleteById(Long id);

}
