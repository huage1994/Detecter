package formyountest;

import org.omg.PortableInterceptor.INACTIVE;
import org.omg.PortableInterceptor.Interceptor;

import java.util.*;

/**
 * Created by huage on 2017/4/11.
 */
public class TestForNew {
    public static void main(String[] args) {
        Map<Integer, List<Integer>> hashmap = new HashMap<Integer, List<Integer>>();
        for (int i=7;i<65535;i++){
            List<Integer> list = hashmap.get(i);
            if (list !=null)
            list.add(3);
            else {
                list = new ArrayList<Integer>();
                list.add(4);
                hashmap.put(i,list);
            }
        }
        for (int i=0;i<63335;i++){
            List<Integer> list = hashmap.get(i);
            if (list !=null)
                list.add(3);
            else {
                list = new ArrayList<Integer>();
                list.add(4);
                hashmap.put(i,list);
            }
        }
        Iterator iterator = hashmap.entrySet().iterator();
        StringBuffer stb = new StringBuffer();
        while (iterator.hasNext())
        {
            Map.Entry entry = (Map.Entry) iterator.next();
            Integer key = (Integer) entry.getKey();
            List<Integer> val = ( List<Integer>) entry.getValue();
            stb.append("--"+key+":"+val);
        }
        System.out.println(stb);

        System.out.println(hashmap.size());
        System.out.println(hashmap.get(560));

    }
}
