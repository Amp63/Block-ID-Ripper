package net.amp.blockidripper;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import static net.minecraft.server.command.CommandManager.*;

import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import net.fabricmc.api.ModInitializer;
import net.minecraft.server.command.ServerCommandSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.fabricmc.loader.api.FabricLoader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.File;
import java.nio.file.Path;

public class BlockIdRipper implements ModInitializer {
	public static final String BLOCKIDRIPPER = "blockidripper";
	public static final Logger LOGGER = LoggerFactory.getLogger(BLOCKIDRIPPER);
	@Override
	public void onInitialize() {
		LOGGER.info("yo");
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal("ripid")
				.executes(context -> {
					ServerCommandSource source = context.getSource();
					Path gameDir = FabricLoader.getInstance().getGameDir();
					String itemIdFilePath = gameDir.resolve("ripped-item-ids.txt").toString();
					String blockIdFilePath = gameDir.resolve("ripped-block-ids.txt").toString();
					String enchantsFilePath = gameDir.resolve("ripped-enchants.txt").toString();
					String entitiesFilePath = gameDir.resolve("ripped-entity-types.txt").toString();
					try {
						new File(itemIdFilePath).createNewFile();
						new File(blockIdFilePath).createNewFile();
						new File(enchantsFilePath).createNewFile();
						new File(entitiesFilePath).createNewFile();
					} catch (IOException e) {
						System.out.println("Error creating files.");
						e.printStackTrace();
					}

					try {
						PrintWriter itemIdPw = new PrintWriter(itemIdFilePath);
						PrintWriter blockIdPw = new PrintWriter(blockIdFilePath);
						PrintWriter enchantsPw = new PrintWriter(enchantsFilePath);
						PrintWriter entitiesPw = new PrintWriter(entitiesFilePath);

						for (Identifier id : Registries.ITEM.getIds()) {
							itemIdPw.println(id.toString());
						}
						for (Identifier id : Registries.BLOCK.getIds()) {
							blockIdPw.println(id.toString());
						}
						for (Identifier id : Registries.ENCHANTMENT.getIds()) {
							enchantsPw.println(id.toString());
						}
						for (Identifier id : Registries.ENTITY_TYPE.getIds()) {
							entitiesPw.println(id.toString());
						}

						itemIdPw.close();
						blockIdPw.close();
						enchantsPw.close();
						entitiesPw.close();
						source.sendMessage(Text.literal("Successfully ripped item ids, block ids, enchantments, and entity types."));
					} catch (FileNotFoundException e) {
						System.out.println("File not found");
						e.printStackTrace();
					}
					return 1;
				})));

	}
}
