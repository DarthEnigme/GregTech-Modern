package com.gregtechceu.gtceu.integration.kjs.recipe.components;

import com.gregtechceu.gtceu.api.capability.recipe.RecipeCapability;
import com.gregtechceu.gtceu.api.recipe.content.Content;

import com.mojang.serialization.Codec;
import dev.latvian.mods.kubejs.recipe.RecipeScriptContext;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponent;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponentType;
import dev.latvian.mods.kubejs.recipe.filter.RecipeMatchContext;
import dev.latvian.mods.kubejs.recipe.match.ReplacementMatchInfo;
import dev.latvian.mods.rhino.type.TypeInfo;

public record ContentJS<T>(RecipeComponent<T> baseComponent, RecipeCapability<?> capability)
        implements RecipeComponent<Content> {

    public static <T> ContentJS<T> create(RecipeComponent<T> baseComponent, RecipeCapability<?> capability) {
        return new ContentJS<>(baseComponent, capability);
    }

    @Override
    public RecipeComponentType<?> type() {
        return baseComponent.type();
    }

    @Override
    public Codec<Content> codec() {
        return Content.codec(capability);
    }

    @Override
    public TypeInfo typeInfo() {
        return TypeInfo.of(Content.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean matches(RecipeMatchContext cx, Content value, ReplacementMatchInfo match) {
        return baseComponent.matches(cx, (T) value.content, match);
    }

    @Override
    public Content replace(RecipeScriptContext cx, Content original, ReplacementMatchInfo match, Object with) {
        if (!matches(cx, original, match)) {
            return original;
        }
        return new Content(
                baseComponent.replace(cx, baseComponent.wrap(cx, original.content), match, with),
                original.chance, original.maxChance, original.tierChanceBoost);
    }

    @Override
    public String toString() {
        return baseComponent.toString() + "_content";
    }
}
