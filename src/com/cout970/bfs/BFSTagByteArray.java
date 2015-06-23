package com.cout970.bfs;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;

public class BFSTagByteArray extends BFSBase {

	private byte[] array;
	
	BFSTagByteArray(){}
	
	public BFSTagByteArray(byte[] arr){
		array = arr;
	}
	
	@Override
	public byte getID() {
		return (byte)7;
	}

	@Override
	public BFSBase copy() {
		byte[] abyte = new byte[array.length];
		System.arraycopy(array, 0, abyte, 0, array.length);
		return new BFSTagByteArray(abyte);
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
	public void read(DataInput container, int a, BFSSizeTraker bfsS) throws IOException {
		bfsS.allocate(32L);
		int tam = container.readInt();
		bfsS.allocate(8 * tam);
		array = new byte[tam];
		container.readFully(array);
	}
	
	public boolean equals(Object obj){

		if(super.equals(obj)){
			
			return Arrays.equals(array, ((BFSTagByteArray)obj).array);
			
		}else return false;
	}
	
	public int hashCode(){
		return super.hashCode() ^ Arrays.hashCode(array);
	}
	
	public byte[] getRawArray(){
		return array;
	}
}
