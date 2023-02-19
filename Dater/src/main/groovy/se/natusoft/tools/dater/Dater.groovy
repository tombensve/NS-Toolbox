package se.natusoft.tools.dater

import groovy.transform.CompileStatic
import groovy.transform.ToString

import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.temporal.Temporal

/**
 * Parses date strings and provides java.time.LocalDateTime instances.
 *
 * Note this have primitive support for American date format.
 */
@CompileStatic
class Dater {
    //
    // Constants
    //

    private static String VALID_NUMBERS = "0123456789"

    //
    // Private Members
    //

    /** The original date string */
    private String dateString

    /**
     * The temporal object representing the parsed information. Should be one of LocalTime,
     * LocalDate or LocalDateTime.
     */
    private Temporal date

    /**
     * @return a LocalDate or a LocalDateTime
     */
    Temporal getTemporalObject() {
        return this.date
    }

    //
    // Public API
    //

    /**
     * Users call this to get a Temporal implementation representing the data in the passed string.
     *
     * This is a bit of cosmetics. I think this way of creating looks clearer.
     *
     * @param dateString
     *
     * @return A Dater instance.
     */
    static Dater from( String dateString ) {
        return new Dater( dateString ).parse()
    }

    //
    // Internals
    //

    /**
     * Internal model holding parsed values and some flags.
     */
    @ToString
    private class Components {

        int year = 0, month = 0, day = 0, hour = 0, minute = 0, second = 0, millisecond = 0
        //int timeZoneOffsetHH = 0, timeZoneOffsetMM = 0
        String timeZone
        boolean haveTime = false
        //boolean timeZoneIsUTC = false
        boolean american = false // No good support for this format yet, maybe never.
    }

    /**
     * Constructor.
     *
     * @param dateString The string to parse.
     */
    private Dater( String dateString ) {

        this.dateString = dateString
    }

    /**
     * @return The original passed string.
     */
    String getDateString() {
        this.dateString
    }

    /**
     * Parses the date of the specified date string.
     */
    private Dater parse() {

        Components comps = new Components()

        int pos = parseDate( comps )

        if ( pos < this.dateString.length() ) {

            pos = parseTime( pos, comps )
        }

        if ( comps.haveTime ) {

            ZoneId zoneId = ZoneId.of( comps.timeZone )

            this.date = ZonedDateTime.of( comps.year, comps.month, comps.day, comps.hour, comps.minute,
                    comps.second, comps.millisecond, zoneId )
        }
        else {

            this.date = LocalDate.of( comps.year, comps.month, comps.day )
        }

        this
    }

    //
    // Date
    //

    /**
     * Parses the date part.
     *
     * @param comps The result values from parsing.
     *
     * @return Current position in string.
     */
    private int parseDate( Components comps ) {

        int pos = parseYear( 0, comps )

        if ( !this.dateString[ pos ] == '-' ) {
            if ( this.dateString[ pos ] == '/' ) {
                comps.american = true
            }
            else {
                throw new IllegalArgumentException( "Expected a '-' but got ${this.dateString[ pos ]}" )
            }
        }

        if ( comps.american ) {
            comps.month = comps.year
            comps.year = 0
            pos = parseDay( pos + 1, comps )
            pos = parseYear( pos + 1, comps )
        }
        else {
            pos = parseMonth( pos + 1, comps )
            pos = parseDay( pos + 1, comps )
        }

        // Check for date and time divider

        if (pos < this.dateString.length(  )) {
            String dateTimeDivider = this.dateString[ pos ]
            if ( dateTimeDivider != " " && dateTimeDivider != "T" ) {
                throw new IllegalArgumentException( "Bad date&time divider (${this.dateString[ pos, pos + 1 ]})!" )
            }
        }
        //System.err.println( "Date & time divider: '${dateTimeDivider}'" )

        ++pos
    }

    /**
     * Parses year.
     *
     * @param pos Current position in string.
     * @param comps The data to update with parse result.
     *
     * @return Current position in parsed string.
     */
    private int parseYear( int pos, Components comps ) {


        boolean startsWith4Digits = this.dateString[ 0..3 ].isNumber()
        boolean startsWith2Digits = this.dateString[ 0..1 ].isNumber()

        if ( startsWith2Digits ) {

            if ( startsWith4Digits ) {
                comps.year = Integer.parseInt( this.dateString[ 0..3 ] )
                pos = 4
            }
            else {
                comps.year = Integer.parseInt( this.dateString[ 0..1 ] )
                pos = 2
            }

        }

        pos
    }

    /**
     * Parses month.
     *
     * @param pos Current position in string.
     * @param comps The data to update with parse result.
     *
     * @return Current position in parsing.
     */
    int parseMonth( int pos, Components comps ) {

        int end = pos + 1
        comps.month = Integer.parseInt( this.dateString[ pos..end ] )

        end + 1
    }

    /**
     * Parses day.
     *
     * @param pos Current position in string.
     * @param comps The data to update with parse result.
     *
     * @return Current position in string.
     */
    int parseDay( int pos, Components comps ) {

        int end = pos + 1
        comps.day = Integer.parseInt( this.dateString[ pos..end ] )

        end + 1
    }

    //
    // Time
    //

    /**
     * Parses time information.
     *
     * @param pos Current position in String.
     * @param comps The data to update with parse result.
     *
     * @return Current position in parsing.
     */
    private int parseTime( int pos, Components comps ) {

        comps.haveTime = true

        pos = parseHour( pos, comps )

        pos = parseMinute( pos + 1, comps )

        pos = parseSeconds( pos + 1, comps )

        if ( pos < this.dateString.length() ) {

            pos = parseMilliseconds( pos + 1, comps )

            if ( pos < this.dateString.length() ) {
                parseTimeZone( pos, comps )
            }
        }

        ++pos
    }

    /**
     * Parses Hour.
     *
     * @param pos Current position in parsing.
     * @param comps The data to update with parse result.
     *
     * @return Current position in parsing.
     */
    private int parseHour( int pos, Components comps ) {

        int end = pos + 1
        comps.hour = Integer.parseInt( this.dateString[ pos..end ] )

        end + 1

    }

    /**
     * Parses Minute.
     *
     * @param pos Current position in parsing.
     * @param comps The data to update with parse result.
     *
     * @return Current position in parsing.
     */
    private int parseMinute( int pos, Components comps ) {

        int end = pos + 1
        comps.minute = Integer.parseInt( this.dateString[ pos..end ] )

        end + 1
    }

    /**
     * Parses seconds.
     *
     * @param pos Current position in parsing.
     * @param comps The data to update with parsed result.
     *
     * @return Current position in parsing.
     */
    private int parseSeconds( int pos, Components comps ) {

        int end = pos + 1
        comps.second = Integer.parseInt( this.dateString[ pos..end ] )

        end + 1
    }

    /**
     * "sss"
     *
     * Number of complete milliseconds since the start of the second as three decimal digits.
     * The milliseconds field may be omitted.
     *
     * @param pos Current position in parsing.
     * @param comps The data to update with parsed result.
     *
     * @return Current position in parsing.
     */
    private int parseMilliseconds( int pos, Components comps ) {

        if ( this.dateString[ pos ] == "." ) {
            ++pos
        }

        int end = pos + 2
        // 3 digits!!
        comps.millisecond = Integer.parseInt( this.dateString[ pos..end ] )

        end + 1
    }

    //
    // Time Zone
    //

    /**
     * "Z"
     *
     * Time zone offset is specified as "Z" (for UTC), or either "+" or "-" followed by a time
     * expression hh:mm
     *
     * @param pos Current position in parsing.
     * @param comps The data to update with parsed result.
     */
    private void parseTimeZone( int pos, Components comps ) {

        // Here I decided to let java.time manage this part. It seemed compatible in docs.
        int end = this.dateString.length() - 1
        comps.timeZone = this.dateString[ pos..end ]

        // I Keep this for now!
        //        // Timezone can be prefixed with '.'.
        //        if ( this.dateString[ pos ] == "." ) {
        //            ++pos
        //        }
        //
        //        if ( this.dateString[ pos ] == "Z" ) {
        //            comps.timeZoneIsUTC = true
        //        }
        //        else if ( this.dateString[ pos ] == "+" ) {
        //            ++pos
        //            int end = pos + 1
        //            comps.timeZoneOffsetHH = Integer.parseInt( this.dateString[ pos..end ] )
        //            pos = pos + 3 // Intentionally skipping the check of ":" between hh:mm.
        //            end = pos + 1
        //            comps.timeZoneOffsetMM = Integer.parseInt( this.dateString[ pos..end ] )
        //        }
        //        else if ( this.dateString[ pos ] == "-" ) {
        //            ++pos
        //            int end = pos + 1
        //            comps.timeZoneOffsetHH = Integer.parseInt( this.dateString[ pos..end ] ) * -1
        //            pos = pos + 3 // Intentionally skipping the check of ":" between hh:mm.
        //            end = pos + 1
        //            comps.timeZoneOffsetMM = Integer.parseInt( this.dateString[ pos..end ] ) * -1
        //        }

    }

    /**
     * Workaround for something I have no control over.
     *
     * @param javaTimeToStringOutput The java.time output
     *
     * @return Cleaned output.
     */
    static String javaTimeToStringFix(String javaTimeToStringOutput) {
        javaTimeToStringOutput.replace( "000000", "" )
    }

}

