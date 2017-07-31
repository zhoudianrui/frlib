package io.vicp.frlib.router;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>命令执行器<p>
 *
 * @author zhoudr
 * @version $Id: InvokerHolder, v 0.1 2017/7/29 15:37 zhoudr Exp $$
 */

public class InvokerHolder {

    private static Map<Integer, Map<Integer, Invoker>> moduleInvokerMap = new HashMap<>();

    public static Invoker get(int module, int command) {
        Map<Integer, Invoker> commandInvokerMap = moduleInvokerMap.get(module);
        if (commandInvokerMap != null) {
            return commandInvokerMap.get(command);
        }
        return null;
    }

    public static void add(int module, int command, Invoker invoker) {
        Map<Integer, Invoker> commandInvokerMap = moduleInvokerMap.get(module);
        if (commandInvokerMap == null) {
            commandInvokerMap = new HashMap<>();
            moduleInvokerMap.put(module, commandInvokerMap);
        }
        commandInvokerMap.put(command, invoker);
    }
}
