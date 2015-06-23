package com.cout970.bfs;

public class BFSSizeTraker {

	private final long maxNumOfBits;
	private long allocatedBits;
	
	public BFSSizeTraker(long maxTam){
		maxNumOfBits = maxTam;
	}
	
	public void allocate(long numBytes){
		allocatedBits += numBytes / 8L;
		if(allocatedBits > maxNumOfBits){
			System.out.println("Error al leer un BFS demasido grande, se ha intentado alocar: "+allocatedBits+" cuando el maximo es: "+maxNumOfBits);
		}
	}
	
	public static void readUTF(BFSSizeTraker tracker, String data){
		tracker.allocate(16);//esto deja espacio para saber que es UTF, es para el Header
		if(data == null)return;
		
		int tam = data.length();
		int utfTam = 0;//tamaño teniendo el cuenta que data.length puede falla al usar UTF-8
		
		for(int i = 0; i< tam; i++){
			int c = data.charAt(i);
			if(c >= 0x0001 && c <= 0x007F){
				utfTam += 1;
			}else if(c > 0x007F){
				utfTam += 3;
			}else{
				utfTam += 2;
			}
		}
		tracker.allocate(8 * utfTam);
	}
}
