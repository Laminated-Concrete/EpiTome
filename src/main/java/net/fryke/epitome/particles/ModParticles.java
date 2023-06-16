package net.fryke.epitome.particles;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.fryke.epitome.EpiTomeMod;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModParticles {
    public static final DefaultParticleType CUSTOM_SMOKE = FabricParticleTypes.simple();
    public static final DefaultParticleType FALLING_UP_WATER = FabricParticleTypes.simple();

    public static void registerModParticles() {
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(EpiTomeMod.MOD_ID, "custom_smoke"), CUSTOM_SMOKE);
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(EpiTomeMod.MOD_ID, "falling_up_water"), FALLING_UP_WATER);
    }
}
