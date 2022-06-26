package com.bisfunc.Recipe.entity;

import lombok.Data;

@Data
public class RecipeDto {
    private Integer id;

    private String title;

    private String content;

    private String tags;

    private Integer updateUser;

    private String coverUrl;

    public static void SynDtoToRecipe(RecipeDto recipeDto, Recipe recipe) {
        if (recipeDto.getTitle() != null) recipe.setTitle(recipeDto.getTitle());
        if (recipeDto.getContent() != null) recipe.setContent(recipeDto.getContent());
        if (recipeDto.getTags() != null) recipe.setTags(recipeDto.getTags());
        if (recipeDto.getCoverUrl() != null) recipe.setCoverUrl(recipeDto.getCoverUrl());
    }
}
