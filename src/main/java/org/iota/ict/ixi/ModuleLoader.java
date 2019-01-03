package org.iota.ict.ixi;

import org.iota.ict.Ict;

import java.io.File;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

public class ModuleLoader {

    private static final File DEFAULT_MODULE_FOLDER = new File("modules/org/iota/ict/ixi/");

    static {
        if(!DEFAULT_MODULE_FOLDER.exists())
            DEFAULT_MODULE_FOLDER.mkdirs();
    }

    public static List<IxiModule> load(Ict ict) throws ModuleLoadingException {

        List<IxiModule> ret = new ArrayList<>();

        try {

            for(File file: DEFAULT_MODULE_FOLDER.listFiles()) {

                String name = file.getName().replace(".class", "");

                File base = new File("modules/");
                URL[] urls = new URL[]{ base.toURI().toURL() };

                ClassLoader l = new URLClassLoader(urls);
                Class<?> loadedClass = l.loadClass("org.iota.ict.ixi." + name);

                Constructor<?> ctor = loadedClass.getConstructor(IctProxy.class);
                IxiModule module = (IxiModule) ctor.newInstance(new IctProxy(ict));

                ret.add(module);

            }

            return ret;

        } catch (Throwable t) {
            throw new ModuleLoadingException(t.getMessage());
        }

    }

    public static class ModuleLoadingException extends Exception {
        public ModuleLoadingException(String msg) {
            super(msg);
        }
    }

}

