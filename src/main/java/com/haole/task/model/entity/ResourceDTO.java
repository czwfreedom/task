package com.haole.task.model.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * 资源。
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ResourceDTO extends Resource {

    /**
     * 资源的后缀。
     */
    private String postfix;

    /**
     * 阿里云上传。
     */
    private OSSUpload upload;

    @Override
    public void adapt() {
        super.adapt();
        this.setCreator(null);
        this.setChecked(null);
        if (upload != null && getPath() != null) {
            setPath(null);
        }
    }

    /**
     * 阿里云上传需要的参数。
     */
    @Data
    public static class OSSUpload {
        /**
         * oss上传的url (bucket + endpoint)
         */
        private String url;
        /**
         * objectKey
         */
        private String key;

        /**
         * OSSAccessKeyId
         */
        private String keyId;

        /**
         * policy
         */
        private String policy;

        /**
         * signature
         */
        private String signature;
    }
}
