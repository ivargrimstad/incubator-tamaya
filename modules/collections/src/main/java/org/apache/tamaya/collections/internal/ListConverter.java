/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.tamaya.collections.internal;

import org.apache.tamaya.spi.ConversionContext;
import org.apache.tamaya.spi.PropertyConverter;

import java.util.Collections;
import java.util.List;

/**
 *  PropertyConverter for gnerating a LIST representation of values.
 */
public class ListConverter implements PropertyConverter<List> {

    @Override
    public List convert(String value, ConversionContext context) {
        String collectionType = context.getConfiguration().getOrDefault('_' + context.getKey()+".collection-type", "List");
        if(collectionType.startsWith("java.util.")){
            collectionType = collectionType.substring("java.util.".length());
        }
        List result = null;
        switch(collectionType){
            case "LinkedList":
                result = LinkedListConverter.getInstance().convert(value, context);
                break;
            case "List":
            case "ArrayList":
            default:
                result = ArrayListConverter.getInstance().convert(value, context);
                break;
        }
        if(context.getConfiguration().getOrDefault('_' + context.getKey()+".read-only",
                Boolean.class, Boolean.TRUE)){
            return Collections.unmodifiableList(result);
        }
        return result;
    }
}
