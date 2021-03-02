package com.leaves.gmall.model;

import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Author Chenweiwei
 * @Date 2021/2/25 18:20
 * @Version 1.0
 */
public class SearchParam  implements Serializable {


    private String catalog3Id;

    private String keyword;

    private List<String> valueId;


    public String getCatalog3Id() {
        return catalog3Id;
    }

    public void setCatalog3Id(String catalog3Id) {
        this.catalog3Id = catalog3Id;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public List<String> getValueId() {
        return valueId;
    }

    public void setValueId(List<String> valueId) {
        this.valueId = valueId;
    }
}
