package com.terheyden;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkElementIndex;
import static com.google.common.base.Preconditions.checkNotNull;
import static java.lang.String.format;
import static java.lang.System.out;
import static org.apache.commons.lang3.Validate.isTrue;
import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notEmpty;
import static org.apache.commons.lang3.Validate.validIndex;

import java.util.List;

import javax.annotation.ParametersAreNonnullByDefault;

@SuppressWarnings("ALL")
@ParametersAreNonnullByDefault
public class ValidatePreconditions
{
    /**
     * Guava's preconditions are pretty sparse.
     * The names are pretty clear, though.
     * https://github.com/google/guava/wiki/PreconditionsExplained
     */
    public void greetGuava(String name, int age, List<String> address)
    {
        checkArgument(age > 18);
        checkElementIndex(0, address.size());

        out.println(format(
            "Hello %s, you are %d. Your address is: %s",
            checkNotNull(name),
            age,
            address.get(0)));
    }

    /**
     * Apache Commons validations are similarly clear, IMHO, and more expressive.
     * http://commons.apache.org/proper/commons-lang/javadocs/api-3.9/org/apache/commons/lang3/Validate.html
     * Note that 'notBlank()' is for Strings, and 'notEmpty()' is for collections.
     */
    public void greetCommons(String name, int age, List<String> address)
    {
        isTrue(age > 18);
        notEmpty(address);
        validIndex(address, 0);

        out.println(format(
            "Hello %s, you are %d. Your address is: %s",
            notBlank(name),
            age,
            address.get(0)));
    }
}
