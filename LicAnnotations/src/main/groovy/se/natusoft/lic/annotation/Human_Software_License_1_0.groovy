package se.natusoft.lic.annotation

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

/**
 * This is an annotation for the Human Software License (HSL)!
 * HSL extends the ASL (Apache Software License)
 */
@Retention( RetentionPolicy.RUNTIME)
@interface Human_Software_License_1_0 {

    String licenseText() default "" +
            "        Licensed under the Human Software License, Version 1.0 (the \"License\");\n" +
            "        you may not use this file except in compliance with the License.\n" +
            "        You may obtain a copy of the License at\n" +
            "\n" +
            "          https://tombensvebloggish.craft.me/hsl\n" +
            "\n" +
            "        Unless required by applicable law or agreed to in writing, software\n" +
            "        distributed under the License is distributed on an \"AS IS\" BASIS,\n" +
            "        WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n" +
            "        See the License for the specific language governing permissions and\n" +
            "        limitations under the License.\n";

    String URL() default "https://tombensvebloggish.craft.me/hsl"

    Apache_Software_License_2_0 Extends()
}
