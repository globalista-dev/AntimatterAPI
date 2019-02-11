package muramasa.gregtech.common.items;

import muramasa.gregtech.api.materials.Material;
import muramasa.gregtech.api.materials.Prefix;
import muramasa.gregtech.common.blocks.BlockOre;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockOres extends ItemBlock {

    public ItemBlockOres(Block block) {
        super(block);
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        Material material = ((BlockOre) Block.getBlockFromItem(stack.getItem())).getMaterial();
        if (material != null) {
            return Prefix.Ore.getDisplayName(material);
        }
        return getUnlocalizedName();
    }

    public static class ColorHandler implements IItemColor {
        @Override
        public int colorMultiplier(ItemStack stack, int tintIndex) {
            if (tintIndex == 1) {
                Material material = ((BlockOre) Block.getBlockFromItem(stack.getItem())).getMaterial();
                if (material != null) {
                    return material.getRGB();
                }
            }
            return -1;
        }
    }
}
