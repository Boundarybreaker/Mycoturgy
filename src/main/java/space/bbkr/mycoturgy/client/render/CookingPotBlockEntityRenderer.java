package space.bbkr.mycoturgy.client.render;

import java.util.ArrayList;
import java.util.List;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import space.bbkr.mycoturgy.block.entity.CookingPotBlockEntity;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;

public class CookingPotBlockEntityRenderer extends BlockEntityRenderer<CookingPotBlockEntity> {
	private static final List<Vec3d> OFFSETS = new ArrayList<>();

	public CookingPotBlockEntityRenderer(BlockEntityRenderDispatcher dispatcher) {
		super(dispatcher);
	}

	@Override
	public void render(CookingPotBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
		for (int i = 0; i < entity.getInventory().size(); i++) {
			renderStack(OFFSETS.get(i), entity.getInventory().getStack(i), entity, tickDelta, matrices, vertexConsumers, light, overlay);
		}
		renderStack(Vec3d.ZERO, entity.getOutputInventory().getStack(), entity, tickDelta, matrices, vertexConsumers, light, overlay);
	}

	private void renderStack(Vec3d offset, ItemStack stack, CookingPotBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
		if (!stack.isEmpty()) {
			matrices.push();
			matrices.translate(0.5D, -0.2D, 0.5D);
			matrices.translate(offset.x, offset.y + (Math.sin((tickDelta + entity.ticks) / 8) * 0.05), offset.z);
			matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion((tickDelta + entity.ticks) / 1.5f));
			matrices.scale(0.35f, 0.35f, 0.35f);
			MinecraftClient.getInstance().getItemRenderer().renderItem(stack, ModelTransformation.Mode.FIXED, light, overlay, matrices, vertexConsumers);
			matrices.pop();
		}
	}

	static {
		OFFSETS.add(new Vec3d(2/16D, 0D, 2/16D));
		OFFSETS.add(new Vec3d(-2/16D, 0D, -2/16D));
		OFFSETS.add(new Vec3d(-2/16D, 0D, 2/16D));
		OFFSETS.add(new Vec3d(2/16D, 0D, -2/16D));
		OFFSETS.add(new Vec3d(0D, 0D, 4/16D));
		OFFSETS.add(new Vec3d(0D, 0D, -4/16D));
	}
}
