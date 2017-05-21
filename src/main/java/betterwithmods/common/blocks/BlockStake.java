package betterwithmods.common.blocks;

import betterwithmods.common.blocks.tile.TileStake;
import betterwithmods.util.DirUtils;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nullable;

/**
 * Created by tyler on 5/17/17.
 */
public class BlockStake extends BWMBlock {


    public BlockStake() {
        super(Material.WOOD);
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        return getDefaultState().withProperty(DirUtils.FACING, facing.getOpposite());
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, DirUtils.FACING);
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileStake();
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(DirUtils.FACING).getIndex();
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(DirUtils.FACING, EnumFacing.getFront(meta));
    }


    public boolean isFullCube(IBlockState state) {
        return false;
    }

    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        ItemStack heldItem = playerIn.getHeldItem(hand);
        TileEntity te = worldIn.getTileEntity(pos);
        if (te != null && playerIn != null && !playerIn.isSneaking() && heldItem.isEmpty() && te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)) {
            return ((TileStake) te).onActivated(playerIn, hand, heldItem);
        }
        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, side, hitX, hitY, hitZ);
    }
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        switch (state.getValue(DirUtils.FACING)) {

            default:
                return new AxisAlignedBB(6 / 16f, 0, 6 / 16f, 10f / 16, 12f / 16f, 10f / 16f);
            case UP:
                return new AxisAlignedBB(6 / 16f, 4/16f, 6 / 16f, 10f / 16, 1, 10f / 16f);
            case NORTH:
                return new AxisAlignedBB(6 / 16f, 6 / 16f, 12f / 16f, 10f / 16f, 10f / 16, 0);
            case SOUTH:
                return new AxisAlignedBB(6 / 16f, 6 / 16f, 4f / 16f, 10f / 16f, 10f / 16, 1);
            case WEST:
                return new AxisAlignedBB(12f / 16f, 6 / 16f, 6 / 16f, 0, 10f / 16, 10f / 16f);
            case EAST:
                return new AxisAlignedBB(4f / 16f, 6 / 16f, 6 / 16f, 1, 10f / 16, 10f / 16f);


        }
    }
}