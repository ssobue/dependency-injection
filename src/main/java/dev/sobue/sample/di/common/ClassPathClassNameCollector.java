package dev.sobue.sample.di.common;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Collects class names from the compiled output directory for a package tree.
 *
 * @author Sho Sobue
 */
public final class ClassPathClassNameCollector {

  /**
   * Suffix of compiled class files.
   */
  private static final String CLASS_SUFFIX = ".class";

  /**
   * Prevent instantiation.
   */
  private ClassPathClassNameCollector() {
    throw new AssertionError("no instances");
  }

  /**
   * Collect class names from the package and its sub-packages.
   *
   * @param packageName package name
   * @return list of class names
   */
  public static List<String> collect(final String packageName) {
    String resourceName = packageName.replace('.', '/');
    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    URL root = classLoader.getResource(resourceName);
    if (root == null) {
      throw new IllegalStateException("resource not found: " + resourceName);
    }
    File rootFile = new File(root.getFile());

    File[] dirs = rootFile.listFiles(File::isDirectory);
    File[] files = rootFile.listFiles(file -> file.isFile() && file.getName().endsWith(CLASS_SUFFIX));

    List<String> classes = new ArrayList<>();

    if (dirs != null && dirs.length > 0) {
      for (File dir : dirs) {
        classes.addAll(collect(packageName + "." + dir.getName()));
      }
    }

    if (files != null && files.length > 0) {
      for (File file : files) {
        String name = file.getName();
        String className = name.substring(0, name.length() - CLASS_SUFFIX.length());
        classes.add(packageName + "." + className);
      }
    }

    return classes;
  }
}
