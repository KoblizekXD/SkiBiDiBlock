package lol.koblizek.biblock;

import net.fabricmc.api.ModInitializer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class BiBlockMod implements ModInitializer {
    @Override
    public void onInitialize() {
        Registry.register(
                Registries.BLOCK,
                new Identifier("biblock", "test"),
                TestBlock.DEMO_BLOCK
        );
    }
}
