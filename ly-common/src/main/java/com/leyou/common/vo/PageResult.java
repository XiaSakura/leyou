package com.leyou.common.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResult<T> {
    private Long total; //总条数
    private List<T> items;//当前页的数据
    private Long totalPage;//总页数

    public PageResult(Long totalPage,List<T> items){
        this.totalPage=totalPage;
        this.items=items;
    }
}
