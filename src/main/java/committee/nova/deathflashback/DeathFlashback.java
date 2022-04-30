package committee.nova.deathflashback;

import dev.intelligentcreations.mudrock.event.MudrockEventHandler;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeathFlashback implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("Death Flashback");

	@Override
	public void onInitialize() {
		MudrockEventHandler.registerListener(RecoveryCompassUseListener.class);
		LOGGER.info("Initialized.");
	}
}
