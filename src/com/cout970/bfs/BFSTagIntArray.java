package com.cout970.bfs;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;

public class BFSTagIntArray extends BFSBase{

	private int[] array;
	
	BFSTagIntArray(){}
	
	public BFSTagIntArray(int[] arr){
		array = arr;
	}
	
	@Override
	public byte getID() {
		return (byte)11;
	}

	@Override
	public BFSBase copy() {
		int[] aint = new int[array.length];
		System.arraycopy(array, 0, aint, 0, array.length);
		return new BFSTagIntArray(aint);
	}

	@Override
	public String getName() {
		String s = "[";
		for(int i = 0; i< array.length; i++){
			s = s + array[i] + ",";
		}
		return s +"]";
	}

	@Override
	public void write(DataOutput container) throws IOException {
		container.writeInt(array.length);
		for(int i = 0; i< array.length; i++){
			container.writeInt(array[i]);
		}
	}

	@Override
	public void read(DataInput container, int depth, BFSSizeTraker bfsS) throws IOException {
		bfsS.allocate(32);
		int tam = container.readInt();
		bfsS.allocate(32 * tam);
		array = new int[tam];
		for(int i = 0; i< array.length; i++){
			array[i] = container.readInt();
		}
	}

	public boolean equals(Object obj){

		if(super.equals(obj)){
			
			return Arrays.equals(array, ((BFSTagIntArray)obj).array);
			
		}else return false;
	}
	
	public int hashCode(){
		return super.hashCode() ^ Arrays.hashCode(array);
	}
	
	public int[] getRawArray(){
		return array;
	}
}
