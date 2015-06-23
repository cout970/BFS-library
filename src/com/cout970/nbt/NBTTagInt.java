package com.cout970.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagInt extends NBTBase implements INBTPrimitive{

	private int numero;
	
	NBTTagInt(){}
	
	public NBTTagInt(int num){
		numero = num;
	}
	
	@Override
	public byte getID() {
		return (byte)3;
	}

	@Override
	public NBTBase copy() {
		return new NBTTagInt(numero);
	}

	@Override
	public String getName() {
		return "" + this.numero;
	}

	@Override
	public void write(DataOutput container) throws IOException {
		container.writeInt(numero);
	}

	@Override
	public void read(DataInput container, int a, NBTSizeTraker nbtS) throws IOException {
		nbtS.allocate(32L);
		this.numero = container.readInt();
	}
	
	public boolean equals(Object obj){

		if(super.equals(obj)){
			
			return this.numero == ((NBTTagInt) obj).numero;
			
		}else return false;
	}
	
	public int hashCode(){
		return super.hashCode() ^ numero;
	}

	@Override
	public int getAsInteger() {
		return numero;
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
