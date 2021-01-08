package com.railways.ecsoket.injection.component;

import com.railways.ecsoket.injection.PerFragment;
import com.railways.ecsoket.injection.module.FragmentModule;

import dagger.Subcomponent;

/**
 * This component inject dependencies to all Fragments across the application
 */

@PerFragment
@Subcomponent(modules = FragmentModule.class)
public interface FragmentComponent {

}
