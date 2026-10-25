package com.gregtechceu.gtceu.integration.kjs.recipe.components;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.addon.AddonFinder;
import com.gregtechceu.gtceu.api.addon.events.KJSRecipeKeyEvent;
import com.gregtechceu.gtceu.api.capability.recipe.*;
import com.gregtechceu.gtceu.api.recipe.chance.logic.ChanceLogic;
import com.gregtechceu.gtceu.api.recipe.condition.RecipeCondition;
import com.gregtechceu.gtceu.api.recipe.ingredient.EnergyStack;
import com.gregtechceu.gtceu.api.registry.GTRegistries;
import com.gregtechceu.gtceu.data.recipe.GTRecipeCapabilities;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.crafting.SizedIngredient;
import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;

import com.mojang.serialization.Codec;
import dev.latvian.mods.kubejs.recipe.component.*;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponent;
import dev.latvian.mods.rhino.type.TypeInfo;

import java.util.*;

public class GTRecipeComponents {

    public static final RecipeComponent<CompoundTag> TAG = RecipeComponentType
            .<CompoundTag>unit(GTCEu.id("tag"), type -> new RecipeComponent<>() {

                @Override
                public RecipeComponentType<?> type() {
                    return type;
                }

                @Override
                public Codec<CompoundTag> codec() {
                    return CompoundTag.CODEC;
                }

                @Override
                public TypeInfo typeInfo() {
                    return TypeInfo.RAW_MAP;
                }

                @Override
                public String toString() {
                    return "tag";
                }
            }).instance();

    public static final RecipeComponent<ResourceLocation> RESOURCE_LOCATION = RecipeComponentType
            .<ResourceLocation>unit(GTCEu.id("resource_location"), type -> new RecipeComponent<>() {

                @Override
                public RecipeComponentType<?> type() {
                    return type;
                }

                @Override
                public Codec<ResourceLocation> codec() {
                    return ResourceLocation.CODEC;
                }

                @Override
                public TypeInfo typeInfo() {
                    return TypeInfo.STRING;
                }

                @Override
                public String toString() {
                    return "resource_location";
                }
            }).instance();

    public static final RecipeComponent<RecipeCapability<?>> RECIPE_CAPABILITY = RecipeComponentType
            .<RecipeCapability<?>>unit(GTCEu.id("recipe_capability"), type -> new RecipeComponent<>() {

                @Override
                public RecipeComponentType<?> type() {
                    return type;
                }

                @Override
                public Codec<RecipeCapability<?>> codec() {
                    return RecipeCapability.DIRECT_CODEC;
                }

                @Override
                public TypeInfo typeInfo() {
                    return TypeInfo.of(RecipeCapability.class);
                }

                @Override
                public String toString() {
                    return "recipe_capability";
                }
            }).instance();

    public static final RecipeComponent<ChanceLogic> CHANCE_LOGIC = RecipeComponentType
            .<ChanceLogic>unit(GTCEu.id("chance_logic"), type -> new RecipeComponent<>() {

                @Override
                public RecipeComponentType<?> type() {
                    return type;
                }

                @Override
                public Codec<ChanceLogic> codec() {
                    return GTRegistries.CHANCE_LOGICS.byNameCodec();
                }

                @Override
                public TypeInfo typeInfo() {
                    return TypeInfo.of(ChanceLogic.class);
                }

                @Override
                public String toString() {
                    return "chance_logic";
                }
            }).instance();

    public static final RecipeComponent<RecipeCondition<?>> RECIPE_CONDITION = RecipeComponentType
            .<RecipeCondition<?>>unit(GTCEu.id("recipe_condition"), type -> new RecipeComponent<>() {

                @Override
                public RecipeComponentType<?> type() {
                    return type;
                }

                @Override
                public Codec<RecipeCondition<?>> codec() {
                    return RecipeCondition.CODEC;
                }

                @Override
                public TypeInfo typeInfo() {
                    return TypeInfo.of(RecipeCondition.class);
                }

                @Override
                public String toString() {
                    return "recipe_condition";
                }
            }).instance();

    public static final RecipeComponent<EnergyStack.WithIO> ENERGY_STACK = RecipeComponentType
            .<EnergyStack.WithIO>unit(GTCEu.id("energy_stack"), type -> new RecipeComponent<>() {

                @Override
                public RecipeComponentType<?> type() {
                    return type;
                }

                @Override
                public Codec<EnergyStack.WithIO> codec() {
                    return EnergyStack.WithIO.CODEC;
                }

                @Override
                public TypeInfo typeInfo() {
                    return TypeInfo.of(EnergyStack.WithIO.class)
                            .or(TypeInfo.of(EnergyStack.class))
                            .or(TypeInfo.PRIMITIVE_LONG)
                            .or(TypeInfo.LONG);
                }

                @Override
                public String toString() {
                    return "energy_stack";
                }
            }).instance();

    public static final ContentJS<SizedIngredient> ITEM = ContentJS.create(
            SizedIngredientComponent.SIZED_INGREDIENT.instance(), GTRecipeCapabilities.ITEM);
    public static final ContentJS<SizedFluidIngredient> FLUID = ContentJS.create(
            SizedFluidIngredientComponent.NESTED.instance(), GTRecipeCapabilities.FLUID);
    public static final ContentJS<EnergyStack.WithIO> EU = ContentJS.create(ENERGY_STACK, GTRecipeCapabilities.EU);
    public static final ContentJS<Integer> CWU = ContentJS.create(NumberComponent.NON_NEGATIVE_INT.instance(),
            GTRecipeCapabilities.CWU);

    public static final RecipeComponent<Map<RecipeCapability<?>, ChanceLogic>> CHANCE_LOGIC_MAP = new JavaMapRecipeComponent<>(
            RECIPE_CAPABILITY, CHANCE_LOGIC);

    /**
     * First in pair is in, second is out
     */
    public static final Map<RecipeCapability<?>, ContentJS<?>> VALID_CAPS = new IdentityHashMap<>();

    static {
        VALID_CAPS.put(GTRecipeCapabilities.ITEM, ITEM);
        VALID_CAPS.put(GTRecipeCapabilities.FLUID, FLUID);
        VALID_CAPS.put(GTRecipeCapabilities.EU, EU);
        VALID_CAPS.put(GTRecipeCapabilities.CWU, CWU);

        KJSRecipeKeyEvent event = new KJSRecipeKeyEvent();
        AddonFinder.getAddonList().forEach(addon -> addon.registerRecipeKeys(event));
        VALID_CAPS.putAll(event.getRegisteredKeys());
    }
}
