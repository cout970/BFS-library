package com.cout970.bfs;

import java.io.DataInput;
import java.io.DataOutput;

public class BFSTagEnd extends BFSBase{

	public byte getID(){
		return (byte)0;
	}
	
	@Override
	public BFSBase copy() {
		return new BFSTagEnd();
	}
	
	public String getName(){
		return "END";
	}

	@Override
	public void write(DataOutput container) {}

	@Override
	public void read(DataInput container, int a, BFSSizeTraker bfsS) {}

}
