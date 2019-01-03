package org.iota.ict.ixi;

import org.iota.ict.Ict;
import org.iota.ict.Main;

import java.io.File;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

public class IxiModuleLoader {

    private static final File DEFAULT_MODULE_FOLDER = new File("modules/");

    static {
        if(!DEFAULT_MODULE_FOLDER.exists())
            DEFAULT_MODULE_FOLDER.mkdirs();
    }

    public List<IxiModule> load(Ict ict) throws ModuleLoadingException {

        try {

            List<IxiModule> ret = new ArrayList<>();

            for(File jar: DEFAULT_MODULE_FOLDER.listFiles()) {

                URLClassLoader child = new URLClassLoader (new URL[] {jar.toURI().toURL()}, Main.class.getClassLoader());
                Class classToLoad = Class.forName("org.iota.ict.ixi.Start", true, child);

                Constructor<?> c = classToLoad.getConstructor(IctProxy.class);
                IxiModule module = (IxiModule) c.newInstance(new IctProxy(ict));

                ret.add(module);

            }

//            for(File file: DEFAULT_MODULE_FOLDER.listFiles()) {
//
//                String name = file.getName().replace(".class", "");
//
//                File base = new File("modules/");
//                URL[] urls = new URL[]{ base.toURI().toURL() };
//
//                ClassLoader l = new URLClassLoader(urls);
//                Class<?> loadedClass = l.loadClass("org.iota.ict.ixi." + name);
//
//                Constructor<?> ctor = loadedClass.getConstructor(IctProxy.class);
//                IxiModule module = (IxiModule) ctor.newInstance(new IctProxy(ict));
//
//                ret.add(module);
//
//            }

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