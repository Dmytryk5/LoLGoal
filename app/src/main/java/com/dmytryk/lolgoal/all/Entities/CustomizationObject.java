package com.dmytryk.lolgoal.all.Entities;

/**
 * Customization object (skins)
 */

public class CustomizationObject {

    private String category;
    private String content;

    public CustomizationObject(String category, String content){
        this.category = category;
        this.content = content;
    }


    public String getCategory() {
        return category;
    }

    public String getContent() {
        return content;
    }


}
