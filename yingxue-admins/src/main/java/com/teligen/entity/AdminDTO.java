package com.teligen.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liangtao
 * @date 2023-03-13 00:30
 * @desc: 用户信息返回给前端的数据
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminDTO {
    private String username;

    private String avatar;
}
