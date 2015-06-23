package com.cout970.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;

public class NBTTagByteArray extends NBTBase {

	private byte[] array;
	
	NBTTagByteArray(){}
	
	public NBTTagByteArray(byte[] arr){
		array = arr;
	}
	
	@Override
	public byte getID() {
		return (byte)7;
	}

	@Override
	public NBTBase copy() {
		byte[] abyte = new byte[array.length];
		System.arraycopy(array, 0, abyte, 0, array.length);
		return new NBTTagByteArray(abyte);
	}

	@Override
	public String getName() {
		return "[" + this.array.length + " bytes]";
	}

	@Override
	public void write(DataOutput container) throws IOException {
		container.writeInt(array.length);
		container.write(array);
	}

	@Override
	public void read(DataInput container, int a, NBTSizeTraker nbtS) throws IOException {
		nbtS.allocate(32L);
		int tam = container.readInt();
		nbtS.allocate(8 * tam);
		array = new byte[tam];
		container.readFully(array);
	}
	
	public boolean equals(Object obj){

		if(super.equals(obj)){
			
			return Arrays.equals(array, ((NBTTagByteArray)obj).array);
			
		}else return false;
	}
	
	public int hashCode(){
		return super.hashCode() ^ Arrays.hashCode(array);
	}
	
	public byte[] getRawArray(){
		return array;
	}
}
