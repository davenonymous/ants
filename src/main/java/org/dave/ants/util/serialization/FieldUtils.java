package org.dave.ants.util.serialization;

import net.minecraft.nbt.NBTTagCompound;
import org.apache.commons.lang3.tuple.Pair;
import org.dave.ants.util.Logz;
import scala.actors.threadpool.Arrays;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class FieldUtils {
    private static Map<Class, List<Field>> lookupCache = new HashMap<>();

    public static List<Field> getAllDeclaredFields(Class clz) {
        if(!lookupCache.containsKey(clz)) {
            lookupCache.put(clz, FieldUtils.getAllDeclaredFields(new ArrayList<>(), clz));
        }

        return lookupCache.get(clz);
    }

    private static List<Field> getAllDeclaredFields(List<Field> fields, Class<?> type) {
        fields.addAll(Arrays.asList(type.getDeclaredFields()));

        if (type.getSuperclass() != null) {
            getAllDeclaredFields(fields, type.getSuperclass());
        }

        return fields;
    }

    public static List<NBTFieldSerializationData> initSerializableStoreFields(Class clz) {
        List<NBTFieldSerializationData> actionList = new ArrayList<>();

        for(Field field : FieldUtils.getAllDeclaredFields(clz)) {
            Annotation annotation = field.getDeclaredAnnotation(Store.class);
            if(annotation != null && annotation instanceof Store) {
                if(!FieldHandlers.hasNBTHandler(field.getType())) {
                    Logz.warn("No NBT serialization methods for field='%s' (type='%s') in class='%s' exists.", field.getName(), field.getType().getSimpleName(), clz.getSimpleName());
                    continue;
                }

                Store storeAnnotation = (Store)annotation;
                String key = storeAnnotation.key();
                if(key.equals("")) {
                    key = field.getName();
                }
                actionList.add(new NBTFieldSerializationData(field, key, storeAnnotation.storeWithItem(), storeAnnotation.sendInUpdatePackage()));
                field.setAccessible(true);
            }
        }

        return actionList;
    }

    public static HashMap<Field, Pair<FieldHandlers.Reader, FieldHandlers.Writer>> initSerializableSyncFields(Class clz) {
        HashMap<Field, Pair<FieldHandlers.Reader, FieldHandlers.Writer>> actionList = new HashMap<>();

        for(Field field : FieldUtils.getAllDeclaredFields(clz)) {
            Annotation annotation = field.getDeclaredAnnotation(Sync.class);
            if(annotation != null && annotation instanceof Sync) {
                if(!FieldHandlers.hasIOHandler(field.getType())) {
                    Logz.warn("No ByteBuf serialization methods for field='%s' (type='%s') in class='%s' exists.", field.getName(), field.getType().getSimpleName(), clz.getSimpleName());
                    continue;
                }

                // TODO: Implement sync annotations/actions
                Sync syncAnnotation = (Sync)annotation;
                actionList.put(field, FieldHandlers.getIOHandler(field.getType()));
            }
        }

        return actionList;
    }

    public static NBTTagCompound writeFieldsToNBT(List<NBTFieldSerializationData> NBTActions, Object source, NBTTagCompound targetCompound, Predicate<NBTFieldSerializationData> test) {
        for(NBTFieldSerializationData data : NBTActions) {
            if(!test.test(data)) {
                continue;
            }

            try {
                Object value = data.field.get(source);
                data.writer.write(data.key, value, targetCompound);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return targetCompound;
    }

    public static void readFieldsFromNBT(List<NBTFieldSerializationData> NBTActions, Object target, NBTTagCompound sourceCompound, Predicate<NBTFieldSerializationData> test) {
        for(NBTFieldSerializationData data : NBTActions) {
            if(!test.test(data)) {
                continue;
            }

            try {
                Object value = data.reader.read(data.key, sourceCompound);
                data.field.set(target, value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
