package com.railways.ecsoket.injection.component;

import com.railways.ecsoket.base.BaseActivity;
import com.railways.ecsoket.base.BaseFragment;
import com.railways.ecsoket.injection.ConfigPersistent;
import com.railways.ecsoket.injection.module.ActivityModule;
import com.railways.ecsoket.injection.module.FragmentModule;

import dagger.Component;

/**
 * A dagger component that will live during the lifecycle of an Activity or Fragment but it won't be
 * destroy during configuration changes. Check {@link BaseActivity } and {@link BaseFragment} to see
 * how this components survives configuration changes. Use the {@link ConfigPersistent} scope to
 * annotate dependencies that need to survive configuration changes (for example Presenters).
 */

@ConfigPersistent
@Component(dependencies = AppComponent.class)
public interface ConfigPersistentComponent {

    ActivityComponent activityComponent(ActivityModule activityModule);

    FragmentComponent fragmentComponent(FragmentModule fragmentModule);
}
