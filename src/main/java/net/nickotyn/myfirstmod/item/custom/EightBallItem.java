package net.nickotyn.myfirstmod.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;



import javax.swing.text.StyledEditorKit;
import java.util.List;

public class EightBallItem extends Item {
    private Level level;
    private Player player;
    private InteractionHand hand;

    public EightBallItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, @NotNull Player player, @NotNull InteractionHand hand) {

        if(!level.isClientSide() && hand == InteractionHand.MAIN_HAND){
        outputRandomNumber(player);
        player.getCooldowns().addCooldown(this,20);
        }

        return super.use(level, player, hand);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag) {

        components.add(Component.literal(("Helpful to predict the") + ("\u00A7o Future")).withStyle(ChatFormatting.LIGHT_PURPLE));

        super.appendHoverText(itemStack, level, components, tooltipFlag);
    }

    private void outputRandomNumber(Player player){
        player.sendSystemMessage(Component.literal("Your Number is " + getRandomNumber()).withStyle(ChatFormatting.GOLD));
    }

    private int getRandomNumber(){
        return RandomSource.createNewThreadLocalInstance().nextInt(10);
    }
}
