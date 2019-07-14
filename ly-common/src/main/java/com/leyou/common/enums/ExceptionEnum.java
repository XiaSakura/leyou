package com.leyou.common.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 定义一个枚举 来用于返回信息 因为每个人写的信息都不同 所以这里需要去规定
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum  ExceptionEnum {

    INVALID_FILE_TYPE(400,"无效的文件类型"),
    UPLOAD_FILE_ERROR(500,"文件上传失败"),
    PRICE_CANNOT_BE_NULL(400,"价格不能为空!!!"),
    CATEGORY_NOT_FOUND(404,"该商品分类没找到"),
    CATEGORY_SAVE_FAIL(404,"商品分类保存失败"),
    CATEGORY_UPDATE_FAIL(404,"商品分类更新失败"),
    CATEGORY_DELETE_FAIL(404,"商品分类删除失败"),
    BRAND_NOT_FOUND(404,"品牌没查找到"),
    BRAND_SAVE_FAIL(500,"新增品牌失败"),
    BRAND_UPDATE_FAIL(500,"修改品牌失败"),
    BRAND_DELETE_FAIL(500,"删除品牌失败"),
    ;
    private int stauts;
    private String msg;

}
