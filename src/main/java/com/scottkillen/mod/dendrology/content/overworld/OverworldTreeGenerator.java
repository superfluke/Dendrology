package com.scottkillen.mod.dendrology.content.overworld;

import java.util.List;
import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.google.common.collect.ImmutableList;
import com.scottkillen.mod.dendrology.config.Settings;

public class OverworldTreeGenerator implements IWorldGenerator
{
    private static final int GENERATOR_WEIGHT = 0;

    public void install()
    {
        GameRegistry.registerWorldGenerator(this, GENERATOR_WEIGHT);
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world,
			IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) 
    {
        final Settings settings = Settings.INSTANCE;
        if (settings.MAX_OVERWORLD_TREE_GEN_RARITY != 0)
        {
            final int x = (chunkX << 4) + 8 + random.nextInt(16);
            final int z = (chunkZ << 4) + 8 + random.nextInt(16);

            final Biome biome = world.getBiome(new BlockPos(x, 90, z));
            final List<Type> biomeTypes = ImmutableList.copyOf(BiomeDictionary.getTypes(biome));

            if (!(biomeTypes.contains(Type.NETHER) || biomeTypes.contains(Type.END)))
                if (random.nextInt(settings.overworldTreeGenRarity) == 0)
                {
                    final OverworldTreeSpecies species =
                            OverworldTreeSpecies.values()[random.nextInt(OverworldTreeSpecies.values().length)];
                    final WorldGenerator tree = species.worldTreeGenerator();
                    final int maxY = world.getHeight(x, z) * 2;
                    final int y = maxY > 0 ? random.nextInt(maxY) : 0;
                    tree.generate(world, random, new BlockPos(x, y, z));
                }
        }
    }
}
