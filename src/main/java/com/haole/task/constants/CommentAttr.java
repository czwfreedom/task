package com.haole.task.constants;

/**
 * 把点赞、评论放到一个attrs中，方便查询。
 */
public interface CommentAttr {

    /**
     * 点赞
     */
    Byte LIKE = 1;

    /**
     * 评论
     */
    Byte COMMENT = 2;


    Byte ALL = (byte) (LIKE | COMMENT);
}
