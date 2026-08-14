package com.haole.task.model.entity;

/**
 * 常规带名字的实体。
 */
public interface IInfoEntity extends IDBEntity {
    /**
     * 名字
     */
    String getName();

    /**
     * 昵称
     */
    String getNickname();
}
