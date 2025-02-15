/*
 * Copyright 2023 Squircle CE contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.blacksquircle.ui.core.data.converter

import com.blacksquircle.ui.core.data.storage.database.entity.server.ServerEntity
import com.blacksquircle.ui.filesystem.base.model.AuthMethod
import com.blacksquircle.ui.filesystem.base.model.ServerModel

object ServerConverter {

    fun toModel(serverEntity: ServerEntity): ServerModel {
        return ServerModel(
            uuid = serverEntity.uuid,
            scheme = serverEntity.scheme,
            name = serverEntity.name,
            address = serverEntity.address,
            port = serverEntity.port,
            authMethod = AuthMethod.find(serverEntity.authMethod),
            username = serverEntity.username,
            password = serverEntity.password,
            privateKey = serverEntity.privateKey,
            passphrase = serverEntity.passphrase,
        )
    }

    fun toEntity(serverModel: ServerModel): ServerEntity {
        return ServerEntity(
            uuid = serverModel.uuid,
            scheme = serverModel.scheme,
            name = serverModel.name,
            address = serverModel.address,
            port = serverModel.port,
            authMethod = serverModel.authMethod.value,
            username = serverModel.username,
            password = serverModel.password,
            privateKey = serverModel.privateKey,
            passphrase = serverModel.passphrase,
        )
    }
}