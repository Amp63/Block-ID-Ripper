package net.amp.blockidripper;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BlockIdRipper implements ModInitializer {
	public static final String BLOCKIDRIPPER = "blockidripper";
	public static final Logger LOGGER = LoggerFactory.getLogger(BLOCKIDRIPPER);

	@Override
	public void onInitialize() {
		LOGGER.info("Hello Fabric world!");
	}
}
