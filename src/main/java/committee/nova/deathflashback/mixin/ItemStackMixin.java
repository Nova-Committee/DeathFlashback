package committee.nova.deathflashback.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public class ItemStackMixin {
    @Inject(method = "use", at = @At("RETURN"), cancellable = true)
    public void onUse(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
        if (user.getMainHandStack().isItemEqual(Items.RECOVERY_COMPASS.getDefaultStack()) && user.getOffHandStack().isItemEqual(Items.ENDER_PEARL.getDefaultStack()) && user.getLastDeathPos().isPresent()) {
            user.teleport(user.getLastDeathPos().get().getPos().getX(), user.getLastDeathPos().get().getPos().getY(), user.getLastDeathPos().get().getPos().getZ());
            if (!world.isClient) {
                world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 1f, 1f);
            }
            if (!user.isCreative()) {
                user.getOffHandStack().decrement(1);
            }
            cir.setReturnValue(TypedActionResult.success(user.getMainHandStack()));
        }
    }
}
