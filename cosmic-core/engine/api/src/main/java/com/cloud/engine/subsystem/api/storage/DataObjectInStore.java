package com.cloud.engine.subsystem.api.storage;

import com.cloud.legacymodel.statemachine.StateObject;

public interface DataObjectInStore extends StateObject<ObjectInDataStoreStateMachine.State> {
    String getInstallPath();

    void setInstallPath(String path);

    long getObjectId();

    long getDataStoreId();

    ObjectInDataStoreStateMachine.State getObjectInStoreState();
}
