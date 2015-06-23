package com.cout970.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public abstract class NBTBase{

	public static final String[] NBTTypes = new String[]{"END", "BYTE", "SHORT", "INT", "LONG", "FLOAT", "DOUBLE", "BYTE[]", "STRING", "LIST", "COMPOUND", "INT[]"};

	protected static NBTBase createTag(byte id)
    {
        switch (id)
        {
            case 0:
                return new NBTTagEnd();
            case 1:
                return new NBTTagByte();
            case 2:
                return new NBTTagShort();
            case 3:
                return new NBTTagInt();
            case 4:
                return new NBTTagLong();
            case 5:
                return new NBTTagFloat();
            case 6:
                return new NBTTagDouble();
            case 7:
                return new NBTTagByteArray();
            case 8:
                return new NBTTagString();
            case 9:
                return new NBTTagList();
            case 10:
                return new NBTTagCompound();
            case 11:
                return new NBTTagIntArray();
            default:
                return null;
        }
    }
	
	public boolean equals(Object obj){
		if(obj instanceof NBTBase){
			
			return this.getID() == ((NBTBase) obj).getID();
			
		}else return false;
	}
	
	public abstract byte getID();
	
	public abstract NBTBase copy();
	
	public abstract String getName();
	
	public abstract void write(DataOutput container) throws IOException;
	
	public abstract void read(DataInput container, int depth, NBTSizeTraker nbtS) throws IOException;
	
}
