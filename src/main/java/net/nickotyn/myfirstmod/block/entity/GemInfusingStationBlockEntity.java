package net.nickotyn.myfirstmod.block.entity;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.*;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.nickotyn.myfirstmod.item.ModItems;
import net.nickotyn.myfirstmod.item.custom.tags.ModTags;
import net.nickotyn.myfirstmod.networking.ModMessages;
import net.nickotyn.myfirstmod.networking.packet.FluidSyncS2CPacket;
import net.nickotyn.myfirstmod.networking.packet.ItemStackSyncS2CPacket;
import net.nickotyn.myfirstmod.recipe.GemInfusingStationRecipe;
import net.nickotyn.myfirstmod.screen.GemInfusingStationMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


import java.util.Optional;


public class GemInfusingStationBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(5) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (!level.isClientSide()) {
                ModMessages.sendToClients((new ItemStackSyncS2CPacket(this, worldPosition)));
            }
        }


        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {

            return switch (slot) {
                case 0 -> stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).isPresent();
                case 1, 2 -> true;
                case 3 -> false;
                case 4 -> stack.is(ModTags.Items.MOULDS);
                default -> super.isItemValid(slot, stack);
            };
        }
    };


    private static Level staticLevel;
    private static BlockPos staticBlockPos;

    private static void setContext(Level level, BlockPos blockPos) {
        staticLevel = level;
        staticBlockPos = blockPos;
    }


    private final FluidTank FLUID_TANK = new FluidTank(30000) {
        @Override
        protected void onContentsChanged() {
            setChanged();
            if (!level.isClientSide()) {
                ModMessages.sendToClients(new FluidSyncS2CPacket(this.fluid, worldPosition));
            }
        }

        @Override
        public boolean isFluidValid(FluidStack stack) {
            return stack.getFluid() == Fluids.LAVA;

        }

    };

    public void setFluid(FluidStack stack) {
        this.FLUID_TANK.setFluid((stack));

    }

    public FluidStack getFluidStack() {
        return this.FLUID_TANK.getFluid();
    }

    public ItemStack getRenderStack() {
        ItemStack stack;

        if (!itemHandler.getStackInSlot(4).isEmpty()) {
            stack = itemHandler.getStackInSlot(4);
        } else {
            stack = ItemStack.EMPTY;
        }

        return stack;
    }

    public void setHandler(ItemStackHandler itemStackHandler) {
        for (int i = 0; i < itemStackHandler.getSlots(); i++) {
            itemHandler.setStackInSlot(i, itemStackHandler.getStackInSlot(i));
        }
    }

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    private LazyOptional<IFluidHandler> lazyFluidHandler = LazyOptional.empty();

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 780;

    public GemInfusingStationBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.GEM_INFUSING_STATION.get(), pos, state);
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> GemInfusingStationBlockEntity.this.progress;
                    case 1 -> GemInfusingStationBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> GemInfusingStationBlockEntity.this.progress = value;
                    case 1 -> GemInfusingStationBlockEntity.this.maxProgress = value;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Gem Infusing Station");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new GemInfusingStationMenu(id, inventory, this, this.data);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }

        if (cap == ForgeCapabilities.FLUID_HANDLER) {
            return lazyFluidHandler.cast();
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
        lazyFluidHandler = LazyOptional.of(() -> FLUID_TANK);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
        lazyFluidHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        nbt.put("inventory", itemHandler.serializeNBT());
        nbt.putInt("gem_infusing_station.progress", this.progress);
        nbt = FLUID_TANK.writeToNBT(nbt);

        super.saveAdditional(nbt);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
        progress = nbt.getInt("gem_infusing_station.progress");
        FLUID_TANK.readFromNBT(nbt);
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, GemInfusingStationBlockEntity pEntity) {


        ///TODO innefitient it updates the block every tick, it needs to be updated only when the world loades
        if (!level.isClientSide()) {
            ModMessages.sendToClients((new ItemStackSyncS2CPacket(pEntity.itemHandler, pEntity.worldPosition)));
        }

        if (level.isClientSide()) {
        }


        if (hasRecipe(pEntity) && hasEnoughFluid(pEntity)) {
            pEntity.progress++;
            setChanged(level, pos, state);

            if (pEntity.progress >= pEntity.maxProgress) {
                craftItem(pEntity);

            }
        } else {
            pEntity.resetProgress();
            setChanged(level, pos, state);
        }

        if (hasFluidItemInSourceSlot(pEntity)) {
            transferItemFluidToFluidTank(pEntity, level, pos);
        }

    }

    private static boolean hasEnoughFluid(GemInfusingStationBlockEntity pEntity) {
        return pEntity.FLUID_TANK.getFluidAmount() >= 500;
    }

    private static void transferItemFluidToFluidTank(GemInfusingStationBlockEntity pEntity, Level level, BlockPos pos) {
        pEntity.itemHandler.getStackInSlot(0).getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).ifPresent(handler -> {

            int drainAmount = Math.min(pEntity.FLUID_TANK.getSpace(), 1500);

            FluidStack stack = handler.drain(drainAmount, IFluidHandler.FluidAction.SIMULATE);
            if (pEntity.FLUID_TANK.isFluidValid(stack)) {
                stack = handler.drain(drainAmount, IFluidHandler.FluidAction.EXECUTE);

                setContext(staticLevel, staticBlockPos);
                fillTankWithFluid(pEntity, stack, handler.getContainer());
                level.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BUCKET_FILL_LAVA, SoundSource.PLAYERS, 1.0f, 1.0f);

            }

        });
    }

    private static void fillTankWithFluid(GemInfusingStationBlockEntity pEntity, FluidStack stack, ItemStack container) {
        pEntity.FLUID_TANK.fill(stack, IFluidHandler.FluidAction.EXECUTE);

        pEntity.itemHandler.extractItem(0, 1, false);
        pEntity.itemHandler.insertItem(0, container, false);
    }

    private static boolean hasFluidItemInSourceSlot(GemInfusingStationBlockEntity pEntity) {
        return pEntity.itemHandler.getStackInSlot(0).getCount() > 0;
    }

    private void resetProgress() {
        this.progress = 0;
    }

    private static void craftItem(GemInfusingStationBlockEntity pEntity) {
        pEntity.FLUID_TANK.drain(500, IFluidHandler.FluidAction.EXECUTE);
        Level level = pEntity.level;
        SimpleContainer inventory = new SimpleContainer(pEntity.itemHandler.getSlots());
        for (int i = 0; i < pEntity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, pEntity.itemHandler.getStackInSlot(i));
        }

        Optional<GemInfusingStationRecipe> recipe = level.getRecipeManager()
                .getRecipeFor(GemInfusingStationRecipe.Type.INSTANCE, inventory, level);

        if (hasRecipe(pEntity)) {

            ItemStack inputStack1 = pEntity.itemHandler.extractItem(1, 1, false);
            ItemStack inputStack2 = pEntity.itemHandler.extractItem(2, 1, false);

            if (!inputStack1.isEmpty() && !inputStack2.isEmpty()) {

//                pEntity.itemHandler.extractItem(1, 1, false);
//                pEntity.itemHandler.extractItem(2, 1, false);
                pEntity.itemHandler.setStackInSlot(3, new ItemStack(recipe.get().getResultItem().getItem(),
                        pEntity.itemHandler.getStackInSlot(3).getCount() + 1));

                pEntity.resetProgress();
            }
        }
    }

    private static boolean hasRecipe(@NotNull GemInfusingStationBlockEntity entity) {
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));

        }

        Optional<GemInfusingStationRecipe> recipe = level.getRecipeManager()
                .getRecipeFor(GemInfusingStationRecipe.Type.INSTANCE, inventory, level);

        return recipe.isPresent() && canInsertAmountIntoOutputSlot(inventory) &&
                canInsertItemIntoOutputSlot(inventory, recipe.get().getResultItem());
    }

    private static boolean canInsertItemIntoOutputSlot(SimpleContainer inventory, ItemStack stack) {
        return inventory.getItem(3).getItem() == stack.getItem() || inventory.getItem(3).isEmpty();
    }

    private static boolean canInsertAmountIntoOutputSlot(SimpleContainer inventory) {
        return inventory.getItem(3).getMaxStackSize() > inventory.getItem(3).getCount();
    }


}
