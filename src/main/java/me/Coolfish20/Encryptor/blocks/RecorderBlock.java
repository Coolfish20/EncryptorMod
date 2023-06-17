package me.Coolfish20.Encryptor.blocks;

import me.Coolfish20.Encryptor.init.Register;
import me.Coolfish20.Encryptor.tileentities.EncryptorTileEntity;
import me.Coolfish20.Encryptor.tileentities.RecorderTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class RecorderBlock extends HorizontalBlock {
    public RecorderBlock(Properties p_i48440_1_) {
        super(p_i48440_1_);
    }
    //VoxelShapes

    private static VoxelShape VOXEL_SHAPE_N = Stream.of(
            Block.box(2, 1, 3, 14, 2, 13),
            Block.box(4, 2, 5, 12, 3, 11),
            Block.box(2, 0, 3, 3, 1, 4),
            Block.box(13, 0, 3, 14, 1, 4),
            Block.box(13, 0, 12, 14, 1, 13),
            Block.box(2, 0, 12, 3, 1, 13)
    ).reduce((v1, v2) -> VoxelShapes.join(v1, v2, IBooleanFunction.OR)).get();

    private static VoxelShape VOXEL_SHAPE_E = Stream.of(
            Block.box(3, 1, 2, 13, 2, 14),
            Block.box(5, 2, 4, 11, 3, 12),
            Block.box(12, 0, 2, 13, 1, 3),
            Block.box(12, 0, 13, 13, 1, 14),
            Block.box(3, 0, 13, 4, 1, 14),
            Block.box(3, 0, 2, 4, 1, 3)
    ).reduce((v1, v2) -> VoxelShapes.join(v1, v2, IBooleanFunction.OR)).get();

    private static VoxelShape VOXEL_SHAPE_S = Stream.of(
            Block.box(2, 1, 3, 14, 2, 13),
            Block.box(4, 2, 5, 12, 3, 11),
            Block.box(13, 0, 12, 14, 1, 13),
            Block.box(2, 0, 12, 3, 1, 13),
            Block.box(2, 0, 3, 3, 1, 4),
            Block.box(13, 0, 3, 14, 1, 4)
    ).reduce((v1, v2) -> VoxelShapes.join(v1, v2, IBooleanFunction.OR)).get();

    private static VoxelShape VOXEL_SHAPE_W = Stream.of(
            Block.box(3, 1, 2, 13, 2, 14),
            Block.box(5, 2, 4, 11, 3, 12),
            Block.box(3, 0, 13, 4, 1, 14),
            Block.box(3, 0, 2, 4, 1, 3),
            Block.box(12, 0, 2, 13, 1, 3),
            Block.box(12, 0, 13, 13, 1, 14)
    ).reduce((v1, v2) -> VoxelShapes.join(v1, v2, IBooleanFunction.OR)).get();

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
        switch (state.getBlockState().getValue(HorizontalBlock.FACING)) {
            case NORTH:
                return VOXEL_SHAPE_N;
            case EAST:
                return VOXEL_SHAPE_E;
            case SOUTH:
                return VOXEL_SHAPE_S;
            case WEST:
                return VOXEL_SHAPE_W;
            default:
                return VOXEL_SHAPE_N;
        }
    }
    //Make it possible to save recordings to a book
    @Override
    public boolean hasTileEntity(BlockState state){
        return true;
    }

    @Override
    public RecorderTileEntity createTileEntity(BlockState state, IBlockReader reader){
        return new RecorderTileEntity();
    }

    @Override//Creates the blockstate property
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder){
        builder.add(HorizontalBlock.FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context){//Modifies the state of a block
        return this.defaultBlockState().setValue(HorizontalBlock.FACING, context.getHorizontalDirection().getOpposite());
    }//BlockStates are booleans

    @Override
    public void onBlockExploded(BlockState state, World world, BlockPos pos, Explosion explosion) {
        TileEntity tileEntity = world.getBlockEntity(pos);
        if (tileEntity instanceof RecorderTileEntity) {
            //Saving the message Number
            int message_number = tileEntity.getTileData().getInt("message_number");
            ItemStack tilestack = new ItemStack(Register.RECORDER_ITEM.get());
            if (message_number != 0) {
                CompoundNBT nbt1 = new CompoundNBT();
                tilestack.addTagElement("message_number", nbt1);//Creating CompoundNBT and assigning the key to it
                tilestack.getTagElement("message_number").putInt("message_number", message_number);
            }


            for (int i = 0; i < message_number; i++) {
                String key = "message_" + String.valueOf(i + 1);//very important <--
                String recording = tileEntity.getTileData().getString(key);
                CompoundNBT nbt = new CompoundNBT();
                tilestack.addTagElement("recordings", nbt);
                tilestack.getTagElement("recordings").putString(key, recording);

            }


            ItemEntity tilestackentity = new ItemEntity(tileEntity.getLevel(), pos.getX(), pos.getY(), pos.getZ());
            tilestackentity.setItem(tilestack);
            tileEntity.setRemoved();
            world.addFreshEntity(tilestackentity);

        }
        super.onBlockExploded(state, world,pos,explosion);
    }

    @Override
    public void onRemove(BlockState state, World world, BlockPos pos, BlockState state2, boolean p_196243_5_){
        TileEntity tileEntity = world.getBlockEntity(pos);
        if (tileEntity instanceof RecorderTileEntity) {
            //Saving the message Number
            int message_number = tileEntity.getTileData().getInt("message_number");
            ItemStack tilestack = new ItemStack(Register.RECORDER_ITEM.get());
            if(message_number!= 0) {
                CompoundNBT nbt1 = new CompoundNBT();
                tilestack.addTagElement("message_number", nbt1);//Creating CompoundNBT and assigning the key to it
                tilestack.getTagElement("message_number").putInt("message_number", message_number);
            }


            for(int i = 0; i < message_number; i++) {
            String key = "message_"+String.valueOf(i+1);//very important <--
            String recording = tileEntity.getTileData().getString(key);
            CompoundNBT nbt = new CompoundNBT();
            tilestack.addTagElement("recordings", nbt);
            tilestack.getTagElement("recordings").putString(key, recording);

            }



            ItemEntity tilestackentity = new ItemEntity(tileEntity.getLevel(), pos.getX(), pos.getY(), pos.getZ());
            tilestackentity.setItem(tilestack);
            tileEntity.setRemoved();
            world.addFreshEntity(tilestackentity);

    }
}
    }
