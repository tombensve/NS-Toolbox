package se.natusoft.tools.serviceloader.annotations

/*
    Properties to annotate a service provider with. The `FilteringServiceLoader` will
    use these to possibly exclude services.

    Example:

        @Props(
            @Prop(name="detectAliens", value="true")
            ...
        )
        class DetectorService {
            ...
        }

   RPNQuery can for example be used to query these properties to determine inclusion or
   exclusion of the service.
 */

/**
 * Service Properties.
 */
@interface Props {

    Prop[] props
}

/**
 * An individual property.
 */
@interface Prop {
    String name
    String val
}

