package org.dave.ants.util;

import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import org.dave.ants.api.chambers.AntChamber;
import org.dave.ants.api.chambers.IAntChamber;
import org.dave.ants.api.chambers.IAntChamberVoxelHandler;
import org.dave.ants.api.chambers.AntChamberVoxelHandler;
import org.dave.ants.api.hill.HillProperty;
import org.dave.ants.api.hill.IHillProperty;


import java.util.*;

public class AnnotatedInstanceUtil {
    public static ASMDataTable asmDataTable;

    private AnnotatedInstanceUtil() {
    }

    public static List<IAntChamber> getAntChamberIntegrations() {
        return getInstances(AntChamber.class, IAntChamber.class);
    }

    public static Map<IAntChamberVoxelHandler, Map<String, Object>> getVoxelHandlers() {
        return getInstancesWithAnnotations(AntChamberVoxelHandler.class, IAntChamberVoxelHandler.class);
    }

    public static List<Class<? extends IAntChamber>> getAntChamberClasses() {
        return getClasses(AntChamber.class, IAntChamber.class);
    }

    public static Map<Class<? extends IHillProperty>, Map<String, Object>> getHillPropertyClasses() {
        return getClassesWithAnnotations(HillProperty.class, IHillProperty.class);
    }

    private static <T> Map<Class<? extends T>, Map<String, Object>> getClassesWithAnnotations(Class annotationClass, Class<T> instanceClass) {
        String annotationClassName = annotationClass.getCanonicalName();
        Set<ASMDataTable.ASMData> asmDatas = asmDataTable.getAll(annotationClassName);
        Map<Class<? extends T>, Map<String, Object>> classes = new HashMap<>();
        for (ASMDataTable.ASMData asmData : asmDatas) {
            try {
                Map<String, Object> annotationInfo = asmData.getAnnotationInfo();
                if(annotationInfo.containsKey("mod")) {
                    String requiredMod = (String)annotationInfo.get("mod");
                    if(requiredMod.length() > 0 && !Loader.isModLoaded(requiredMod)) {
                        continue;
                    }
                }

                Class<?> asmClass = Class.forName(asmData.getClassName());
                Class<? extends T> asmInstanceClass = asmClass.asSubclass(instanceClass);
                classes.put(asmInstanceClass, annotationInfo);
            } catch (ClassNotFoundException e) {
                Logz.error("Failed to load: {}", asmData.getClassName(), e);
            } catch (ExceptionInInitializerError e) {
                Logz.error("Failed to load: {}", asmData.getClassName(), e);
            }
        }

        return classes;

    }

    private static <T> List<Class<? extends T>> getClasses(Class annotationClass, Class<T> instanceClass) {
        String annotationClassName = annotationClass.getCanonicalName();
        Set<ASMDataTable.ASMData> asmDatas = asmDataTable.getAll(annotationClassName);
        List<Class<? extends T>> classes = new ArrayList<>();
        for (ASMDataTable.ASMData asmData : asmDatas) {
            try {
                Map<String, Object> annotationInfo = asmData.getAnnotationInfo();
                if(annotationInfo.containsKey("mod")) {
                    String requiredMod = (String)annotationInfo.get("mod");
                    if(requiredMod.length() > 0 && !Loader.isModLoaded(requiredMod)) {
                        continue;
                    }
                }

                Class<?> asmClass = Class.forName(asmData.getClassName());
                Class<? extends T> asmInstanceClass = asmClass.asSubclass(instanceClass);
                classes.add(asmInstanceClass);
            } catch (ClassNotFoundException e) {
                Logz.error("Failed to load: {}", asmData.getClassName(), e);
            } catch (ExceptionInInitializerError e) {
                Logz.error("Failed to load: {}", asmData.getClassName(), e);
            }
        }

        return classes;
    }

    private static <T> Map<T, Map<String, Object>> getInstancesWithAnnotations(Class annotationClass, Class<T> instanceClass) {
        String annotationClassName = annotationClass.getCanonicalName();
        Set<ASMDataTable.ASMData> asmDatas = asmDataTable.getAll(annotationClassName);
        Map<T, Map<String,Object>> instances = new HashMap<>();
        for (ASMDataTable.ASMData asmData : asmDatas) {
            try {
                Map<String, Object> annotationInfo = asmData.getAnnotationInfo();
                if(annotationInfo.containsKey("mod")) {
                    String requiredMod = (String)annotationInfo.get("mod");
                    if(requiredMod.length() > 0 && !Loader.isModLoaded(requiredMod)) {
                        continue;
                    }
                }

                Class<?> asmClass = Class.forName(asmData.getClassName());
                Class<? extends T> asmInstanceClass = asmClass.asSubclass(instanceClass);
                T instance = asmInstanceClass.newInstance();

                instances.put(instance, annotationInfo);
            } catch (ClassNotFoundException e) {
                Logz.error("Failed to load: {}", asmData.getClassName(), e);
            } catch (IllegalAccessException e) {
                Logz.error("Failed to load: {}", asmData.getClassName(), e);
            } catch (InstantiationException e) {
                Logz.error("Failed to load: {}", asmData.getClassName(), e);
            } catch (ExceptionInInitializerError e) {
                Logz.error("Failed to load: {}", asmData.getClassName(), e);
            }
        }
        return instances;
    }

    private static <T> List<T> getInstances(Class annotationClass, Class<T> instanceClass) {
        String annotationClassName = annotationClass.getCanonicalName();
        Set<ASMDataTable.ASMData> asmDatas = asmDataTable.getAll(annotationClassName);
        List<T> instances = new ArrayList<T>();
        for (ASMDataTable.ASMData asmData : asmDatas) {
            try {
                Map<String, Object> annotationInfo = asmData.getAnnotationInfo();
                if(annotationInfo.containsKey("mod")) {
                    String requiredMod = (String)annotationInfo.get("mod");
                    if(requiredMod.length() > 0 && !Loader.isModLoaded(requiredMod)) {
                        continue;
                    }
                }

                Class<?> asmClass = Class.forName(asmData.getClassName());
                Class<? extends T> asmInstanceClass = asmClass.asSubclass(instanceClass);
                T instance = asmInstanceClass.newInstance();
                instances.add(instance);
            } catch (ClassNotFoundException e) {
                Logz.error("Failed to load: {}", asmData.getClassName(), e);
            } catch (IllegalAccessException e) {
                Logz.error("Failed to load: {}", asmData.getClassName(), e);
            } catch (InstantiationException e) {
                Logz.error("Failed to load: {}", asmData.getClassName(), e);
            } catch (ExceptionInInitializerError e) {
                Logz.error("Failed to load: {}", asmData.getClassName(), e);
            }
        }
        return instances;
    }
}
