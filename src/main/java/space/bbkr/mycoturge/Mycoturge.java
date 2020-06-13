package space.bbkr.mycoturge;

import nerdhub.cardinal.components.api.ComponentRegistry;
import nerdhub.cardinal.components.api.ComponentType;
import nerdhub.cardinal.components.api.event.ChunkComponentCallback;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import space.bbkr.mycoturge.block.CustomCropBlock;
import space.bbkr.mycoturge.component.HaustorComponent;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.loot.v1.FabricLootPoolBuilder;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;

public class Mycoturge implements ModInitializer {
	public static final String MODID = "mycoturge";

	public static final Logger logger = LogManager.getLogger();

	//TODO: config
	public static final int HAUSTOR_TICK_SPEED = 2;

	public static Block SPOREBRUSH_CROP;

	public static Item SPORE_BUNDLE;
	public static Item SPOREBRUSH;
	public static Item GLITTERING_SPORES;
	public static Item SPOREBRUSH_ASH;

	public static final ItemGroup MYCOTURGE_GROUP = FabricItemGroupBuilder.build(new Identifier(MODID, MODID), () -> new ItemStack(SPORE_BUNDLE));

	//TODO: static reg
	public static final ComponentType<HaustorComponent> HAUSTOR_COMPONENT = ComponentRegistry.INSTANCE.registerIfAbsent(new Identifier(MODID, "haustor"), HaustorComponent.class);

	@Override
	public void onInitialize() {
		SPOREBRUSH_CROP = register("sporebrush", new CustomCropBlock(FabricBlockSettings.copyOf(Blocks.WHEAT).breakByHand(true)));
		SPORE_BUNDLE = register("spore_bundle", new Item(new Item.Settings().group(MYCOTURGE_GROUP)));
		SPOREBRUSH = register("sporebrush", new Item(new Item.Settings().group(MYCOTURGE_GROUP)));
		GLITTERING_SPORES = register("glimmering_spores", new AliasedBlockItem(SPOREBRUSH_CROP, new Item.Settings().group(MYCOTURGE_GROUP)));
		SPOREBRUSH_ASH = register("sporebrush_ash", new Item(new Item.Settings().group(MYCOTURGE_GROUP)));

		LootTableLoadingCallback.EVENT.register((resourceManager, lootManager, id, builder, table) -> {
			if (id.equals(new Identifier("blocks/grass"))) {
				builder.withPool(FabricLootPoolBuilder.builder()
						.withCondition(RandomChanceLootCondition.builder(0.1f)
								.build()
						)
				.withEntry(ItemEntry.builder(GLITTERING_SPORES)
						.build()
				).build());
			}
		});

		//TODO: static reg
		ChunkComponentCallback.register(HAUSTOR_COMPONENT, HaustorComponent::new);
	}

	private static Item register(String name, Item item) {
		return Registry.register(Registry.ITEM, new Identifier(MODID, name), item);
	}

	private static Block register(String name, Block block) {
		return Registry.register(Registry.BLOCK, new Identifier(MODID, name), block);
	}
}
