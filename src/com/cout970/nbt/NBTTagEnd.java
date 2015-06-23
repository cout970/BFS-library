package com.cout970.nbt;

import java.io.DataInput;
import java.io.DataOutput;

public class NBTTagEnd extends NBTBase{

	public byte getID(){
		return (byte)0;
	}
	
	@Override
	public NBTBase copy() {
		return new NBTTagEnd();
	}
	
	public String getName(){
		return "END";
	}

	@Override
	public void write(DataOutput container) {}

	@Override
	public void read(DataInput container, int a, NBTSizeTraker nbtS) {}

}
