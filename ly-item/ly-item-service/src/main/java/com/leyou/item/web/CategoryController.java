package com.leyou.item.web;

import com.leyou.item.pojo.Category;
import com.leyou.item.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("list")
    public ResponseEntity<List<Category>> queryByParentId(
            @RequestParam(name = "pid", defaultValue = "0") Long pid
    ) {
        return ResponseEntity.ok(categoryService.queryByParentId(pid));
    }

    @PostMapping("save")
    public ResponseEntity<Void> saveCategory(
            @RequestBody Category category
    ){
        categoryService.saveCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("update")
    public ResponseEntity<Void> updateCategory(
            @RequestBody Category category
    ){
        categoryService.updateCategory(category);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("delete")
    public ResponseEntity<Void> deleteCaregory(
            @RequestParam(name = "id") Long id
    ){
        categoryService.deleteCategory(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
