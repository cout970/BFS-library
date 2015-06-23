package com.cout970.bfs;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class BFSTagFloat extends BFSBase implements IBFSPrimitive{

	private float numero;
	
	BFSTagFloat(){}
	
	public BFSTagFloat(float num){
		numero = num;
	}
	
	@Override
	public byte getID() {
		return (byte)5;
	}

	@Override
	public BFSBase copy() {
		return new BFSTagFloat(numero);
	}

	@Override
	public String getName() {
		return "" + this.numero + "f";
	}

	@Override
	public void write(DataOutput container) throws IOException {
		container.writeFloat(numero);
	}

	@Override
	public void read(DataInput container, int a, BFSSizeTraker bfsS) throws IOException {
		bfsS.allocate(32L);
		this.numero = container.readFloat();
	}
	
	public boolean equals(Object obj){

		if(super.equals(obj)){
			
			return this.numero == ((BFSTagFloat) obj).numero;
			
		}else return false;
	}
	
	public int hashCode(){
		return super.hashCode() ^ Float.floatToIntBits(numero);
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

	private int floor(float num) {
		int toInt = (int)num; 
		return toInt < (float)toInt ? toInt - 1 : toInt;
	}

}
