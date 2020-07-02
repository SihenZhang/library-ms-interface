package com.sihenzhang.library.app.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryVO implements Serializable {

    private static final long serialVersionUID = 8219116226207251066L;

    private Long catId;

    private String catSymbol;

    private String catName;

    private Long catPid;

    private Integer catLevel;

    private Boolean catDeleted;

    private List<CategoryVO> children;

    public CategoryVO(Category category) {
        this(category.getCatId(), category.getCatSymbol(), category.getCatName(), category.getCatPid(), category.getCatLevel(), category.getCatDeleted(), null);
    }

}
