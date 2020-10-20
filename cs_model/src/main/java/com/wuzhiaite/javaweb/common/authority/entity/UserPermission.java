package com.wuzhiaite.javaweb.common.authority.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.wuzhiaite.javaweb.base.entity.TreeEntity;
import io.swagger.annotations.ApiModel;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
* <p>
* 
* </p>
*
* @author lpf
* @since 2020-06-08
*/
@Data
@Builder
@ToString
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="UserPermission对象", description="")
@AllArgsConstructor
@NoArgsConstructor
public class UserPermission  implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id ;

    private String userId;

    private String menuId;

    private Boolean canAdd;

    private Boolean canEdit;

    private Boolean canDelete;

    private Date createTime;

    private String createUser;

    private Date updateTime;

    private String updateUser;

}
