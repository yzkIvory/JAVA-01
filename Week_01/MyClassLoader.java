
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

public class MyClassLoader extends ClassLoader {
    private String fullPath;
    private String classLoaderName;
 
    public MyClassLoader(String fullPath, String classLoaderName) {
        this.fullPath = fullPath;
        this.classLoaderName = classLoaderName;
    }
 
    @Override
    public Class findClass(String name){
        byte[] b = loadClassData(fullPath);
        int blen = b.length;
        byte[] bNew = new byte[blen];
        if(fullPath.endsWith(".xlass")){
        	for(int i=0;i<blen;i++){
        		bNew[i] = (byte) (255-b[i]);
        	}
        	return defineClass(name, bNew, 0, b.length);
        }else{
        	return defineClass(name, b, 0, b.length);
        }
    }
 
    private byte[] loadClassData(String fullPath){
        InputStream in = null;
        ByteArrayOutputStream out = null;
 
        try {
            in = new FileInputStream(new File(fullPath));
            out = new ByteArrayOutputStream();
            int i = 0;
            while ((i = in.read()) != -1){
                out.write(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                out.close();
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return out.toByteArray();
 
    }

    public static void main(String[] args) {
    	MyClassLoader loader = new MyClassLoader("C:/Users/yzkiv/Desktop/Hello/Hello.xlass","loadHello");
        Class<?> aClass = loader.findClass("Hello");
        try {
            Object obj = aClass.newInstance();
            Method method = aClass.getMethod("hello");
            method.invoke(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}
