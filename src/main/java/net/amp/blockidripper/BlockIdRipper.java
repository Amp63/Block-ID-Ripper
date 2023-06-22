package net.amp.blockidripper;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import static net.minecraft.server.command.CommandManager.*;

import net.minecraft.util.math.*;
import net.minecraft.server.world.*;
import net.minecraft.block.*;
import net.minecraft.text.*;

import net.fabricmc.api.ModInitializer;
import net.minecraft.server.command.ServerCommandSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.fabricmc.loader.api.FabricLoader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.File;
import java.util.HashSet;

public class BlockIdRipper implements ModInitializer {
	public static final String BLOCKIDRIPPER = "blockidripper";
	public static final Logger LOGGER = LoggerFactory.getLogger(BLOCKIDRIPPER);

	@Override
	public void onInitialize() {
		LOGGER.info("yo");

		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal("rip")
				.executes(context -> {
					ServerCommandSource source = context.getSource();
					ServerWorld world = source.getWorld();

					HashSet<String> seenBlocks = new HashSet<>();
					String textFilePath = FabricLoader.getInstance().getGameDir().resolve("ripped-block-ids.txt").toString();
					try {
						new File(textFilePath).createNewFile();
					} catch (IOException e) {
						System.out.println("Error creating block id file.");
						e.printStackTrace();
					}
					try {
						PrintWriter pw = new PrintWriter(textFilePath);
						int x = 1;
						int z = 1;
						while (x < 308) {  // magic values go brr (change this for later versions if necessary)
							while (z < 310) {
								BlockState b = world.getBlockState(new BlockPos(x, 70, z));
								String blockId = b.getBlock().toString();
								blockId = blockId.substring(16, blockId.length() - 1);
								if (!seenBlocks.contains(blockId)) {
									seenBlocks.add(blockId);
									pw.println(blockId);
								}
								z += 2;
							}
							x += 2;
							z = 1;
						}
						pw.close();
					} catch (FileNotFoundException e) {
						System.out.println("File not found");
						e.printStackTrace();
					}
					source.sendMessage(Text.literal("Successfully ripped ids to " + textFilePath));
					return 1;
				})));
	}
}
