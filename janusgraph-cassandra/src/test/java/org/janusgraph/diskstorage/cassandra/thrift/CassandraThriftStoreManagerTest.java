// Copyright 2017 JanusGraph Authors
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package org.janusgraph.diskstorage.cassandra.thrift;

import org.janusgraph.JanusGraphCassandraThriftContainer;
import org.janusgraph.diskstorage.configuration.ModifiableConfiguration;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Testcontainers
public class CassandraThriftStoreManagerTest {
    @Container
    public static final JanusGraphCassandraThriftContainer thriftContainer = new JanusGraphCassandraThriftContainer();

    @Test
    public void configOptionFrameSizeMbShouldErrorOnLowValue() {
        assertThrows(IllegalArgumentException.class, () -> {
            ModifiableConfiguration config = thriftContainer.getThriftConfiguration("janusgraph");
            config.set(CassandraThriftStoreManager.THRIFT_FRAME_SIZE_MB, 0);
            config.get(CassandraThriftStoreManager.THRIFT_FRAME_SIZE_MB);
        });
    }

    @Test
    public void configOptionFrameSizeMbShouldBeHappy() {
        ModifiableConfiguration config = thriftContainer.getThriftConfiguration("janusgraph");
        config.set(CassandraThriftStoreManager.THRIFT_FRAME_SIZE_MB, 1);
        Integer result = config.get(CassandraThriftStoreManager.THRIFT_FRAME_SIZE_MB);
        assertEquals(1, result.intValue());
    }

    @Test
    public void configOptionFrameSizeMbShouldErrorOnHighValue() {
        assertThrows(IllegalArgumentException.class, () -> {
            ModifiableConfiguration config = thriftContainer.getThriftConfiguration("janusgraph");
            config.set(CassandraThriftStoreManager.THRIFT_FRAME_SIZE_MB, 2048);
            config.get(CassandraThriftStoreManager.THRIFT_FRAME_SIZE_MB);
        });
    }

}
