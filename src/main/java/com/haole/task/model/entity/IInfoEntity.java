package com.haole.task.model.entity;

import java.util.Date;

/**
 * 常规带名字的实体。
 */
public interface IInfoEntity extends IIdEntity {
    /**
     * 名字
     */
    String getName();

    /**
     * 昵称
     */
    String getNickname();

    /**
     * 是否删除
     */
    Byte getDeleted();

    /**
     * 实体总有创建时间
     */
    Date getCreateTime();

    /**
     * 实体更新时间
     */
    Date getUpdateTime();
}
