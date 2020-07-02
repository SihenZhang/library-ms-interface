package com.sihenzhang.library.app.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sihenzhang.library.app.entity.Category;
import com.sihenzhang.library.app.entity.CategoryVO;
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

    public IPage<CategoryVO> getAllCategories(Integer type, Integer current, Integer size) {
        var categoriesList = list();
        var categories = categoriesList.stream().map(CategoryVO::new).collect(Collectors.toList());
        var keyCategories = categories.stream().collect(Collectors.toMap(CategoryVO::getCatId, Function.identity()));
        var result = getTreeList(keyCategories, categories, type);
        IPage<CategoryVO> resultPage = new Page<>(current, size, result.size());
        resultPage.setRecords(result.stream().skip((current - 1) * size).limit(size).collect(Collectors.toList()));
        return resultPage;
    }

    private List<CategoryVO> getTreeList(Map<Long, CategoryVO> keyCategories, List<CategoryVO> categories, Integer type) {
        List<CategoryVO> result = new ArrayList<>();
        for (var key : keyCategories.keySet()) {
            var cat = keyCategories.get(key);
            if (isDelete(keyCategories, cat))
                continue;
            if (cat.getCatPid() == null)
                result.add(cat);
            else {
                if (cat.getCatLevel() >= type)
                    continue;
                var parentCat = keyCategories.get(cat.getCatPid());
                if (parentCat == null)
                    continue;
                if (parentCat.getChildren() == null)
                    parentCat.setChildren(new ArrayList<>());
                parentCat.getChildren().add(cat);
            }
        }
        return result;
    }

    private boolean isDelete(Map<Long, CategoryVO> keyCategories, CategoryVO cat) {
        if (cat.getCatPid() == null) { // 如果该分类的父分类 id 为空，即该分类为一级分类，则直接返回该分类是否被删除
            return cat.getCatDeleted();
        } else if(cat.getCatDeleted()) { // 如果该分类本身已经被删除，则直接返回 true
            return true;
        } else {
            var parentCat = keyCategories.get(cat.getCatPid());
            // 如果该分类既不是一级分类，又找不到其父分类，证明该分类有误，返回 true
            if(parentCat == null)
                return true;
            // 递归查找其父分类是否被删除
            return isDelete(keyCategories, parentCat);
        }
    }

}
