package lol.koblizek.biblock.client;

import lol.koblizek.biblock.TestBlock;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

public class BiBlockClientMod implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockEntityRendererFactories.register(TestBlock.DEMO_BLOCK_ENTITY, x -> new TestBlock.TestBlockEntityRenderer());
//        WorldRenderEvents.END.register(context -> {
//            Camera camera = context.camera();
//            Vec3d targetPosition = new Vec3d(0, 50, 0);
//            Vec3d transformedPosition = targetPosition.subtract(camera.getPos());
//            MatrixStack matrixStack = new MatrixStack();
//            matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(camera.getPitch()));
//            matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(camera.getYaw() + 180.0F));
//            matrixStack.translate(transformedPosition.x, transformedPosition.y, transformedPosition.z);
//
//            TestBlock.TestBlockEntityRenderer.POLE.render();
//        });
    }
}
