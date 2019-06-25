package muramasa.gtu.api.data;

import muramasa.gtu.Ref;
import muramasa.gtu.api.materials.Material;
import muramasa.gtu.api.registration.IGregTechObject;
import muramasa.gtu.api.texture.Texture;
import net.minecraft.block.SoundType;

import java.util.ArrayList;
import java.util.Collection;

public class StoneType implements IGregTechObject {

    private static ArrayList<StoneType> generating = new ArrayList<>(), all = new ArrayList<>();
    private static int LAST_INTERNAL_ID;
    
    //TODO: more functionality, soundtype etc

    public static StoneType STONE = new StoneType("stone", Materials.Stone, false, new Texture("minecraft", "blocks/stone"), SoundType.STONE);
    public static StoneType GRANITE = new StoneType("granite", Materials.Stone, false, new Texture("minecraft", "blocks/stone_granite"), SoundType.STONE);
    public static StoneType DIORITE = new StoneType("diorite", Materials.Stone, false, new Texture("minecraft", "blocks/stone_diorite"), SoundType.STONE);
    public static StoneType ANDESITE = new StoneType("andesite", Materials.Stone, false, new Texture("minecraft", "blocks/stone_andesite"), SoundType.STONE);
    
    public static StoneType SAND = new StoneType("sand", Materials.SiliconDioxide, false, new Texture("minecraft", "blocks/sand"), SoundType.SAND);
    public static StoneType SAND_RED = new StoneType("sand_Red", Materials.SiliconDioxide, false, new Texture("minecraft", "blocks/red_sand"), SoundType.SAND);
    public static StoneType SANDSTONE = new StoneType("sandstone", Materials.SiliconDioxide, false, new Texture("minecraft", "blocks/sandstone_normal"), SoundType.STONE);
    
    public static StoneType NETHERRACK = new StoneType("netherrack", Materials.Netherrack, false, new Texture("minecraft", "blocks/netherrack"), SoundType.STONE);
    public static StoneType ENDSTONE = new StoneType("endstone", Materials.Endstone, false, new Texture("minecraft", "blocks/end_stone"), SoundType.STONE);

    public static StoneType GRANITE_RED = new StoneType("granite_red", Materials.GraniteRed, true, new Texture(Ref.MODID, "blocks/stone/granite_red"), SoundType.STONE);
    public static StoneType GRANITE_BLACK = new StoneType("granite_black", Materials.GraniteBlack, true, new Texture(Ref.MODID, "blocks/stone/granite_black"), SoundType.STONE);
    public static StoneType MARBLE = new StoneType("marble", Materials.Marble, true, new Texture(Ref.MODID, "blocks/stone/marble"), SoundType.STONE);
    public static StoneType BASALT = new StoneType("basalt", Materials.Basalt, true, new Texture(Ref.MODID, "blocks/stone/basalt"), SoundType.STONE);

    private String id;
    private Material material;
    private Texture texture;
    private int internalId;
    private SoundType soundType;
    
    public StoneType(String id, Material material, boolean generate, Texture texture, SoundType soundType) {
        this.id = id;
        this.material = material;
        this.texture = texture;
        this.internalId = LAST_INTERNAL_ID++;
        this.soundType = soundType;
        if (generate) {
            generating.add(this);
        }
        all.add(this);
    }

    @Override
    public String getId() {
        return id;
    }

    public Material getMaterial() {
        return material;
    }

    public Texture getTexture() {
        return texture;
    }

    public int getInternalId() {
        return internalId;
    }
    
    public SoundType getSoundType() {
    	return soundType;
    }

    public static Collection<StoneType> getGenerating() {
        return generating;
    }

    public static Collection<StoneType> getAll() {
        return all;
    }

    public static Collection<Texture> getAllTextures() {
        ArrayList<Texture> textures = new ArrayList<>();
        for (StoneType type : getAll()) {
            textures.add(type.getTexture());
        }
        return textures;
    }

    public static int getLastInternalId() {
        return LAST_INTERNAL_ID;
    }
}
