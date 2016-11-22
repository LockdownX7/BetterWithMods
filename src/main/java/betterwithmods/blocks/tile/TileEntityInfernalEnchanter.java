package betterwithmods.blocks.tile;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by tyler on 9/11/16.
 */
public class TileEntityInfernalEnchanter extends TileEntity implements ITickable {
    private int bookcaseCount;
    private final int radius = 8;
    @Override
    public void update() {

        if(worldObj.getTotalWorldTime()%20==0) {
            bookcaseCount = 0;
            for (int x = -radius; x <= radius; x++) {
                for (int y = -radius; y <= radius; y++) {
                    for (int z = -radius; z <= radius; z++) {
                        BlockPos pos = getPos().add(x, y, z);
                        bookcaseCount += (ForgeHooks.getEnchantPower(worldObj, pos) > 0) ? 1 : 0;
                    }
                }
            }

            //required

            // bookcase * item.enchants + 1

        }
        if(worldObj.getTotalWorldTime()%5==0) {
            int x = pos.getX(), y = pos.getY(), z = pos.getZ();
            worldObj.spawnParticle(EnumParticleTypes.FLAME, x + .125, y + .9, z + .125, 0, 0, 0);
            worldObj.spawnParticle(EnumParticleTypes.FLAME, x + .875, y + .9, z + .125, 0, 0, 0);
            worldObj.spawnParticle(EnumParticleTypes.FLAME, x + .875, y + .9, z + .875, 0, 0, 0);
            worldObj.spawnParticle(EnumParticleTypes.FLAME, x + .125, y + .9, z + .875, 0, 0, 0);
        }

    }

    public int getBookcaseCount() {
        return bookcaseCount;
    }

    public void setBookcaseCount(int bookcaseCount) {
        this.bookcaseCount = bookcaseCount;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setInteger("bookcaseCount",bookcaseCount);
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        bookcaseCount = compound.getInteger("bookcaseCount");
        super.readFromNBT(compound);
    }


    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound tag = getUpdateTag();
        return new SPacketUpdateTileEntity(this.getPos(), getBlockMetadata(), tag);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onDataPacket(NetworkManager mgr, SPacketUpdateTileEntity pkt) {
        NBTTagCompound tag = pkt.getNbtCompound();
        readFromNBT(tag);
        IBlockState state = worldObj.getBlockState(this.pos);
        worldObj.notifyBlockUpdate(this.pos, state, state, 3);
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        return writeToNBT(new NBTTagCompound());
    }

    public String getName() {
        return "bwm.infernalenchanter";
    }
}
