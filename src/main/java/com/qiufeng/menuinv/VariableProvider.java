package com.qiufeng.menuinv;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VariableProvider {
    public static Map<String, Method> map = new HashMap<>();
    static {
        registerThis("time", "getTime");
        registerThis("money", "getMoney");
    }

    public static boolean register(String var, Method method) {
        map.put(var, method);
        return true;
    }
    private static boolean registerThis(String var, String method_name) {
        Method method = null;
        for(var find : VariableProvider.class.getMethods()) {
            if(find.getName().equals(method_name)) {
                method = find;
                break;
            }
        }
        if(method == null) {
            MainPlugin.self.getLogger().warning("Method bind for " + var + " not found: " + method_name);
            return false;
        }
        return register(var, method);
    }
    public static String call(String var, String[] args) throws InvocationTargetException, IllegalAccessException {
        return map.get(var).invoke(null, (Object[]) args).toString();
    }

    public static String replace(String input) throws InvocationTargetException, IllegalAccessException {
        Pattern pattern = Pattern.compile("\\$\\{.*}");
        Matcher matcher = pattern.matcher(input);
        String res_str = input;
        while(matcher.find()) {
            String found = matcher.group();
            Pattern argp = Pattern.compile("[a-zA-Z0-9${}]+");
            Matcher argMatcher = argp.matcher(found);
            String var = "...";
            List<String> args = new ArrayList<>();
            while(argMatcher.find()) {
                String res = replaceVar(argMatcher.group());
                if(var.equals("...")) {
                    var = res;
                }else {
                    args.add(res);
                }
            }
            String val = call(var, (String[]) args.toArray());
        }
        return res_str;
    }

    private static String replaceVar(String input) {
        return input;
    }

    public static String getTime(String[] args) {
        return new Date().toString();
    }

    public static String getMoney(String[] args) {
        if(args.length < 1) {
            return "None";
        }
        Player player = Bukkit.getPlayer(args[0]);
        if(player == null) {
            return "None";
        } else {
            return String.valueOf(MainPlugin.econ.getBalance(player));
        }
    }

}
