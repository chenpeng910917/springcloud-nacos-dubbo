package com.springcloud.mysql.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.springcloud.mysql.common.ServiceResponse;
import com.springcloud.mysql.common.exception.ExceptionCodeEnum;
import com.springcloud.mysql.common.exception.SpringException;
import com.springcloud.mysql.dao.UserDao;
import com.springcloud.mysql.dao.domain.UserDO;
import com.springcloud.mysql.model.param.UserParam;
import com.springcloud.mysql.model.vo.UserVO;
import com.springcloud.mysql.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * (User)表服务实现类
 *
 * @author chenpeng
 * @since 2022-04-28 16:21:02
 */
@Slf4j
@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public ServiceResponse<UserVO> queryById(Long id) {
        try {
            UserVO userVO = new UserVO();
            UserDO user = this.userDao.queryById(id);
            BeanUtils.copyProperties(user, userVO);
            return ServiceResponse.buildSuccessResponse(userVO);
        } catch (SpringException e) {
            log.info("查询业务异常,msg={},code={}", e.getErrorCode(), e.getErrorMsg());
            return ServiceResponse.buildErrorResponse(e.getErrorCode(), e.getErrorMsg(), null);
        } catch (Exception e) {
            log.error("查询程序异常,", e);
            return ServiceResponse.buildErrorResponse(ExceptionCodeEnum.ERROR.getCode(),
                    ExceptionCodeEnum.ERROR.getMsg(), null);
        }
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public ServiceResponse<PageInfo<UserVO>> queryAllByLimit(int offset, int limit) {
        try {
            Page<UserVO> page = PageHelper.startPage(offset, limit, true);
            List<UserDO> list = this.userDao.queryAllByLimit(offset, limit);
            List<UserVO> result = list.stream().map(l -> {
                UserVO userVO = new UserVO();
                BeanUtils.copyProperties(l, userVO);
                return userVO;
            }).collect(Collectors.toList());

            PageInfo<UserVO> pageInfo = new PageInfo<>(result);
            pageInfo.setTotal(page.getTotal());
            return ServiceResponse.buildSuccessResponse(pageInfo);
        } catch (SpringException e) {
            log.info("查询业务异常,msg={},code={}", e.getErrorCode(), e.getErrorMsg());
            return ServiceResponse.buildErrorResponse(e.getErrorCode(), e.getErrorMsg(), null);
        } catch (Exception e) {
            log.error("查询程序异常,", e);
            return ServiceResponse.buildErrorResponse(ExceptionCodeEnum.ERROR.getCode(),
                    ExceptionCodeEnum.ERROR.getMsg(), null);
        }
    }

    /**
     * 新增数据
     *
     * @param userParam 实例对象
     * @return 实例对象
     */
    @Override
    public ServiceResponse<Integer> insert(UserParam userParam) {
        try {
            UserDO userDO = new UserDO();
            BeanUtils.copyProperties(userParam, userDO);
            return ServiceResponse.buildSuccessResponse(this.userDao.insert(userDO));
        } catch (SpringException e) {
            log.info("新增业务异常,msg={},code={}", e.getErrorCode(), e.getErrorMsg());
            return ServiceResponse.buildErrorResponse(e.getErrorCode(), e.getErrorMsg(), null);
        } catch (Exception e) {
            log.error("新增程序异常,", e);
            return ServiceResponse.buildErrorResponse(ExceptionCodeEnum.ERROR.getCode(),
                    ExceptionCodeEnum.ERROR.getMsg(), null);
        }
    }

    /**
     * 修改数据
     *
     * @param userParam 实例对象
     * @return 实例对象
     */
    @Override
    public ServiceResponse<Integer> update(UserParam userParam) {
        try {
            UserDO userDO = new UserDO();
            BeanUtils.copyProperties(userParam, userDO);
            return ServiceResponse.buildSuccessResponse(this.userDao.update(userDO));
        } catch (SpringException e) {
            log.info("修改业务异常,msg={},code={}", e.getErrorCode(), e.getErrorMsg());
            return ServiceResponse.buildErrorResponse(e.getErrorCode(), e.getErrorMsg(), null);
        } catch (Exception e) {
            log.error("修改程序异常,", e);
            return ServiceResponse.buildErrorResponse(ExceptionCodeEnum.ERROR.getCode(),
                    ExceptionCodeEnum.ERROR.getMsg(), null);
        }
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public ServiceResponse<Boolean> deleteById(Long id) {
        try {
            return ServiceResponse.buildSuccessResponse(this.userDao.deleteById(id) > 0);
        } catch (SpringException e) {
            log.info("删除业务异常,msg={},code={}", e.getErrorCode(), e.getErrorMsg());
            return ServiceResponse.buildErrorResponse(e.getErrorCode(), e.getErrorMsg(), null);
        } catch (Exception e) {
            log.error("删除程序异常,", e);
            return ServiceResponse.buildErrorResponse(ExceptionCodeEnum.ERROR.getCode(),
                    ExceptionCodeEnum.ERROR.getMsg(), null);
        }
    }
}
