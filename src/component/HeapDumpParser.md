package com.heapvortex.parser;

import org.eclipse.mat.snapshot.ISnapshot;
import org.eclipse.mat.snapshot.SnapshotFactory;
import org.eclipse.mat.snapshot.model.IObject;
import org.eclipse.mat.util.VoidProgressListener;

import java.io.File;

public class HeapDumpParser {

    public static void main(String[] args) {

        // Change this path to your heap dump file
        String heapDumpPath = "D:/heapdump.hprof";

        try {

            File file = new File(heapDumpPath);

            if (!file.exists()) {
                System.out.println("Heap dump file not found!");
                return;
            }

            System.out.println("======================================");
            System.out.println("       HEAPVORTEX PARSER STARTED      ");
            System.out.println("======================================");

            // Open Heap Dump
            ISnapshot snapshot = SnapshotFactory.openSnapshot(
                    file,
                    new VoidProgressListener()
            );

            System.out.println("Heap Dump Loaded Successfully");
            System.out.println();

            // Display Heap Information
            System.out.println("Snapshot Information");
            System.out.println("-----------------------------");
            System.out.println("Number of GC Roots : " + snapshot.getGCRoots().length);
            System.out.println();

            // Display GC Root Objects
            System.out.println("GC Root Objects");
            System.out.println("-----------------------------");

            int[] gcRoots = snapshot.getGCRoots();

            for (int objectId : gcRoots) {

                IObject object = snapshot.getObject(objectId);

                System.out.println("Object ID      : " + object.getObjectId());
                System.out.println("Class Name     : " + object.getClazz().getName());
                System.out.println("Display Name   : " + object.getDisplayName());
                System.out.println("Used Heap Size : " + object.getUsedHeapSize());
                System.out.println("------------------------------------");
            }

            snapshot.dispose();

            System.out.println();
            System.out.println("Heap Parsing Completed Successfully!");

        } catch (Exception e) {

            System.out.println("Parser Error : " + e.getMessage());
            e.printStackTrace();

        }
    }
}
