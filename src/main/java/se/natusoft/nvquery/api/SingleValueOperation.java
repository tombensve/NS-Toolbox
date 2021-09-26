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
public interface SingleValueOperation extends Operation
{
}
