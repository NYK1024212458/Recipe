package com.ben.recipe.model;
import java.util.List;

public class CookCategory {

    private String parentId;
    private String name;
    private List<CategrgoryList> list;
    public void setParentId(String parentId) {
         this.parentId = parentId;
     }
     public String getParentId() {
         return parentId;
     }

    public void setName(String name) {
         this.name = name;
     }
     public String getName() {
         return name;
     }

    public void setCategrgoryList(List<CategrgoryList> list) {
         this.list = list;
     }
     public List<CategrgoryList> getList() {
         return list;
     }

}