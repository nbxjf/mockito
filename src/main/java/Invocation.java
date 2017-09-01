import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Created by Jeff_xu on 2017/9/1.
 *
 * @author Jeff_xu
 * @date 2017/09/01
 */
public class Invocation {
    private final Object mock;
    private final Method method;
    private final Object[] arguments;
    private final MethodProxy proxy;

    public Invocation(Object mock, Method method, Object[] args, MethodProxy proxy) {
        this.mock = mock;
        this.method = method;
        this.arguments = copyArgs(args);
        this.proxy = proxy;
    }

    private Object[] copyArgs(Object[] args) {
        Object[] newArgs = new Object[args.length];
        System.arraycopy(args, 0, newArgs, 0, args.length);
        return newArgs;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !obj.getClass().equals(this.getClass())) { return false; }
        Invocation other = (Invocation)obj;
        return this.method.equals(other.method) && this.proxy.equals((other).proxy)
            && Arrays.deepEquals(arguments, other.arguments);
    }

    @Override
    public int hashCode() {
        return 1;
    }
}
