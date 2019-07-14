package com.leyou.item.web;

import com.leyou.common.vo.PageResult;
import com.leyou.item.pojo.Brand;
import com.leyou.item.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    /**
     * 获取分页数据 分页条件查询
     * Request URL: http://api.leyou.com/api/item/brand/page?key=&page=1&rows=5&sortBy=id&desc=false
     * Request Method: GET
     * 返回值 pageResult对象
     */
    @GetMapping("page")
    public ResponseEntity<PageResult<Brand>> queryBrandByPage(
            @RequestParam(name = "key", required = false) String key,
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "rows", defaultValue = "5") Integer rows,
            @RequestParam(name = "sortBy", required = false) String sortBy,
            @RequestParam(name = "desc", defaultValue = "false") Boolean desc
    ) {
        return ResponseEntity.ok(brandService.queryBrandByPage(page, rows, sortBy, desc, key));
    }

    @PostMapping
    public ResponseEntity<Void> saveBrand(
            Brand brand,
            @RequestParam("categories") List<Long> cids
    ) {
        brandService.saveBrand(brand,cids);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping
    public ResponseEntity<Void> updateBrand(
            Brand brand,
            @RequestParam("categories") List<Long> cids
    ){
        brandService.updateBrand(brand,cids);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteBrand(
            @PathVariable("id") Long bid
    ){
        brandService.deleteBrand(bid);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
