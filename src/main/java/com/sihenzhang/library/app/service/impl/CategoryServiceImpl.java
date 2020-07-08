package com.sihenzhang.library.app.service.impl;

import cn.hutool.core.lang.Dict;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sihenzhang.library.app.entity.Category;
import com.sihenzhang.library.app.mapper.CategoryMapper;
import com.sihenzhang.library.app.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    public Dict getAllCategories(Integer level, Integer current, Integer size) {
        var categories = list(Wrappers.<Category>lambdaQuery().orderByAsc(Category::getCatSymbol));
        var keyCategories = categories.stream().collect(Collectors.toMap(Category::getCatId, Function.identity()));
        var categoriesResult = getTreeList(keyCategories, categories, level);
        var result = Dict.create();
        result.set("total", categoriesResult.size());
        result.set("current", current);
        if (size != null) {
            result.set("categories", categoriesResult.stream().skip((current - 1) * size).limit(size).collect(Collectors.toList()));
        } else {
            result.set("categories", categoriesResult);
        }
        return result;
    }

    private List<Category> getTreeList(Map<Long, Category> keyCategories, List<Category> categories, Integer level) {
        List<Category> result = new ArrayList<>();
        for (var cat : categories) {
            // 跳过被删除的分类
            if (isDelete(keyCategories, cat))
                continue;
            // 如果该分类的父级分类 id 为空，即该分类为一级分类，则直接将该分类放入结果列表中
            if (cat.getCatPid() == null)
                result.add(cat);
            else {
                // 跳过不在查询范围内的分类
                if (cat.getCatLevel() >= level)
                    continue;
                // 查询该分类的父级分类
                var parentCat = keyCategories.get(cat.getCatPid());
                // 如果该分类既不是一级分类，又找不到其父级分类，证明该分类有误，直接跳过该分类
                if (parentCat == null)
                    continue;
                // 如果该分类的父级分类的 children 字段为空，则新建一个空列表
                if (parentCat.getChildren() == null)
                    parentCat.setChildren(new ArrayList<>());
                // 将该分类放入其父级分类的 children 列表中
                parentCat.getChildren().add(cat);
            }
        }
        return result;
    }

    private boolean isDelete(Map<Long, Category> keyCategories, Category cat) {
        if (cat.getCatPid() == null) { // 如果该分类的父级分类 id 为空，即该分类为一级分类，则直接返回该分类是否被删除
            return cat.getCatDeleted() != null;
        } else if(cat.getCatDeleted() != null) { // 如果该分类本身已经被删除，则直接返回 true
            return true;
        } else {
            var parentCat = keyCategories.get(cat.getCatPid());
            // 如果该分类既不是一级分类，又找不到其父级分类，证明该分类有误，返回 true
            if(parentCat == null)
                return true;
            // 递归查找其父分类是否被删除
            return isDelete(keyCategories, parentCat);
        }
    }

}
