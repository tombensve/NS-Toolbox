package se.natusoft.tools.modelish;

public interface ModelishModel<T> {

    /**
     * This has to be called when all values have been set to make it impossible to modify
     * the objects content.
     *
     * This is not required, but is a good idea to call this!
     *
     * @return itself.
     */
    T lock();
}
