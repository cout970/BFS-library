package com.cout970.nbt.main;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

import com.cout970.nbt.NBTSizeTraker;
import com.cout970.nbt.NBTTagCompound;

public class InitTest {

	public static void main(String[] args) {
		String path = System.getProperty("user.home");
		File f = new File(path+"/tests/save.bfs");
		DataOutputStream output; 
		//se crea el NBT
		NBTTagCompound nbt = new NBTTagCompound();
		//se guarda un valor
		nbt.setByte("Byte", (byte)65);
		nbt.setShort("Short", (short)3840);
		nbt.setInteger("Int", 80);
		nbt.setLong("Long", 81L);
		nbt.setFloat("Float", 1.5f);
		nbt.setDouble("Double", Math.PI);
		nbt.setByteArray("ByteArray", new byte[]{0,1,2,3,4,5,6,7,8,9,0});
		nbt.setString("Name", "cout970");
		
		NBTTagCompound nbt2 = new NBTTagCompound();
		nbt2.setInteger("Test", 42);
		nbt.setTag("NBTTag", nbt2);
		
		nbt.setIntegerArray("IntArray", new int[]{1,2,3,4,5,6});

		try {
			//se escriben los datos en el archivo
			output = new DataOutputStream(new FileOutputStream(f));
			nbt.write(output);
			output.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		DataInputStream input; 
		//se resetea el NBT
		nbt = new NBTTagCompound();

		try {
			//se lee el archivo
			input = new DataInputStream(new FileInputStream(f));
			nbt.read(input, 0, new NBTSizeTraker(1024));
			input.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//se recuperan los datos del NBT
		System.out.println(nbt.getByte("Byte"));
		System.out.println(nbt.getShort("Short"));
		System.out.println(nbt.getInteger("Int"));
		System.out.println(nbt.getLong("Long"));
		System.out.println(nbt.getFloat("Float"));
		System.out.println(nbt.getDouble("Double"));
		System.out.println(Arrays.toString(nbt.getByteArray("ByteArray")));
		System.out.println(nbt.getString("Name"));
		
		NBTTagCompound nbt3 = nbt.getNBTTagCompound("NBTTag");
		System.out.println(nbt3.getInteger("Test"));
		
		System.out.println(Arrays.toString(nbt.getIntegerArray("IntArray")));
	}

}
