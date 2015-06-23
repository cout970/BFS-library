package com.cout970.bfs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

/**
 * Esta clase es Wip puede no funcionar correctamente
 * 
 * @author Cout970
 *
 */
public class JsonToBFS {

	public static BFSBase fromJsonText(String str) throws BFSException {
		str = str.trim();
		int i = getTagCount(str);

		if (i != 1){
			throw new BFSException("Se han encontrado varios tags cuando solo se esperaba uno");
		}else{
			JsonToBFS.Any any = null;

			if (str.startsWith("{")){
				any = getByKey("tag", str);
			}else{
				any = getByKey(splitValue(str, false), cutValue(str, false));
			}

			return any.getBFS();
		}
	}

	static int getTagCount(String str) throws BFSException {
		int i = 0;
		boolean flag = false;
		Stack<Character> stack = new Stack<Character>();

		for (int j = 0; j < str.length(); ++j){

			char caracter = str.charAt(j);

			if (caracter == 34){//34 caracter "
				if (j > 0 && str.charAt(j - 1) == 92){//92 caracter /
					if (!flag){
						throw new BFSException("Illegal use of \\\": " + str);
					}
				}else{
					flag = !flag;
				}
			} else if (!flag){
				if (caracter != 123 && caracter != 91){// 123 caracter {  91 carater [

					if (caracter == 125 && (stack.isEmpty() || ((Character)stack.pop()).charValue() != 123)){//125 carater }
						throw new BFSException("Numero incorrecto de {}: " + str);
					}

					if (caracter == 93 && (stack.isEmpty() || ((Character)stack.pop()).charValue() != 91)){// 93 caracter ]
						throw new BFSException("Numero incorrecto de []: " + str);
					}
				}else{
					if (stack.isEmpty()) {
						++i;
					}

					stack.push(Character.valueOf(caracter));
				}
			}
		}

		if (flag){

			throw new BFSException("Numero incorrecto de \" :" + str);

		}else if (!stack.isEmpty()){

			throw new BFSException("Numero incorrecto de / :" + str);

		}else if (i == 0 && !str.isEmpty()){
			return 1;
		}else{
			return i;
		}
	}

	static JsonToBFS.Any getByKey(String key, String str) throws BFSException {
		str = str.trim();
		getTagCount(str);
		String temp1;
		String temp2;
		String temp3;
		char caracter;

		if (str.startsWith("{")){
			if (!str.endsWith("}")){
				throw new BFSException("No se ha podido encontrar el } final: " + str);
			}else{
				str = str.substring(1, str.length() - 1);
				JsonToBFS.Compound compound = new JsonToBFS.Compound(key);

				while (str.length() > 0){
					temp1 = splitString(str, false);

					if (temp1.length() > 0){
						temp2 = splitValue(temp1, false);
						temp3 = cutValue(temp1, false);
						compound.list.add(getByKey(temp2, temp3));

						if (str.length() < temp1.length() + 1){
							break;
						}

						caracter = str.charAt(temp1.length());

						if (caracter != 44 && caracter != 123 && caracter != 125 && caracter != 91 && caracter != 93){
							throw new BFSException("Token inesperado \'" + caracter + "\' en: " + str.substring(temp1.length()));
						}

						str = str.substring(temp1.length() + 1);
					}
				}

				return compound;
			}
		}else if (str.startsWith("[") && !str.matches("\\[[-\\d|,\\s]+\\]")){
			if (!str.endsWith("]")){
				throw new BFSException("No se pudo encontrar el ]: " + str);
			}else{
				str = str.substring(1, str.length() - 1);
				JsonToBFS.List list = new JsonToBFS.List(key);

				while (str.length() > 0){
					temp1 = splitString(str, true);

					if (temp1.length() > 0){
						temp2 = splitValue(temp1, true);
						temp3 = cutValue(temp1, true);
						list.tagList.add(getByKey(temp2, temp3));

						if (str.length() < temp1.length() + 1){
							break;
						}

						caracter = str.charAt(temp1.length());

						if (caracter != 44 && caracter != 123 && caracter != 125 && caracter != 91 && caracter != 93){
							throw new BFSException("Token inesperado \'" + caracter + "\' en: " + str.substring(temp1.length()));
						}

						str = str.substring(temp1.length() + 1);
					}
				}

				return list;
			}
		}else{
			return new JsonToBFS.Primitive(key, str);
		}
	}

	private static String splitString(String str, boolean check) throws BFSException {
		int count = countUntilChar(str, ':');

		if (count < 0 && !check){
			throw new BFSException("No fue posible encontrar el separador del string: " + str);
		}else{
			int j = countUntilChar(str, ',');

			if (j >= 0 && j < count && !check){
				throw new BFSException("Error de un nombre en: " + str);
			}else{
				if (check && (count < 0 || count > j)){
					count = -1;
				}

				Stack<Character> stack = new Stack<Character>();
				int k = count + 1;
				boolean flag1 = false;
				boolean flag2 = false;
				boolean flag3 = false;

				for (int l = 0; k < str.length(); ++k){
					char caracter = str.charAt(k);

					if (caracter == 34) {
						if (k > 0 && str.charAt(k - 1) == 92){
							if (!flag1){
								throw new BFSException("Caracter \\\" incorrecto: " + str);
							}
						}else{
							flag1 = !flag1;

							if (flag1 && !flag3){
								flag2 = true;
							}

							if (!flag1){
								l = k;
							}
						}
					}else if (!flag1){
						if (caracter != 123 && caracter != 91){
							if (caracter == 125 && (stack.isEmpty() || ((Character)stack.pop()).charValue() != 123)){
								throw new BFSException("Falta un o mas }: " + str);
						}

							if (caracter == 93 && (stack.isEmpty() || ((Character)stack.pop()).charValue() != 91)) {
								throw new BFSException("Falta un o mas ]: " + str);
							}

							if (caracter == 44 && stack.isEmpty()){
								return str.substring(0, k);
							}
						}else{
							stack.push(Character.valueOf(caracter));
						}
					}

					if (!Character.isWhitespace(caracter)) {
						if (!flag1 && flag2 && l != k){
							return str.substring(0, l + 1);
						}

						flag3 = true;
					}
				}

				return str.substring(0, k);
			}
		}
	}

	private static String splitValue(String str, boolean check) throws BFSException {
		if (check){
			str = str.trim();

			if (str.startsWith("{") || str.startsWith("[")){
				return "";
			}
		}

		int i = str.indexOf(58);//58 --> :

		if (i < 0){
			if (check){
				return "";
			}else{
				throw new BFSException("No fue posible encontrar el separador del string: " + str);
			}
		}
		else
		{
			return str.substring(0, i).trim();
		}
	}

	private static String cutValue(String str, boolean check) throws BFSException {
		if (check){
			str = str.trim();

			if (str.startsWith("{") || str.startsWith("[")){
				return str;
			}
		}

		int i = str.indexOf(58);//58 --> :

		if (i < 0){
			if (check){
				return str;
			}else{
				throw new BFSException("No fue posible encontrar el separador del string: " + str);
			}
		}else{
			return str.substring(i + 1).trim();
		}
	}

	private static int countUntilChar(String str, char caracter){
		int count = 0;

		for (boolean flag = false; count < str.length(); ++count){
			char c1 = str.charAt(count);

			if (c1 == 34){//34 caracter "
				if (count <= 0 || str.charAt(count - 1) != 92){// 92 caracter /
					flag = !flag;
				}
			}else if (!flag){
				if (c1 == caracter){
					return count;
				}

				if (c1 == 123 || c1 == 91){// 123 -> { 91 -> [
				                                              return -1;
				}
			}
		}

		return -1;
	}

	private abstract static class Any{
		
		protected String string;

		public abstract BFSBase getBFS();
	}

	private static class Compound extends JsonToBFS.Any{
		
		protected ArrayList<Any> list = new ArrayList<Any>();

		public Compound(String str){
			this.string = str;
		}

		public BFSBase getBFS(){
			BFSTagCompound bfs = new BFSTagCompound();
			Iterator<Any> iterator = this.list.iterator();

			while (iterator.hasNext()){
				JsonToBFS.Any any = (JsonToBFS.Any)iterator.next();
				bfs.setTag(any.string, any.getBFS());
			}

			return bfs;
		}
	}

	private static class List extends JsonToBFS.Any{

		protected ArrayList<Any> tagList = new ArrayList<Any>();

		public List(String str){
			this.string = str;
		}

		public BFSBase getBFS(){
			BFSTagList bfs = new BFSTagList();
			Iterator<Any> iterator = this.tagList.iterator();

			while (iterator.hasNext()){
				JsonToBFS.Any any = (JsonToBFS.Any)iterator.next();
				bfs.appendTag(any.getBFS());
			}
			return bfs;
		}
	}

	private static class Primitive extends JsonToBFS.Any {

		protected String value;

		public Primitive(String str, String val){
			this.string = str;
			this.value = val;
		}

		public BFSBase getBFS(){
			try{
				if (this.value.matches("[-+]?[0-9]*\\.?[0-9]+[d|D]")){
					
					return new BFSTagDouble(Double.parseDouble(this.value.substring(0, this.value.length() - 1)));
				
				}else if (this.value.matches("[-+]?[0-9]*\\.?[0-9]+[f|F]")){
					
					return new BFSTagFloat(Float.parseFloat(this.value.substring(0, this.value.length() - 1)));
				
				}else if (this.value.matches("[-+]?[0-9]+[b|B]")){
					
					return new BFSTagByte(Byte.parseByte(this.value.substring(0, this.value.length() - 1)));
				
				}else if (this.value.matches("[-+]?[0-9]+[l|L]")){
					
					return new BFSTagLong(Long.parseLong(this.value.substring(0, this.value.length() - 1)));
				
				}else if (this.value.matches("[-+]?[0-9]+[s|S]")){
					
					return new BFSTagShort(Short.parseShort(this.value.substring(0, this.value.length() - 1)));
				
				}else if (this.value.matches("[-+]?[0-9]+")){
					
					return new BFSTagInt(Integer.parseInt(this.value.substring(0, this.value.length())));
				
				}else if (this.value.matches("[-+]?[0-9]*\\.?[0-9]+")){
					
					return new BFSTagDouble(Double.parseDouble(this.value.substring(0, this.value.length())));
				
				}else if (!this.value.equalsIgnoreCase("true") && !this.value.equalsIgnoreCase("false")){
					if (this.value.startsWith("[") && this.value.endsWith("]")){
						if (this.value.length() > 2){
							String s = this.value.substring(1, this.value.length() - 1);
							String[] astring = s.split(",");

							try{
								if (astring.length <= 1){
									return new BFSTagIntArray(new int[] {Integer.parseInt(s.trim())});
								}else{
									int[] aint = new int[astring.length];

									for (int i = 0; i < astring.length; ++i){
									
										aint[i] = Integer.parseInt(astring[i].trim());
									}

									return new BFSTagIntArray(aint);
								}
							}catch (NumberFormatException numberformatexception){
								return new BFSTagString(this.value);
							}
						}else{
							return new BFSTagIntArray();
						}
					}else{
						if (this.value.startsWith("\"") && this.value.endsWith("\"") && this.value.length() > 2){
							this.value = this.value.substring(1, this.value.length() - 1);
						}

						this.value = this.value.replaceAll("\\\\\"", "\"");
						return new BFSTagString(this.value);
					}
				}else{
					return new BFSTagByte((byte)(Boolean.parseBoolean(this.value) ? 1 : 0));
				}
			}catch (NumberFormatException numberformatexception1){
				this.value = this.value.replaceAll("\\\\\"", "\"");
				return new BFSTagString(this.value);
			}
		}
	}
}
