package io.katharsis.dispatcher.controller;

import io.katharsis.context.SampleJsonApplicationContext;
import io.katharsis.path.PathBuilder;
import io.katharsis.path.ResourcePath;
import io.katharsis.resource.registry.ResourceRegistry;
import io.katharsis.resource.registry.ResourceRegistryBuilder;
import io.katharsis.resource.registry.ResourceRegistryBuilderTest;
import io.katharsis.response.BaseResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ResourceGetTest {

    private PathBuilder pathBuilder;
    private String requestType;
    private ResourceRegistry resourceRegistry;

    @Before
    public void prepare() {
        pathBuilder = new PathBuilder();
        ResourceRegistryBuilder registryBuilder = new ResourceRegistryBuilder(new SampleJsonApplicationContext());
        resourceRegistry = registryBuilder.build(ResourceRegistryBuilderTest.TEST_MODELS_PACKAGE);
        requestType = "GET";
    }

    @Test
    public void onGivenRequestCollectionGetShouldDenyIt() {
        // GIVEN
        ResourcePath resourcePath = pathBuilder.buildPath("/resource/");
        ResourceGet sut = new ResourceGet(resourceRegistry);

        // WHEN
        boolean result = sut.isAcceptable(resourcePath, requestType);

        // THEN
        Assert.assertEquals(result, false);
    }

    @Test
    public void onGivenRequestResourceGetShouldAcceptIt() {
        // GIVEN
        ResourcePath resourcePath = pathBuilder.buildPath("/resource/2");
        ResourceGet sut = new ResourceGet(resourceRegistry);

        // WHEN
        boolean result = sut.isAcceptable(resourcePath, requestType);

        // THEN
        Assert.assertEquals(result, true);
    }

    @Test
    public void onGivenRequestResourceGetShouldHandleIt() {
        // GIVEN

        ResourcePath resourcePath = pathBuilder.buildPath("/tasks/1");
        ResourceGet sut = new ResourceGet(resourceRegistry);

        // WHEN
        BaseResponse<?> response = sut.handle(resourcePath);

        // THEN
        Assert.assertNotNull(response);
    }
}
