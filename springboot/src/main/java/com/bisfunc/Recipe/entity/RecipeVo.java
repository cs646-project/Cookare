package com.bisfunc.Recipe.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RecipeVo{
    private Recipe recipe;

    private List<Ingredients> ingredients = new ArrayList<>();
}
