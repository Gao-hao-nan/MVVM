package org.gradle.accessors.dm;

import org.gradle.api.NonNullApi;
import org.gradle.api.artifacts.ProjectDependency;
import org.gradle.api.internal.artifacts.dependencies.ProjectDependencyInternal;
import org.gradle.api.internal.artifacts.DefaultProjectDependencyFactory;
import org.gradle.api.internal.artifacts.dsl.dependencies.ProjectFinder;
import org.gradle.api.internal.catalog.DelegatingProjectDependency;
import org.gradle.api.internal.catalog.TypeSafeProjectDependencyFactory;
import javax.inject.Inject;

@NonNullApi
public class RootProjectAccessor extends TypeSafeProjectDependencyFactory {


    @Inject
    public RootProjectAccessor(DefaultProjectDependencyFactory factory, ProjectFinder finder) {
        super(factory, finder);
    }

    /**
     * Creates a project dependency on the project at path ":"
     */
    public WhaleReadingProjectDependency getWhaleReading() { return new WhaleReadingProjectDependency(getFactory(), create(":")); }

    /**
     * Creates a project dependency on the project at path ":BaseModule"
     */
    public BaseModuleProjectDependency getBaseModule() { return new BaseModuleProjectDependency(getFactory(), create(":BaseModule")); }

    /**
     * Creates a project dependency on the project at path ":NetworkModule"
     */
    public NetworkModuleProjectDependency getNetworkModule() { return new NetworkModuleProjectDependency(getFactory(), create(":NetworkModule")); }

    /**
     * Creates a project dependency on the project at path ":app"
     */
    public AppProjectDependency getApp() { return new AppProjectDependency(getFactory(), create(":app")); }

}
