/*
 *
 * PROJECT
 *     Name
 *         Modelish
 *
 *     Description
 *         Provides a RPN Query against name value set of data (Properties, Map).
 *
 * COPYRIGHTS
 *     Copyright (C) 2022 by Natusoft AB All rights reserved.
 *
 * LICENSE
 *     Apache 2.0 (Open Source)
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 *
 * AUTHORS
 *     tommy ()
 *         Changes:
 *         2022-02-12: Created!
 *
 */
package se.natusoft.tools.modelish

import groovy.transform.CompileStatic

/**
 * Identical to Cloneable but using _create() method instead, more fitting for a factory. Basically cosmetics.
 *
 * @param <T>
 */
@CompileStatic
interface Factory<T> extends Model<T> {

    /**
     * @return A clone of current model. New model will not be locked, and neither will sub models!!
     *         You have to lock clone and all its sub models manually if/when you want them locked.
     */
    T _create()
}
