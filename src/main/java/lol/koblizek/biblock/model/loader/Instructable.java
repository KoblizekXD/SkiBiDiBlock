package lol.koblizek.biblock.model.loader;

import net.minecraft.client.util.math.MatrixStack;

public interface Instructable {
    void render(WavefrontModel model, MatrixStack matrixStack);
}
