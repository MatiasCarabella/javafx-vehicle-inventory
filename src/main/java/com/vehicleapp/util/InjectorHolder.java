package com.vehicleapp.util;

import com.google.inject.Injector;

import lombok.RequiredArgsConstructor;

/** Simple holder for the Guice Injector so JavaFX Application can access it. */
@RequiredArgsConstructor
public final class InjectorHolder {
  private static volatile Injector INJECTOR;

  public static void setInjector(Injector injector) {
    INJECTOR = injector;
  }

  public static Injector getInjector() {
    if (INJECTOR == null) {
      throw new IllegalStateException("Injector has not been initialized");
    }
    return INJECTOR;
  }
}
