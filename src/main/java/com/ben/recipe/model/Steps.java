package com.ben.recipe.model;

import java.io.Serializable;


public class Steps implements Serializable {

    private String img;
    private String step;
    public void setImg(String img) {
         this.img = img;
     }
     public String getImg() {
         return img;
     }

    public void setStep(String step) {
         this.step = step;
     }
     public String getStep() {
         return step;
     }

}