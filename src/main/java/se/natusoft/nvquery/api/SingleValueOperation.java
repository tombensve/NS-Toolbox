/*
 *
 * PROJECT
 *     Name
 *         rpnquery
 *
 * COPYRIGHTS
 *     Copyright (C) 2021 by Natusoft AB All rights reserved.
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
 *         2021-09-30: Created!
 *
 */
package se.natusoft.nvquery.api;

/**
 * This is a special operation that only gets one value.
 *
 * This is just to handle the True and False operations which are not really operations
 * in the normal sense. They are a convenience to verify a single ending true or false
 * value.
 *
 * It still uses the same API but only receives the first argument.
 */
public interface SingleValueOperation extends Operation {
}
