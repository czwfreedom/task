package com.haole.task.model.entity;

import java.util.Date;

/**
 * 约定数据库行必有的字段。
 */
public interface IDBEntity extends IIdEntity {
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
