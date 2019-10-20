package muramasa.gtu.api.pipe;

import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.Locale;

//TODO 1.14+ remove the limit of 8 pipe sizes, as Item/Cable need an extra boolean property
public enum PipeSize implements IStringSerializable {

    VTINY(1),
    TINY(2),
    SMALL(4),
    NORMAL(8),
    LARGE(12),
    HUGE(16);

    public static final PipeSize[] VALUES;

    static {
        VALUES = values();
    }

    private int cableThickness;
    private AxisAlignedBB AABB;

    PipeSize(int cableThickness) {
        this.cableThickness = cableThickness;
        float offset = 0.0625f * ordinal();
        AABB = new AxisAlignedBB(0.4375 - offset, 0.4375 - offset, 0.4375 - offset, 0.5625 + offset, 0.5625 + offset, 0.5625 + offset);
    }

    @Override
    public String getName() {
        return name().toLowerCase(Locale.ENGLISH);
    }

    public ITextComponent getDisplayName() {
        return new TranslationTextComponent("pipe." + getName());
    }

    public int getCableThickness() {
        return cableThickness;
    }

    public AxisAlignedBB getAABB() {
        return AABB;
    }
}
