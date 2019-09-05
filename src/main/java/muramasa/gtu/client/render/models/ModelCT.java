package muramasa.gtu.client.render.models;

import muramasa.gtu.api.blocks.BlockCT;
import muramasa.gtu.client.render.bakedmodels.BakedCT;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.model.IModelState;

import java.util.function.Function;

public class ModelCT extends ModelBase {

    private BlockCT block;

    public ModelCT(BlockCT block) {
        this.block = block;
    }

    @Override
    public IBakedModel bakeModel(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
        return new BakedCT(block);
    }
}
