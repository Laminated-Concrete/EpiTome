package net.fryke.tomesofpower.particles;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.fryke.tomesofpower.ToPMod;
import net.minecraft.client.particle.FireSmokeParticle;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModParticles {
    public static final DefaultParticleType CUSTOM_SMOKE = FabricParticleTypes.simple();
    public static final DefaultParticleType FALLING_UP_WATER = FabricParticleTypes.simple();

    public static void registerModParticles() {
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(ToPMod.MOD_ID, "custom_smoke"), CUSTOM_SMOKE);
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(ToPMod.MOD_ID, "falling_up_water"), FALLING_UP_WATER);
    }
}
