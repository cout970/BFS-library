package com.cout970.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NBTTagList extends NBTBase{

	private List<NBTBase> tagList = new ArrayList<NBTBase>();
	private byte tagType = 0;
	
	NBTTagList(){}
	
	@Override
	public byte getID() {
		return (byte)9;
	}

	@Override
	public NBTBase copy() {
		NBTTagList list = new NBTTagList();
		list.tagType = tagType;
		Iterator<NBTBase> it = tagList.iterator();
		while(it.hasNext()){
			NBTBase nbt = (NBTBase) it.next();
			NBTBase nbt2 = nbt.copy();
			list.tagList.add(nbt2);
		}
		return list;
	}

	@Override
	public String getName() {
		String s = "[";
		int i = 0;
		
		for(Iterator<NBTBase> it = tagList.iterator(); it.hasNext(); i++){
			NBTBase nbt = it.next();
			s = s + "" + i + ':' + nbt + ',';
		}
		return s +"]";
	}

	@Override
	public void write(DataOutput container) throws IOException {
		if(!tagList.isEmpty()){
			tagType = ((NBTBase)tagList.get(0)).getID();
		}else{
			tagType = 0;
		}
		
		container.writeByte(tagType);
		container.writeInt(tagList.size());
		for(int i = 0; i < tagList.size(); i++){
			((NBTBase)tagList.get(i)).write(container);
		}
	}

	@Override
	public void read(DataInput container, int depth, NBTSizeTraker nbtS) throws IOException {
		if(depth > 512){
			throw new RuntimeException("Intento de lectura de una NBT demasiado profunda depth > 512");
		}else{
			nbtS.allocate(8);
			tagType = container.readByte();
			nbtS.allocate(32);
			int tam = container.readInt();
			for(int i = 0; i < tam; i++){
				nbtS.allocate(32);
				NBTBase tag = NBTBase.createTag(tagType);
				tag.read(container, depth+1, nbtS);
				tagList.add(tag);
			}
		}
	}

	public void appendTag(NBTBase nbt){
		if(tagType == 0){
			tagType = nbt.getID();
		}else if(tagType != nbt.getID()){
			System.err.println("WARNING: Se esta añadiendo un elemento a una TagList incompatible con el tipo de la lista");
			return;
		}
		tagList.add(nbt);
	}
	
	public void setTag(int pos, NBTBase nbt){
		if(pos >= 0 && pos < tagList.size()){
			if(tagType == 0){
				tagType = nbt.getID();
			}else if(tagType != nbt.getID()){
				System.err.println("WARNING: Se esta añadiendo un elemento a una TagList incompatible con el tipo de la lista");
				return;
			}
			tagList.set(pos, nbt);
		}else{
			System.err.println("WARNING: index out of bounds");
		}
	}
	
	public NBTBase removeTag(int pos){
		return tagList.remove(pos);
	}
	
	public NBTTagCompound getCompoundTagAt(int pos){
		if(pos >= 0 && pos < tagList.size()){
			NBTBase nbt = tagList.get(pos);
			return nbt.getID() == 10 ? (NBTTagCompound) nbt : new NBTTagCompound(); 
		}else{
			return new NBTTagCompound();
		}
	}
	
	public int tagCount(){
		return tagList.size();
	}

	public int getTagType() {
		return tagType;
	}
	
	public String getStringTagAt(int pos){
		if(pos >= 0 && pos < tagList.size()){
			NBTBase nbt = tagList.get(pos);
			return nbt.toString();
		}else{
			return "";
		}
	}
	
	public boolean equals(Object obj) {
        if (super.equals(obj)){
           
        	NBTTagList nbt = (NBTTagList)obj;

            if (this.tagType == nbt.tagType){
            	
                return tagList.equals(nbt.tagList);
            }
        }

        return false;
    }
	
	public int hashCode(){
        return super.hashCode() ^ tagList.hashCode();
    }
}
