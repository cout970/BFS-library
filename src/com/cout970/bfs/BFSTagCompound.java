package com.cout970.bfs;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class BFSTagCompound extends BFSBase{

	private Map<String, BFSBase> tagMap = new HashMap<String, BFSBase>();

	public void setByteArray(String key, byte[] array){
		tagMap.put(key, new BFSTagByteArray(array));
	}

	public void setIntegerArray(String key, int[] array){
		tagMap.put(key, new BFSTagIntArray(array));
	}

	public void setInteger(String key, int value){
		tagMap.put(key, new BFSTagInt(value));
	}

	public void setLong(String key, long value){
		tagMap.put(key, new BFSTagLong(value));
	}

	public void setFloat(String key, float value){
		tagMap.put(key, new BFSTagFloat(value));
	}

	public void setDouble(String key, double value){
		tagMap.put(key, new BFSTagDouble(value));
	}

	public void setString(String key, String value){
		tagMap.put(key, new BFSTagString(value));
	}

	public void setByte(String key, byte value){
		tagMap.put(key, new BFSTagByte(value));
	}

	public void setShort(String key, short value){
		tagMap.put(key, new BFSTagShort(value));
	}

	public void setTag(String key, BFSBase bfs){
		tagMap.put(key, bfs);
	}

	public void setBoolean(String key, boolean value){
		tagMap.put(key, new BFSTagByte(value ? (byte)1 : (byte)0));
	}

	public byte getIdByKey(String str){
		BFSBase bfs = tagMap.get(str);
		return bfs == null ? 0 : bfs.getID();
	}

	public boolean hasKey(String key){
		return tagMap.containsKey(key);
	}
	
	public boolean hasKey(String key, int ID){
		return ID == getIdByKey(key);
	}

	public void removeTag(String key){
		tagMap.remove(key);
	}
	
	public Set<String> getKeys(){
		return tagMap.keySet();
	}

	@Override
	public byte getID() {
		return (byte)10;
	}
	@Override
	public BFSBase copy() {
		BFSTagCompound bfs = new BFSTagCompound();
		Iterator<String> iterator = this.tagMap.keySet().iterator();

		while (iterator.hasNext()) {
			String s = (String)iterator.next();
			bfs.setTag(s, ((BFSBase)this.tagMap.get(s)).copy());
		}

		return bfs;
	}

	@Override
	public String getName() {
		String s = "{";
		String s1;
		for (Iterator<String> iterator = tagMap.keySet().iterator(); iterator.hasNext(); s = s + s1 + ':' + this.tagMap.get(s1) + ','){

			s1 = (String)iterator.next();
		}
		return s + "}";
	}

	@Override
	public void write(DataOutput container) throws IOException {
		Iterator<String> it = tagMap.keySet().iterator();
		while(it.hasNext()){
			String s = it.next();
			BFSBase bfs = tagMap.get(s);
			container.writeByte(bfs.getID());
			if(bfs.getID() != 0){
				container.writeUTF(s);
				bfs.write(container);
			}
		}
		container.writeByte(0);
	}

	@Override
	public void read(DataInput container, int depth, BFSSizeTraker bfsS) throws IOException {
		if(depth > 512){
			throw new RuntimeException("Intento de lectura de una BFS demasiado profunda depth > 512");
		}else{
			tagMap.clear();
			byte bit;
			
			while((bit = readByte(container, bfsS)) != 0){
				String s = container.readUTF();
				BFSSizeTraker.readUTF(bfsS, s);
				bfsS.allocate(32);
				BFSBase bfs = BFSBase.createTag(bit);
				bfs.read(container, depth+1, bfsS);
				tagMap.put(s, bfs);
			}
		}
	}

	private byte readByte(DataInput container, BFSSizeTraker bfsS) throws IOException {
		bfsS.allocate(8);
		return container.readByte();
	}

	public byte[] getByteArray(String key){
		try{
			return tagMap.containsKey(key) ? ((BFSTagByteArray)tagMap.get(key)).getRawArray() : new byte[0];
		}catch(ClassCastException exc){
			return new byte[0];
		}
	}

	public int[] getIntegerArray(String key){
		try{
			return tagMap.containsKey(key) ? ((BFSTagIntArray)tagMap.get(key)).getRawArray() : new int[0];
		}catch(ClassCastException exc){
			return new int[0];
		}
	}

	public int getInteger(String key){
		try{
			return tagMap.containsKey(key) ? ((IBFSPrimitive)tagMap.get(key)).getAsInteger() : (int)0;
		}catch(ClassCastException exception){
			return (int)0;
		}
	}

	public long getLong(String key){
		try{
			return tagMap.containsKey(key) ? ((IBFSPrimitive)tagMap.get(key)).getAsLong() : (long)0;
		}catch(ClassCastException exception){
			return (long)0;
		}
	}

	public float getFloat(String key){
		try{
			return tagMap.containsKey(key) ? ((IBFSPrimitive)tagMap.get(key)).getAsFloat() : (float)0;
		}catch(ClassCastException exception){
			return (float)0;
		}
	}

	public double getDouble(String key){
		try{
			return tagMap.containsKey(key) ? ((IBFSPrimitive)tagMap.get(key)).getAsDouble() : (double)0;
		}catch(ClassCastException exception){
			return (double)0;
		}
	}

	public String getName(String key){
		try{
			return tagMap.containsKey(key) ? tagMap.get(key).getName() : "";
		}catch(ClassCastException exception){
			return "";
		}
	}
	
	public String getString(String key){
		try{
			return tagMap.containsKey(key) ? ((BFSTagString)tagMap.get(key)).getRawString() : "";
		}catch(ClassCastException exception){
			return "";
		}
	}

	public byte getByte(String key){
		try{
			return tagMap.containsKey(key) ? ((IBFSPrimitive)tagMap.get(key)).getAsByte() : (byte)0;
		}catch(ClassCastException exception){
			return (byte)0;
		}
	}

	public short getShort(String key){
		try{
			return tagMap.containsKey(key) ? ((IBFSPrimitive)tagMap.get(key)).getAsShort() : (short)0;
		}catch(ClassCastException exception){
			return (short)0;
		}
	}
	
	public BFSTagCompound getBFSTagCompound(String key){
		try{
			return tagMap.containsKey(key) ? (BFSTagCompound)(tagMap.get(key)) : new BFSTagCompound();
		}catch(ClassCastException exception){
			return new BFSTagCompound();
		}
	}
	
	public BFSTagList getTagList(String key, int id){
		try{
			if(this.getIdByKey(key) != 9){
				return new BFSTagList();
			}else{
				BFSTagList list = (BFSTagList)(tagMap.get(key));
				return list.tagCount() > 0 && list.getTagType() == id ? new BFSTagList() : list;
			}
		}catch(ClassCastException exception){
			return new BFSTagList();
		}
	}


	public BFSBase getTag(String key){
		return tagMap.get(key);
	}

	public boolean getBoolean(String key){
		try{
			return tagMap.containsKey(key) ? ((IBFSPrimitive)tagMap.get(key)).getAsByte() == 1 : false;
		}catch(ClassCastException exception){
			return false;
		}
	}

	public boolean hasNoTags(){
		return tagMap.isEmpty();
	}

	public boolean equals(Object obj){
		if (super.equals(obj)) {
			BFSTagCompound bfs = (BFSTagCompound)obj;
			return this.tagMap.entrySet().equals(bfs.tagMap.entrySet());
		}else{
			return false;
		}
	}
}
