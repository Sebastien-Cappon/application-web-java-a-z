package com.paymybuddy.ewallet.constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A class of constants that regroups a short list of URI that the logger should
 * ignore. This application don't use Thymeleaf and don't call resources from
 * <code>/src/main</code>. That's why only <code>/error</code> is essential in
 * order not to log Spring's automatic masked calls to the <code>/error</code>
 * page when an error is thrown.
 *
 * @author Sébastien Cappon
 * @version 1.0
 */
public class UriToIgnore {
	
	public static final String errorURI = "/error";
	public static final List<String> uriToIgnore = new ArrayList<>(Arrays.asList(errorURI));
}