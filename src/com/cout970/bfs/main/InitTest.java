package com.cout970.bfs.main;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

import com.cout970.bfs.BFSSizeTraker;
import com.cout970.bfs.BFSTagCompound;

public class InitTest {

	public static void main(String[] args) {
		String path = System.getProperty("user.home");
		File f = new File(path+"/tests/save.bfs");
		DataOutputStream output; 
		//se crea el BFS
		BFSTagCompound bfs = new BFSTagCompound();
		//se guarda un valor
		bfs.setByte("Byte", (byte)65);
		bfs.setShort("Short", (short)3840);
		bfs.setInteger("Int", 80);
		bfs.setLong("Long", 81L);
		bfs.setFloat("Float", 1.5f);
		bfs.setDouble("Double", Math.PI);
		bfs.setByteArray("ByteArray", new byte[]{0,1,2,3,4,5,6,7,8,9,0});
		bfs.setString("Name", "cout970");
		
		BFSTagCompound bfs2 = new BFSTagCompound();
		bfs2.setInteger("Test", 42);
		bfs.setTag("BFSTag", bfs2);
		
		bfs.setIntegerArray("IntArray", new int[]{1,2,3,4,5,6});

		try {
			//se escriben los datos en el archivo
			output = new DataOutputStream(new FileOutputStream(f));
			bfs.write(output);
			output.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		DataInputStream input; 
		//se resetea el BFS
		bfs = new BFSTagCompound();

		try {
			//se lee el archivo
			input = new DataInputStream(new FileInputStream(f));
			bfs.read(input, 0, new BFSSizeTraker(1024));
			input.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//se recuperan los datos del BFS
		System.out.println(bfs.getByte("Byte"));
		System.out.println(bfs.getShort("Short"));
		System.out.println(bfs.getInteger("Int"));
		System.out.println(bfs.getLong("Long"));
		System.out.println(bfs.getFloat("Float"));
		System.out.println(bfs.getDouble("Double"));
		System.out.println(Arrays.toString(bfs.getByteArray("ByteArray")));
		System.out.println(bfs.getString("Name"));
		
		BFSTagCompound bfs3 = bfs.getBFSTagCompound("BFSTag");
		System.out.println(bfs3.getInteger("Test"));
		
		System.out.println(Arrays.toString(bfs.getIntegerArray("IntArray")));
	}

}
