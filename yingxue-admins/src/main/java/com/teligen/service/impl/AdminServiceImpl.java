package com.teligen.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.teligen.entity.Admin;
import com.teligen.dao.AdminDao;
import com.teligen.service.AdminService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;

/**
 * (Admin)表服务实现类
 *
 * @author makejava
 * @since 2023-03-12 22:28:23
 */
@Service("adminService")
@Transactional
public class AdminServiceImpl implements AdminService {
    @Resource
    private AdminDao adminDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Admin queryById(Integer id) {
        return this.adminDao.queryById(id);
    }

    /**
     * 新增数据
     *
     * @param admin 实例对象
     * @return 实例对象
     */
    @Override
    public Admin insert(Admin admin) {
        this.adminDao.insert(admin);
        return admin;
    }

    /**
     * 修改数据
     *
     * @param admin 实例对象
     * @return 实例对象
     */
    @Override
    public Admin update(Admin admin) {
        this.adminDao.update(admin);
        return this.queryById(admin.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.adminDao.deleteById(id) > 0;
    }

    @Override
    public Admin login(Admin admin) {
        //1.根据用户名查找用户
        String username = admin.getUsername();
        Admin adminDB = adminDao.findByUserName(username);

         if(ObjectUtils.isEmpty(adminDB)) throw  new RuntimeException("用户名不存在!");

        String password = DigestUtils.md5DigestAsHex(admin.getPassword().getBytes(StandardCharsets.UTF_8));

        //判断密码是否正确
        if(!StringUtils.equals(password,adminDB.getPassword() ))throw new RuntimeException("密码错误!");

        //如果正确返回用户信息
        return adminDB;
    }
}
