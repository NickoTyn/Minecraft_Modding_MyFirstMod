/*package net.nickotyn.myfirstmod.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import net.nickotyn.myfirstmod.block.entity.ModBlockEntities;
import net.nickotyn.myfirstmod.block.entity.ToolForgeEntity;
import org.jetbrains.annotations.Nullable;

public class ToolForgeBlock extends BaseEntityBlock {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    protected ToolForgeBlock(Properties properties) {
        super(properties);
    }





    @Override
    public BlockState rotate(BlockState pState, Rotation qRotation) {
        return super.rotate(pState, qRotation);
    }

    @Override
    public BlockState mirror(BlockState qState, Mirror qMirror) {
        return super.mirror(qState, qMirror);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    /*BLOCK ENTITY*/

    /*
    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof ToolForgeEntity) {
                ((ToolForgeEntity) blockEntity).drops();
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }

    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos,
                                 Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide()) {
            BlockEntity entity = pLevel.getBlockEntity(pPos);
            if (entity instanceof ToolForgeEntity) {
                NetworkHooks.openScreen(((ServerPlayer) pPlayer), (ToolForgeEntity) entity, pPos);
            } else {
                throw new IllegalStateException("Our Container provider is missing!");
            }
        }
        return InteractionResult.sidedSuccess(pLevel.isClientSide);
    }

    @Nullable
    @Override
        public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
            return new ToolForgeEntity(pos, state);
        }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createTickerHelper(type, ModBlockEntities.TOOL_FORGE.get(),
                ToolForgeEntity::tick);
    }
}*/
