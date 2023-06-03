package me.Coolfish20.Encryptor.items;

import me.Coolfish20.Encryptor.init.Register;
import me.Coolfish20.Encryptor.tileentities.RecorderTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.CooldownTracker;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class RecorderTapeItem extends Item {
    public RecorderTapeItem(Properties p_i48487_1_) {
        super(p_i48487_1_);
    }


   @Override
   public ActionResultType useOn(ItemUseContext context){
        if(!context.getLevel().isClientSide) {
            ItemStack tape_stack = context.getItemInHand();
            if (context.getLevel().getBlockEntity(context.getClickedPos()) instanceof RecorderTileEntity) {
                TileEntity recorder = context.getLevel().getBlockEntity(context.getClickedPos());
                if (tape_stack.getTagElement("hasRecording") == null) {//Fix this line
                    tape_stack.addTagElement("recordings", new CompoundNBT());
                    if (recorder.getTileData().getInt("message_number") > 0) {
                        int message_number = recorder.getTileData().getInt("message_number");
                        tape_stack.addTagElement("message_number", new CompoundNBT());
                        tape_stack.getTagElement("message_number").putInt("number", message_number);
                        String key = null;
                        for (int i = 0; i < message_number; i++)
                            key = "message_" + String.valueOf(i + 1);
                        tape_stack.getTagElement("recordings").putString(key, recorder.getTileData().getString(key));
                    }

                    tape_stack.addTagElement("hasRecording", new CompoundNBT());
                    tape_stack.getTagElement("hasRecording").putString("recording", "true");
                }
                return ActionResultType.PASS;
            }

        }
       return ActionResultType.PASS;
   }

   @Override
   public ActionResult<ItemStack> use(World world, PlayerEntity p, Hand hand){
        if(!world.isClientSide){
            String key = null;
            try {
                if (p.getMainHandItem().getItem() == Register.RECORDER_TAPE_ITEM.get()) {
                    ItemStack tape_stack = p.getMainHandItem();
                if(tape_stack.getTagElement("hasRecording") != null) {//Fix NPE
                    int message_number = tape_stack.getTagElement("message_number").getInt("number");
                    Minecraft mc = Minecraft.getInstance();
                    mc.player.sendMessage(ITextComponent.nullToEmpty(TextFormatting.GOLD+"---Recordings---"), p.getUUID());
                    for (int i = 0; i < message_number; i++) {
                        ITextComponent component = ITextComponent.nullToEmpty(tape_stack.getTagElement("recordings").getString("message_" + String.valueOf(i + 1)));
                        mc.player.sendMessage(component, p.getUUID());
                    }
                    CooldownTracker tracker = new CooldownTracker();
                    tracker.addCooldown(tape_stack.getItem(), 10);
                }
                    return ActionResult.pass(tape_stack);
                } else {
                    ItemStack tape_stack2 = p.getOffhandItem();
                    if(tape_stack2.getTagElement("hasRecording") != null) {
                        int message_number = tape_stack2.getTagElement("message_number").getInt("number");
                        Minecraft mc = Minecraft.getInstance();
                        mc.player.sendMessage(ITextComponent.nullToEmpty(TextFormatting.GOLD+"---Recordings---"), p.getUUID());
                        for (int i = 0; i < message_number; i++) {
                            ITextComponent component = ITextComponent.nullToEmpty(tape_stack2.getTagElement("recordings").getString("message_" + String.valueOf(i + 1)));
                            mc.player.sendMessage(component, p.getUUID());
                        }
                        CooldownTracker tracker = new CooldownTracker();
                        tracker.addCooldown(tape_stack2.getItem(), 10);
                    }
                    return ActionResult.pass(tape_stack2);
                }
            }catch(Exception e){
                    e.printStackTrace();
                }



        }

       return ActionResult.pass(p.getMainHandItem());
   }

}
