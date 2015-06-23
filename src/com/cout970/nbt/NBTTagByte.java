package com.cout970.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagByte extends NBTBase implements INBTPrimitive{

	private byte numero;
	
	NBTTagByte(){}
	
	public NBTTagByte(byte num){
		numero = num;
	}
	
	@Override
	public byte getID() {
		return (byte)1;
	}

	@Override
	public NBTBase copy() {
		return new NBTTagByte(numero);
	}

	@Override
	public String getName() {
		return "" + this.numero + "b";
	}

	@Override
	public void write(DataOutput container) throws IOException {
		container.writeByte(numero);
	}

	@Override
	public void read(DataInput container, int a, NBTSizeTraker nbtS) throws IOException {
		nbtS.allocate(8L);
		this.numero = container.readByte();
	}
	
	public boolean equals(Object obj){

		if(super.equals(obj)){
			
			return this.numero == ((NBTTagByte) obj).numero;
			
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
