package com.haole.task.model.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 为了限制分享卡片而引入的唯一值。
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NonceDTO extends Nonce {

    public void adapt() {
        setUpdateTime(null);
        setDeleted(null);
    }
}
