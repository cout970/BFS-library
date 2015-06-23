package com.cout970.bfs;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BFSTagList extends BFSBase{

	private List<BFSBase> tagList = new ArrayList<BFSBase>();
	private byte tagType = 0;
	
	BFSTagList(){}
	
	@Override
	public byte getID() {
		return (byte)9;
	}

	@Override
	public BFSBase copy() {
		BFSTagList list = new BFSTagList();
		list.tagType = tagType;
		Iterator<BFSBase> it = tagList.iterator();
		while(it.hasNext()){
			BFSBase bfs = (BFSBase) it.next();
			BFSBase bfs2 = bfs.copy();
			list.tagList.add(bfs2);
		}
		return list;
	}

	@Override
	public String getName() {
		String s = "[";
		int i = 0;
		
		for(Iterator<BFSBase> it = tagList.iterator(); it.hasNext(); i++){
			BFSBase bfs = it.next();
			s = s + "" + i + ':' + bfs + ',';
		}
		return s +"]";
	}

	@Override
	public void write(DataOutput container) throws IOException {
		if(!tagList.isEmpty()){
			tagType = ((BFSBase)tagList.get(0)).getID();
		}else{
			tagType = 0;
		}
		
		container.writeByte(tagType);
		container.writeInt(tagList.size());
		for(int i = 0; i < tagList.size(); i++){
			((BFSBase)tagList.get(i)).write(container);
		}
	}

	@Override
	public void read(DataInput container, int depth, BFSSizeTraker bfsS) throws IOException {
		if(depth > 512){
			throw new RuntimeException("Intento de lectura de una BFS demasiado profunda depth > 512");
		}else{
			bfsS.allocate(8);
			tagType = container.readByte();
			bfsS.allocate(32);
			int tam = container.readInt();
			for(int i = 0; i < tam; i++){
				bfsS.allocate(32);
				BFSBase tag = BFSBase.createTag(tagType);
				tag.read(container, depth+1, bfsS);
				tagList.add(tag);
			}
		}
	}

	public void appendTag(BFSBase bfs){
		if(tagType == 0){
			tagType = bfs.getID();
		}else if(tagType != bfs.getID()){
			System.err.println("WARNING: Se esta añadiendo un elemento a una TagList incompatible con el tipo de la lista");
			return;
		}
		tagList.add(bfs);
	}
	
	public void setTag(int pos, BFSBase bfs){
		if(pos >= 0 && pos < tagList.size()){
			if(tagType == 0){
				tagType = bfs.getID();
			}else if(tagType != bfs.getID()){
				System.err.println("WARNING: Se esta añadiendo un elemento a una TagList incompatible con el tipo de la lista");
				return;
			}
			tagList.set(pos, bfs);
		}else{
			System.err.println("WARNING: index out of bounds");
		}
	}
	
	public BFSBase removeTag(int pos){
		return tagList.remove(pos);
	}
	
	public BFSTagCompound getCompoundTagAt(int pos){
		if(pos >= 0 && pos < tagList.size()){
			BFSBase bfs = tagList.get(pos);
			return bfs.getID() == 10 ? (BFSTagCompound) bfs : new BFSTagCompound(); 
		}else{
			return new BFSTagCompound();
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
			BFSBase bfs = tagList.get(pos);
			return bfs.toString();
		}else{
			return "";
		}
	}
	
	public boolean equals(Object obj) {
        if (super.equals(obj)){
           
        	BFSTagList bfs = (BFSTagList)obj;

            if (this.tagType == bfs.tagType){
            	
                return tagList.equals(bfs.tagList);
            }
        }

        return false;
    }
	
	public int hashCode(){
        return super.hashCode() ^ tagList.hashCode();
    }
}
