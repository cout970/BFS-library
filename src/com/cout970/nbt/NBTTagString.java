package com.cout970.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagString extends NBTBase{

	private String texto;
	
	NBTTagString(){}
	
	public NBTTagString(String tex){
		texto = tex;
		if(tex == null){
			throw new IllegalStateException("Aqui no puede haber un string vacio");
		}
	}
	
	@Override
	public byte getID() {
		return (byte)8;
	}

	@Override
	public NBTBase copy() {
		return new NBTTagString(texto);
	}

	@Override
	public String getName() {
		return "\"" + this.texto + "\"";
	}

	@Override
	public void write(DataOutput container) throws IOException {
		container.writeUTF(texto);
	}

	@Override
	public void read(DataInput container, int a, NBTSizeTraker nbtS) throws IOException {
		this.texto = container.readUTF();
		NBTSizeTraker.readUTF(nbtS, texto);
	}
	
	public boolean equals(Object obj){

		if(super.equals(obj)){
			
			NBTTagString nbt = ((NBTTagString) obj);
			
			return this.texto == null && nbt.texto == null || this.texto != null && this.texto.equals(nbt.texto);
			
		}else return false;
	}
	
	public int hashCode(){
		return super.hashCode() ^ texto.hashCode();
	}

	public String getRawString(){
		return texto;
	}

}
