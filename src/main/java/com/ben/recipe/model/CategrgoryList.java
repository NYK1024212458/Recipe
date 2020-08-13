package com.ben.recipe.model;

/**
 * 类别列表
 */
public class CategrgoryList {

    private String id;
    private String name;
    private String parentId;
    public void setId(String id) {
         this.id = id;
     }
     public String getId() {
         return id;
     }

    public void setName(String name) {
         this.name = name;
     }
     public String getName() {
         return name;
     }

    public void setParentId(String parentId) {
         this.parentId = parentId;
     }
     public String getParentId() {
         return parentId;
     }

}