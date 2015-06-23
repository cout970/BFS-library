package com.cout970.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagDouble extends NBTBase implements INBTPrimitive{

	private double numero;
	
	NBTTagDouble(){}
	
	public NBTTagDouble(double num){
		numero = num;
	}
	
	@Override
	public byte getID() {
		return (byte)6;
	}

	@Override
	public NBTBase copy() {
		return new NBTTagDouble(numero);
	}

	@Override
	public String getName() {
		return "" + this.numero + "d";
	}

	@Override
	public void write(DataOutput container) throws IOException {
		container.writeDouble(numero);
	}

	@Override
	public void read(DataInput container, int a, NBTSizeTraker nbtS) throws IOException {
		nbtS.allocate(64L);
		this.numero = container.readDouble();
	}
	
	public boolean equals(Object obj){

		if(super.equals(obj)){
			
			return this.numero == ((NBTTagDouble) obj).numero;
			
		}else return false;
	}
	
	public int hashCode(){
		long i = Double.doubleToLongBits(numero);
		return super.hashCode() ^ (int)(i ^ i >>> 32);
	}

	@Override
	public int getAsInteger() {
		return floor(numero);
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
		return (byte)(floor(numero) & 0xFF);
	}

	@Override
	public short getAsShort() {
		return (short)(floor(numero) & 0xFFFF);
	}

	private int floor(double num) {
		int toInt = (int)num; 
		return toInt < (double)toInt ? toInt - 1 : toInt;
	}

}
