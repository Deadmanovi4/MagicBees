package magicbees.integration.thaumcraft.effects;

import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeHousing;
import forestry.api.genetics.IEffectData;
import magicbees.elec332.corerepack.compat.forestry.EffectData;
import magicbees.elec332.corerepack.compat.forestry.allele.AlleleEffectThrottled;
import magicbees.integration.thaumcraft.AuraHelper;
import net.minecraft.util.ResourceLocation;

/**
 * Created by Elec332 on 3-8-2019
 */
public class AlleleEffectPurifying extends AlleleEffectThrottled {

    public AlleleEffectPurifying(ResourceLocation rl) {
        super(rl);
        setRequiresWorkingQueen();
        setThrottle(15);
    }

    @Override
    public IEffectData doEffectThrottled(IBeeGenome genome, IEffectData storedData, IBeeHousing housing) {
        AuraHelper.handleRandomChunk(genome, housing, auraChunk -> {
            float flux = auraChunk.getFlux();
            float sub = Math.min(flux, 0.05f + 0.04f * housing.getWorldObj().rand.nextFloat());
            auraChunk.setVis(auraChunk.getVis() + sub);
            auraChunk.setFlux(flux - sub);
        });
        storedData.setInteger(0, 0);
        return storedData;
    }

    @Override
    public IEffectData validateStorage(IEffectData storedData) {
        if (storedData == null || !(storedData instanceof EffectData)) {
            storedData = new EffectData(1, 0, 0);
        }
        return storedData;
    }

}