package com.leyou.item.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "tb_brand")
@Data
public class Brand {
    /**
     * `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '品牌id',
     * `name` varchar(50) NOT NULL COMMENT '品牌名称',
     * `image` varchar(200) DEFAULT '' COMMENT '品牌图片地址',
     * `letter` char(1) DEFAULT '' COMMENT '品牌的首字母',
     */
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;
    private String name;
    private String image;
    private Character letter;
}
