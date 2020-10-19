package com.wuzhiaite.javaweb.common.authority.service;

import com.wuzhiaite.javaweb.base.entity.UserRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lpf
 * @since 2020-05-31
 */
public interface IUserRoleService extends IService<UserRole> {

    List<UserRole> getRoleList(String username);
}
