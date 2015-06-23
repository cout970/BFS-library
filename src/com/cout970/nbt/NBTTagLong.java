package com.cout970.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagLong extends NBTBase implements INBTPrimitive{

	private long numero;
	
	NBTTagLong(){}
	
	public NBTTagLong(long num){
		numero = num;
	}
	
	@Override
	public byte getID() {
		return (byte)4;
	}

	@Override
	public NBTBase copy() {
		return new NBTTagLong(numero);
	}

	@Override
	public String getName() {
		return "" + this.numero +"L";
	}

	@Override
	public void write(DataOutput container) throws IOException {
		container.writeLong(numero);
	}

	@Override
	public void read(DataInput container, int a, NBTSizeTraker nbtS) throws IOException {
		nbtS.allocate(64L);
		this.numero = container.readLong();
	}
	
	public boolean equals(Object obj){

		if(super.equals(obj)){
			
			return this.numero == ((NBTTagLong) obj).numero;
			
		}else return false;
	}
	
	public int hashCode(){
		return super.hashCode() ^ (int)(numero ^ numero >>> 32);
	}

	@Override
	public int getAsInteger() {
		return (int)numero;
	}

	@Override
	public long getAsLong() {
		return (long)numero;
	}

	@Override
	public float getAsFloat() {
		return (float)numero;
	}

	@Override
	public double getAsDouble() {
		return (double)numero;
	}

	@Override
	public byte getAsByte() {
		return (byte)(numero & 0xFF);
	}

	@Override
	public short getAsShort() {
		return (short)(numero & 0xFFFF);
	}

}
