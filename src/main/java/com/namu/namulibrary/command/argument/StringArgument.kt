/*
 * Copyright (c) 2020 Noonmaru
 *
 *  Licensed under the General Public License, Version 3.0 (the "License");
 *  you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://opensource.org/licenses/gpl-3.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.namu.namulibrary.command.argument

import com.namu.namulibrary.command.KommandContext
import com.google.common.collect.ImmutableList

class StringArgument internal constructor(
    private val values: () -> Collection<String>
) : KommandArgument<String> {
    override fun parse(context: KommandContext, param: String): String? {
        val values = values()

        return param.takeIf { values.isEmpty() || param in values }
    }

    override fun listSuggestion(context: KommandContext, target: String): Collection<String> {
        return values().suggestions(target)
    }

    companion object {
        internal val emptyStringArgument = StringArgument { ImmutableList.of() }
    }
}