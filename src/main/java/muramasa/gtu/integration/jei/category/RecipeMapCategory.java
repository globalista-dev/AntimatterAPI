package muramasa.gtu.integration.jei.category;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.*;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import muramasa.gtu.api.gui.GuiData;
import muramasa.gtu.api.gui.SlotData;
import muramasa.gtu.api.gui.SlotType;
import muramasa.gtu.api.machines.MachineStack;
import muramasa.gtu.api.machines.Tier;
import muramasa.gtu.api.recipe.RecipeMap;
import muramasa.gtu.api.util.int4;
import muramasa.gtu.Ref;
import muramasa.gtu.common.Data;
import muramasa.gtu.integration.jei.renderer.FluidStackRenderer;
import muramasa.gtu.integration.jei.wrapper.RecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;
import java.util.List;

public class RecipeMapCategory implements IRecipeCategory<RecipeWrapper> {

    protected static int JEI_OFFSET_X = 1, JEI_OFFSET_Y = 1;
    protected static FluidStackRenderer fluidRenderer = new FluidStackRenderer();
    protected static IGuiHelper guiHelper;

    protected String id, title;
    protected IDrawable background, icon;
    protected IDrawableAnimated progressBar;
    protected GuiData gui;

    public RecipeMapCategory(MachineStack stack) {
        this(stack.getType().getRecipeMap(), stack.getType().getGui());
        icon = guiHelper.createDrawableIngredient(stack.asItemStack());
    }

    public RecipeMapCategory(MachineStack stack, GuiData gui) {
        this(stack.getType().getRecipeMap(), gui);
        icon = guiHelper.createDrawableIngredient(stack.asItemStack());
    }

    public RecipeMapCategory(RecipeMap map, GuiData gui) {
        id = map.getId();
        title = map.getDisplayName();
        int4 padding = gui.getPadding(), area = gui.getArea(), progress = gui.getDir().getUV();
        background = guiHelper.drawableBuilder(gui.getTexture(gui.getHighestTier()), area.x, area.y, area.z, area.w).addPadding(padding.x, padding.y, padding.z, padding.w).build();
        progressBar = guiHelper.drawableBuilder(gui.getTexture(gui.getHighestTier()), progress.x, progress.y, progress.z, progress.w).buildAnimated(50, IDrawableAnimated.StartDirection.LEFT, false);
        icon = guiHelper.createDrawableIngredient(Data.DebugScanner.get(1));
        this.gui = gui;
    }

    @Override
    public String getUid() {
        return id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getModName() {
        return Ref.NAME;
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Nullable
    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        if (progressBar == null) return;
        progressBar.draw(minecraft, gui.getDir().getPos().x + gui.getArea().x, gui.getDir().getPos().y + gui.getArea().y);
    }

    @Override
    public void setRecipe(IRecipeLayout layout, RecipeWrapper wrapper, IIngredients ingredients) {
        wrapper.setPadding(gui.getPadding());

        IGuiItemStackGroup itemGroup = layout.getItemStacks();
        IGuiFluidStackGroup fluidGroup = layout.getFluidStacks();
        List<SlotData> slots;
        Tier tier = gui.getHighestTier();
        int i = 0, slotIndex = 0;

        int offsetX = gui.getArea().x + JEI_OFFSET_X, offsetY = gui.getArea().y + JEI_OFFSET_Y;

        if (wrapper.recipe.hasInputItems()) {
            slots = gui.getSlots(SlotType.IT_IN, tier);
            if (slots.size() > 0) {
                for (ItemStack stack : wrapper.recipe.getInputItems()) {
                    itemGroup.init(i, true, slots.get(slotIndex).x - offsetX, slots.get(slotIndex++).y - offsetY);
                    itemGroup.set(i++, stack);
                }
            }
        }
        if (wrapper.recipe.hasOutputItems()) {
            slots = gui.getSlots(SlotType.IT_OUT, tier);
            if (slots.size() > 0) {
                slotIndex = 0;
                for (ItemStack stack : wrapper.recipe.getOutputItemsJEI()) {
                    itemGroup.init(i, false, slots.get(slotIndex).x - offsetX, slots.get(slotIndex++).y - offsetY);
                    itemGroup.set(i++, stack);
                }
            }
        }

        i = 0;
        if (wrapper.recipe.hasInputFluids()) {
            slots = gui.getSlots(SlotType.FL_IN, tier);
            if (slots.size() > 0) {
                slotIndex = 0;
                for (FluidStack fluid : wrapper.recipe.getInputFluids()) {
                    fluidGroup.init(i, true, fluidRenderer, slots.get(slotIndex).x - (offsetX - 1), slots.get(slotIndex++).y - (offsetY - 1), 16, 16, 0, 0);
                    fluidGroup.set(i++, fluid);
                }
            }
        }
        if (wrapper.recipe.hasOutputFluids()) {
            slots = gui.getSlots(SlotType.FL_OUT, tier);
            if (slots.size() > 0) {
                slotIndex = 0;
                for (FluidStack fluid : wrapper.recipe.getOutputFluids()) {
                    fluidGroup.init(i, false, fluidRenderer, slots.get(slotIndex).x - (offsetX - 1), slots.get(slotIndex++).y - (offsetY - 1), 16, 16, 0, 0);
                    fluidGroup.set(i++, fluid);
                }
            }
        }
    }

    public static void setGuiHelper(IGuiHelper helper) {
        guiHelper = helper;
    }
}
