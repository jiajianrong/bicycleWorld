package core;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class PropMgr {
		public static void main(String[] a) throws UnsupportedEncodingException{
//			PropMgr pm = new PropMgr("F:/new.txt");
//					//"Y:/java/workspace/smallPro/Root/WEB-INF/classes/jdbc.properties");
//			System.out.println(pm.getValue("peo"));
//			byte[] bs = pm.getValue("peo").getBytes("iso-8859-1");
//			String s = new String(bs, "utf-8");
//			System.out.println(s);
			
			
		}
        private Properties prop;
        
        public static String[] getDeps(){
            PropMgr pm = new PropMgr(PathMgr.getClassPath() + "department_names.txt");
            String value;
            try {
                value = new String(pm.getValue("value").getBytes("iso-8859-1"), "gbk");
                return value.split(",");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return new String[]{ "0", "后勤车间", "商服分公司", "保洁车间", "客运车间", "售票车间", "房修车间", "运转车间", "行装车间", "上水车间" };
            }
        }

        public PropMgr(String pathstr) {
                prop = new Properties();
                try {
                	 	FileInputStream fileInputStream = new FileInputStream(pathstr);
                        try {
                                prop.load(fileInputStream);
                                fileInputStream.close();
                        } catch (IOException e) {
                                e.printStackTrace();
                        }
                } catch (FileNotFoundException e) {
                        e.printStackTrace();
                }
        }

        public String getValue(String key) {
                String keystr = null;
                if (prop.containsKey(key)) {
                        keystr = prop.getProperty(key);
                }
                return keystr;
        }
}