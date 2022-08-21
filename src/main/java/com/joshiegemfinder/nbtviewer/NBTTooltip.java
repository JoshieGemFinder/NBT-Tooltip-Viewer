package com.joshiegemfinder.nbtviewer;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = NBTTooltip.MODID, useMetadata=true, name = NBTTooltip.NAME, version = NBTTooltip.VERSION)
@EventBusSubscriber(modid = NBTTooltip.MODID)
public class NBTTooltip
{
    public static final String MODID = "nbttooltipviewer";
    public static final String NAME = "NBT Tooltip Viewer";
    public static final String VERSION = "1.0";
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
	public static void onTooltip(ItemTooltipEvent event) {
    	NBTTagCompound compound = event.getItemStack().serializeNBT();
    	NBTTagCompound tag = compound.getCompoundTag("tag");
    	if(tag.hasNoTags()) {return;}
    	event.getToolTip().add(TextFormatting.GREEN.toString() + Parser.serialize(tag));
    }
}
