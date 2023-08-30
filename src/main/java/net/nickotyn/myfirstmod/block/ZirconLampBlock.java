package net.nickotyn.myfirstmod.block;

import net.minecraft.client.sounds.SoundEngine;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.event.TickEvent;

public class ZirconLampBlock extends Block{
    public static final BooleanProperty LIT = BooleanProperty.create("lit");

    public ZirconLampBlock(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand hand, BlockHitResult blockHitResult) {

        if(!level.isClientSide() && hand == InteractionHand.MAIN_HAND){
            level.setBlock(blockPos, blockState.cycle(LIT),3);
            player.swing(hand,true);

            level.playSound(null, blockPos, SoundEvents.BEEHIVE_ENTER, SoundSource.PLAYERS, 1.0f, 1.0f);
        }

            return super.use(blockState, level, blockPos, player, hand, blockHitResult);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder){
        builder.add(LIT);
    }
}

