package com.joshiegemfinder.nbtviewer;

import java.util.Collection;
import java.util.regex.Pattern;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagByteArray;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagLongArray;
import net.minecraft.nbt.NBTTagString;

public class Parser {
	
	public static String valueOf(NBTBase nbt) {
		if(nbt instanceof NBTTagByteArray || nbt instanceof NBTTagIntArray || nbt instanceof NBTTagLongArray) {
			//this is okay because these will be strictly number primitives in a strict format
			return nbt.toString().replaceAll(",", ", ");
		} else if(nbt instanceof NBTTagList) {
	        NBTTagList list = ((NBTTagList)nbt);
	        if(list.hasNoTags()) {
	        	return "[]";
	        }
	        
	        StringBuilder stringbuilder = new StringBuilder("[");
	        
	        int size = list.tagCount();
	        for (int i = 0; i < size; ++i)
	        {
	            if (i != 0)
	            {
	                stringbuilder.append(", ");
	            }

	            stringbuilder.append(valueOf(list.get(i)));
	        }

	        return stringbuilder.append(']').toString();
		}
		else if(nbt instanceof NBTTagCompound) {
			return serialize((NBTTagCompound)nbt);
		} else {
			return nbt.toString();
		}
	}
	
	public static String serialize(NBTTagCompound nbt) {
        StringBuilder stringbuilder = new StringBuilder("{");
        Collection<String> collection = nbt.getKeySet();

        for (String s : collection)
        {
            if (stringbuilder.length() != 1)
            {
                stringbuilder.append(", ");
            }

            stringbuilder.append(handleEscape(s)).append(": ").append(valueOf(nbt.getTag(s)));
        }
        return stringbuilder.append('}').toString();
	}
	
	public static final Pattern SIMPLE_VALUE_PATTERN = Pattern.compile("[A-Za-z0-9._+-]+");
	
    public static String handleEscape(String str)
    {
        return SIMPLE_VALUE_PATTERN.matcher(str).matches() ? str : NBTTagString.quoteAndEscape(str);
    }
}
