import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Jeff_xu on 2017/9/1.
 *
 * @author Jeff_xu
 * @date 2017/09/01
 */
public class Mockito {

    private static Map<Invocation, Object> results = new HashMap<Invocation, Object>();
    private static Invocation lastInvocation;

    public static <T> T mock(Class<T> clazz) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(new MockInterceptor());
        return (T)enhancer.create();
    }

    private static class MockInterceptor implements MethodInterceptor {
        public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
            Invocation invocation = new Invocation(proxy, method, args, proxy);
            lastInvocation = invocation;
            if (results.containsKey(invocation)) {
                return results.get(invocation);
            }
            return null;
        }
    }

    public static <T> When<T> when(T o) {
        return new When<T>();
    }

    public static class When<T> {
        public void thenReturn(T retObj) {
            results.put(lastInvocation, retObj);
        }
    }

    @Test
    public void test() {
        Calculate calculate = mock(Calculate.class);
        when(calculate.add(1, 1)).thenReturn(1);
        Assert.assertEquals(1, calculate.add(1, 1));
    }
}
