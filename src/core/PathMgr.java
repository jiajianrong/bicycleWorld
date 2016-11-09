package core;

import java.net.URL;

public class PathMgr {
	public static void main(String[] args) {
		System.out.println(PathMgr.getClassPath());
		System.out.println(PathMgr.getProjectPath());
	}

	/**返回类路径**/
	public static String getClassPath() {
		URL url = Thread.currentThread().getContextClassLoader()
				.getResource("");
		String classpath = url.toString().substring(getByteOmitted());
		return transfer(classpath);
	}

	/**返回工程路径**/
	public static String getProjectPath() {
		String temp = getClassPath();
		for (int i = 0; i <= 2; i++) {
			int n = temp.lastIndexOf('/');
			temp = temp.substring(0, n);
		}
		return temp + "/";
	}
	
	
	private static String transfer(String classpath) {
		//System.out.println(transfer("D:/javacc%20example/myjsp/dhcc%20public123"));
		byte[] oldBytes = classpath.getBytes();
		int count = 0;
		for (int i = 0; i < oldBytes.length - 2; i++) {
			if ('%' == oldBytes[i] && '2' == oldBytes[i + 1]
					&& '0' == oldBytes[i + 2]) {
				count++;
			}
		}
		String temp = classpath;
		for (int i = 0; i < count; i++) {
			int n = temp.indexOf("%20");
			String s1 = temp.substring(0, n);
			String s2 = temp.substring(n + 3, temp.length());
			temp = s1 + " " + s2;
		}
		return temp;
	}
	
	private static int getByteOmitted(){
		String os = System.getProperty("os.name");
		if(os.toUpperCase().indexOf("WINDOWS")!=-1)
			return 6;
		if(os.toUpperCase().indexOf("LINUX")!=-1)
			return 5;
		return 5;
	}
}
