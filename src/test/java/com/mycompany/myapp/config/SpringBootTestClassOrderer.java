package com.mycompany.myapp.config;

import java.util.Comparator;
import org.junit.jupiter.api.ClassDescriptor;
import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.ClassOrdererContext;

public class SpringBootTestClassOrderer implements ClassOrderer {

  private static int getOrder(ClassDescriptor classDescriptor) {
    return 1;
  }

  @Override
  public void orderClasses(ClassOrdererContext context) {
    context
        .getClassDescriptors()
        .sort(Comparator.comparingInt(SpringBootTestClassOrderer::getOrder));
  }
}
