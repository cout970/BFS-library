package com.cout970.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class NBTTagCompound extends NBTBase{

	private Map<String, NBTBase> tagMap = new HashMap<String, NBTBase>();

	public void setByteArray(String key, byte[] array){
		tagMap.put(key, new NBTTagByteArray(array));
	}

	public void setIntegerArray(String key, int[] array){
		tagMap.put(key, new NBTTagIntArray(array));
	}

	public void setInteger(String key, int value){
		tagMap.put(key, new NBTTagInt(value));
	}

	public void setLong(String key, long value){
		tagMap.put(key, new NBTTagLong(value));
	}

	public void setFloat(String key, float value){
		tagMap.put(key, new NBTTagFloat(value));
	}

	public void setDouble(String key, double value){
		tagMap.put(key, new NBTTagDouble(value));
	}

	public void setString(String key, String value){
		tagMap.put(key, new NBTTagString(value));
	}

	public void setByte(String key, byte value){
		tagMap.put(key, new NBTTagByte(value));
	}

	public void setShort(String key, short value){
		tagMap.put(key, new NBTTagShort(value));
	}

	public void setTag(String key, NBTBase nbt){
		tagMap.put(key, nbt);
	}

	public void setBoolean(String key, boolean value){
		tagMap.put(key, new NBTTagByte(value ? (byte)1 : (byte)0));
	}

	public byte getIdByKey(String str){
		NBTBase nbt = tagMap.get(str);
		return nbt == null ? 0 : nbt.getID();
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
	public NBTBase copy() {
		NBTTagCompound nbt = new NBTTagCompound();
		Iterator<String> iterator = this.tagMap.keySet().iterator();

		while (iterator.hasNext()) {
			String s = (String)iterator.next();
			nbt.setTag(s, ((NBTBase)this.tagMap.get(s)).copy());
		}

		return nbt;
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
			NBTBase nbt = tagMap.get(s);
			container.writeByte(nbt.getID());
			if(nbt.getID() != 0){
				container.writeUTF(s);
				nbt.write(container);
			}
		}
		container.writeByte(0);
	}

	@Override
	public void read(DataInput container, int depth, NBTSizeTraker nbtS) throws IOException {
		if(depth > 512){
			throw new RuntimeException("Intento de lectura de una NBT demasiado profunda depth > 512");
		}else{
			tagMap.clear();
			byte bit;
			
			while((bit = readByte(container, nbtS)) != 0){
				String s = container.readUTF();
				NBTSizeTraker.readUTF(nbtS, s);
				nbtS.allocate(32);
				NBTBase nbt = NBTBase.createTag(bit);
				nbt.read(container, depth+1, nbtS);
				tagMap.put(s, nbt);
			}
		}
	}

	private byte readByte(DataInput container, NBTSizeTraker nbtS) throws IOException {
		nbtS.allocate(8);
		return container.readByte();
	}

	public byte[] getByteArray(String key){
		try{
			return tagMap.containsKey(key) ? ((NBTTagByteArray)tagMap.get(key)).getRawArray() : new byte[0];
		}catch(ClassCastException exc){
			return new byte[0];
		}
	}

	public int[] getIntegerArray(String key){
		try{
			return tagMap.containsKey(key) ? ((NBTTagIntArray)tagMap.get(key)).getRawArray() : new int[0];
		}catch(ClassCastException exc){
			return new int[0];
		}
	}

	public int getInteger(String key){
		try{
			return tagMap.containsKey(key) ? ((INBTPrimitive)tagMap.get(key)).getAsInteger() : (int)0;
		}catch(ClassCastException exception){
			return (int)0;
		}
	}

	public long getLong(String key){
		try{
			return tagMap.containsKey(key) ? ((INBTPrimitive)tagMap.get(key)).getAsLong() : (long)0;
		}catch(ClassCastException exception){
			return (long)0;
		}
	}

	public float getFloat(String key){
		try{
			return tagMap.containsKey(key) ? ((INBTPrimitive)tagMap.get(key)).getAsFloat() : (float)0;
		}catch(ClassCastException exception){
			return (float)0;
		}
	}

	public double getDouble(String key){
		try{
			return tagMap.containsKey(key) ? ((INBTPrimitive)tagMap.get(key)).getAsDouble() : (double)0;
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
			return tagMap.containsKey(key) ? ((NBTTagString)tagMap.get(key)).getRawString() : "";
		}catch(ClassCastException exception){
			return "";
		}
	}

	public byte getByte(String key){
		try{
			return tagMap.containsKey(key) ? ((INBTPrimitive)tagMap.get(key)).getAsByte() : (byte)0;
		}catch(ClassCastException exception){
			return (byte)0;
		}
	}

	public short getShort(String key){
		try{
			return tagMap.containsKey(key) ? ((INBTPrimitive)tagMap.get(key)).getAsShort() : (short)0;
		}catch(ClassCastException exception){
			return (short)0;
		}
	}
	
	public NBTTagCompound getNBTTagCompound(String key){
		try{
			return tagMap.containsKey(key) ? (NBTTagCompound)(tagMap.get(key)) : new NBTTagCompound();
		}catch(ClassCastException exception){
			return new NBTTagCompound();
		}
	}
	
	public NBTTagList getTagList(String key, int id){
		try{
			if(this.getIdByKey(key) != 9){
				return new NBTTagList();
			}else{
				NBTTagList list = (NBTTagList)(tagMap.get(key));
				return list.tagCount() > 0 && list.getTagType() == id ? new NBTTagList() : list;
			}
		}catch(ClassCastException exception){
			return new NBTTagList();
		}
	}


	public NBTBase getTag(String key){
		return tagMap.get(key);
	}

	public boolean getBoolean(String key){
		try{
			return tagMap.containsKey(key) ? ((INBTPrimitive)tagMap.get(key)).getAsByte() == 1 : false;
		}catch(ClassCastException exception){
			return false;
		}
	}

	public boolean hasNoTags(){
		return tagMap.isEmpty();
	}

	public boolean equals(Object obj){
		if (super.equals(obj)) {
			NBTTagCompound nbt = (NBTTagCompound)obj;
			return this.tagMap.entrySet().equals(nbt.tagMap.entrySet());
		}else{
			return false;
		}
	}
}
