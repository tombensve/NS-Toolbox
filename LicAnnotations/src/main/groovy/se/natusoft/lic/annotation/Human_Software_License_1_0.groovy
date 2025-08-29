package se.natusoft.lic.annotation

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

/**
 * This is an annotation for the Human Software License (HSL)!
 * HSL extends the ASL (Apache Software License)
 */
@Retention( RetentionPolicy.RUNTIME)
@interface Human_Software_License_1_0 {

    String licenseText() default """
    Licensed under the Human Software License, Version 1.0 (the "License")
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
        https://tombensvebloggish.craft.me/hsl
            
            Unless required by applicable law or agreed to in writing, software
            distributed under the License is distributed on an "AS IS" BASIS,
            WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
            See the License for the specific language governing permissions and
            limitations under the License.
            
        The HSL license extends the Apache Software Licence 2.0.
    """

    String URL() default "https://tombensvebloggish.craft.me/hsl"

    
}
