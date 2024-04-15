package lol.koblizek.biblock;

import lol.koblizek.biblock.model.loader.WavefrontModel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class TestBlock extends Block implements BlockEntityProvider {

    public TestBlock() {
        super(FabricBlockSettings.create());
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new TestBlockEntity(pos, state);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.INVISIBLE;
    }

    public static class TestBlockEntity extends BlockEntity {
        public TestBlockEntity(BlockPos pos, BlockState state) {
            super(DEMO_BLOCK_ENTITY, pos, state);
        }
    }

    public static final TestBlock DEMO_BLOCK = new TestBlock();

    public static final BlockEntityType<TestBlockEntity> DEMO_BLOCK_ENTITY = Registry.register(
            Registries.BLOCK_ENTITY_TYPE,
            new Identifier("biblock", "test_block_entity"),
            FabricBlockEntityTypeBuilder.create(TestBlockEntity::new, DEMO_BLOCK).build()
    );

    @Environment(EnvType.CLIENT)
    public static class TestBlockEntityRenderer implements BlockEntityRenderer<TestBlockEntity> {

        public static final WavefrontModel POLE = new WavefrontModel(new Identifier("biblock", "models/block/pole.obj"));

        @Override
        public void render(TestBlockEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
            matrices.push();
            POLE.render(matrices);
            matrices.pop();
        }
    }
}
