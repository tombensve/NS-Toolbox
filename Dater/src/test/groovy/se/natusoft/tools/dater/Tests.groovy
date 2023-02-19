package se.natusoft.tools.dater

import groovy.transform.CompileStatic
import org.junit.jupiter.api.Test

import java.time.temporal.Temporal

@CompileStatic
class Tests {


    @Test
    void validateParsingFull1() {

        Dater dater = Dater.from( "2023-02-18T16:47:05.123+04:30" )

        Temporal date = dater.getTemporalObject(  )
        System.out.println("" + date)

        assert Dater.javaTimeToStringFix(date.toString(  )) == "2023-02-18T16:47:05.123+04:30"

    }

    @Test
    void validateParsingFull2() {

        Dater dater = Dater.from( "2021-04-28T08:35:24.321Z" )

        Temporal date = dater.getTemporalObject(  )

        assert Dater.javaTimeToStringFix(date.toString(  )) == "2021-04-28T08:35:24.321Z"

        System.out.println("" + date)
    }

    @Test
    void validateParsingNoTime() {

        Dater dater = Dater.from( "2023-02-18" )

        Temporal date = dater.getTemporalObject(  )

        assert date.toString(  ) == "2023-02-18"

        System.out.println("" + date)
    }
}
