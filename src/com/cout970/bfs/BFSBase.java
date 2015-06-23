package com.cout970.bfs;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public abstract class BFSBase{

	public static final String[] BFSTypes = new String[]{"END", "BYTE", "SHORT", "INT", "LONG", "FLOAT", "DOUBLE", "BYTE[]", "STRING", "LIST", "COMPOUND", "INT[]"};

	protected static BFSBase createTag(byte id)
    {
        switch (id)
        {
            case 0:
                return new BFSTagEnd();
            case 1:
                return new BFSTagByte();
            case 2:
                return new BFSTagShort();
            case 3:
                return new BFSTagInt();
            case 4:
                return new BFSTagLong();
            case 5:
                return new BFSTagFloat();
            case 6:
                return new BFSTagDouble();
            case 7:
                return new BFSTagByteArray();
            case 8:
                return new BFSTagString();
            case 9:
                return new BFSTagList();
            case 10:
                return new BFSTagCompound();
            case 11:
                return new BFSTagIntArray();
            default:
                return null;
        }
    }
	
	public boolean equals(Object obj){
		if(obj instanceof BFSBase){
			
			return this.getID() == ((BFSBase) obj).getID();
			
		}else return false;
	}
	
	public abstract byte getID();
	
	public abstract BFSBase copy();
	
	public abstract String getName();
	
	public abstract void write(DataOutput container) throws IOException;
	
	public abstract void read(DataInput container, int depth, BFSSizeTraker bfsS) throws IOException;
	
}
