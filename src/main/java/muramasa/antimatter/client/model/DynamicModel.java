package muramasa.antimatter.client.model;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import muramasa.antimatter.client.AntimatterModelManager;
import muramasa.antimatter.client.baked.DynamicBakedModel;
import net.minecraft.client.renderer.model.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraftforge.client.model.IModelConfiguration;
import net.minecraftforge.client.model.data.EmptyModelData;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

public class DynamicModel extends AntimatterModel<DynamicModel> {

    protected IUnbakedModel modelDefault;
    protected Int2ObjectOpenHashMap<Tuple<String, IUnbakedModel>> configModels;

    public DynamicModel(IUnbakedModel modelDefault, Int2ObjectOpenHashMap<Tuple<String, IUnbakedModel>> configModels) {
        this.modelDefault = modelDefault;
        this.configModels = configModels;
    }

    @Override
    public IBakedModel bake(IModelConfiguration owner, ModelBakery bakery, Function<Material, TextureAtlasSprite> getter, IModelTransform transform, ItemOverrideList overrides, ResourceLocation loc) {
        IBakedModel bakedDefault = modelDefault.bakeModel(bakery, getter, transform, loc);
        Int2ObjectOpenHashMap<IBakedModel> bakedModels = new Int2ObjectOpenHashMap<>();
        configModels.forEach((k, v) -> bakedModels.put((int)k, AntimatterModelManager.getBaked(v.getA(), () -> v.getB().bakeModel(bakery, getter, transform, loc))));
        return new DynamicBakedModel(bakedDefault, bakedModels).particle(bakedDefault.getParticleTexture(EmptyModelData.INSTANCE));
    }

    @Override
    public Collection<Material> getTextures(IModelConfiguration owner, Function<ResourceLocation, IUnbakedModel> getter, Set<Pair<String, String>> errors) {
        Set<Material> textures = new HashSet<>();
        configModels.values().forEach(t -> textures.addAll(t.getB().getTextures(getter, errors)));
        textures.addAll(modelDefault.getTextures(getter, errors));
        return textures;
    }
}
