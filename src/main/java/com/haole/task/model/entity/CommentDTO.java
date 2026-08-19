package com.haole.task.model.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * 评论。
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class CommentDTO extends Comment {
}
