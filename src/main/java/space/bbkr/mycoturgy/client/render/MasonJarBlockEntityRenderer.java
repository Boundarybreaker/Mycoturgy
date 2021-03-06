package space.bbkr.mycoturgy.client.render;

import space.bbkr.mycoturgy.block.entity.MasonJarBlockEntity;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;

public class MasonJarBlockEntityRenderer extends BlockEntityRenderer<MasonJarBlockEntity> {
	public MasonJarBlockEntityRenderer(BlockEntityRenderDispatcher dispatcher) {
		super(dispatcher);
	}

	@Override
	public void render(MasonJarBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
		ItemStack stack = entity.getInventory().getStack();
		if (!stack.isEmpty()) {
			matrices.push();
			matrices.translate(0.5D, 0.2D, 0.5D);
			Vec3d offset = entity.getCachedState().getModelOffset(entity.getWorld(), entity.getPos());
			matrices.translate(offset.x, offset.y + (Math.sin((tickDelta + entity.ticks) / 8) * 0.05), offset.z);
			matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion((tickDelta + entity.ticks) / 1.5f));
			matrices.scale(0.35f, 0.35f, 0.35f);
			MinecraftClient.getInstance().getItemRenderer().renderItem(stack, ModelTransformation.Mode.FIXED, light, overlay, matrices, vertexConsumers);
			matrices.pop();
		}
	}
}
