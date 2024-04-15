package lol.koblizek.biblock.model.loader;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.util.math.MatrixStack;

public class Culling implements Instructable {

    private final String arg;

    public Culling(String arg) {
        this.arg = arg;
    }

    @Override
    public void render(WavefrontModel model, MatrixStack matrixStack) {
        if (arg.equals("on"))
            RenderSystem.enableCull();
        else if (arg.equals("off"))
            RenderSystem.disableCull();
        else WavefrontModel.LOGGER.warn("Unknown culling argument: {}", arg);
    }
}
