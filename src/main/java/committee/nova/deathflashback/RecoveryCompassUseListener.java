package committee.nova.deathflashback;

import dev.intelligentcreations.mudrock.event.listeners.ItemUseListener;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class RecoveryCompassUseListener implements ItemUseListener {
    @Override
    public TypedActionResult<ItemStack> onItemUse(World world, PlayerEntity user, Hand hand) {
        TypedActionResult<ItemStack> tar = TypedActionResult.pass(user.getMainHandStack());
        if (user.getMainHandStack().isItemEqual(Items.RECOVERY_COMPASS.getDefaultStack()) && user.getOffHandStack().isItemEqual(Items.ENDER_PEARL.getDefaultStack()) && user.getLastDeathPos().isPresent()) {
            user.teleport(user.getLastDeathPos().get().getPos().getX(), user.getLastDeathPos().get().getPos().getY(), user.getLastDeathPos().get().getPos().getZ());
            if (!world.isClient) {
                world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 1f, 1f);
            }
            if (!user.isCreative()) {
                user.getOffHandStack().decrement(1);
            }
            tar = TypedActionResult.success(user.getMainHandStack());
        }
        return tar;
    }
}
