package com.common.handler;

import cn.dev33.satoken.stp.StpUtil;
import com.common.constants.Constants;
import com.common.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * mybatis-plus自动填充
 */
@Slf4j
@Component
public class CMetaObjectHandler implements com.baomidou.mybatisplus.core.handlers.MetaObjectHandler {

    private String CREAT_USER = "createUser";
    private String CREAT_TIME = "createTime";
    private String UPDATE_USER = "updateUser";
    private String UPDATE_TIME = "updateTime";

    private String DELETE_FLG = "deleteFlg";

    /**
     * 自动填充CREAT_USER、CREAT_TIME、删除FLG
     *
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        Long userId = 0l;
        if (StpUtil.isLogin()) {
            userId = StpUtil.getLoginIdAsLong();
        }
        this.strictInsertFill(metaObject, CREAT_USER, Long.class, userId);
        this.strictInsertFill(metaObject, CREAT_TIME, Date.class, DateUtils.currentDate());
        this.strictUpdateFill(metaObject, UPDATE_USER, Long.class, userId);
        this.strictUpdateFill(metaObject, UPDATE_TIME, Date.class, DateUtils.currentDate());
        this.strictInsertFill(metaObject, DELETE_FLG, Integer.class, Constants.DELETE_FLG_FALSE);
    }

    /**
     * 自动填充UPDATE_USER和UPDATE_TIME
     *
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        Long userId = StpUtil.getLoginIdAsLong();
        this.strictUpdateFill(metaObject, UPDATE_USER, Long.class, userId);
        this.strictUpdateFill(metaObject, UPDATE_TIME, Date.class, DateUtils.currentDate());
    }

}
