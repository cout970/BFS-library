package com.cout970.bfs;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class BFSTagString extends BFSBase{

	private String texto;
	
	BFSTagString(){}
	
	public BFSTagString(String tex){
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
	public BFSBase copy() {
		return new BFSTagString(texto);
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
	public void read(DataInput container, int a, BFSSizeTraker bfsS) throws IOException {
		this.texto = container.readUTF();
		BFSSizeTraker.readUTF(bfsS, texto);
	}
	
	public boolean equals(Object obj){

		if(super.equals(obj)){
			
			BFSTagString bfs = ((BFSTagString) obj);
			
			return this.texto == null && bfs.texto == null || this.texto != null && this.texto.equals(bfs.texto);
			
		}else return false;
	}
	
	public int hashCode(){
		return super.hashCode() ^ texto.hashCode();
	}

	public String getRawString(){
		return texto;
	}

}
